package ru.senina.itmo.lab8;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.senina.itmo.lab8.parser.JsonParser;
import ru.senina.itmo.lab8.stages.AddElementStage;


import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class CommandsController {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TerminalKeeper terminalKeeper = new TerminalKeeper();
    private static final ClientNetConnector netConnector = new ClientNetConnector();
    private static final JsonParser<CommandResponse> responseParser = new JsonParser<>(objectMapper, CommandResponse.class);
    private static final JsonParser<CommandArgs> commandArgsJsonParser = new JsonParser<>(objectMapper, CommandArgs.class);
    private static int recursionLevel = ClientMain.RECURSION_LEVEL;

    //todo: process exceptions
    private static String newCommand(CommandArgs command) throws InvalidServerAnswer, RefusedConnectionException {
        if ("execute_script".equals(command.getCommandName())) {
            if (recursionLevel < ClientMain.RECURSION_LEVEL) {
                recursionLevel++;
                try {
                    LinkedList<CommandArgs> scriptCommands = terminalKeeper.executeScript(command.getArgs()[1]);
                    for (CommandArgs c : scriptCommands) {
                        newCommand(c);
                    }
                } catch (FileAccessException e) {
                    GraphicsMain.printCommandResult(e.getMessage());
                }
            } else {
                GraphicsMain.printCommandResult(
                        "You have stacked in the recursion! It's not allowed to deep in more then 10 levels. " +
                                "\n No more recursive scripts would be executed!");
                recursionLevel = 0;
            }
            return "Execute script command finished!";
        }
        command.setToken(ClientMain.TOKEN);
        String message = commandArgsJsonParser.fromObjectToString(command);
        tryToConnect(ClientMain.HOST, ClientMain.PORT, ClientMain.ATTEMPTS_TO_CONNECT, ClientMain.DELAY_TO_CONNECT);
        netConnector.sendMessage(message);
        String response = Optional.ofNullable(netConnector.receiveMessage()).orElseThrow(InvalidServerAnswer::new);
        netConnector.stopConnection();
        CommandResponse commandAnswer = responseParser.fromStringToObject(response);
        if (commandAnswer.getCommandName().equals("save")) {
            //todo сохрянять коллекцию в объект
        }
        return commandAnswer.getResponse();
    }

    public static void tryToConnect(String host, int serverPort, int attempts, int delay) {
        for (int i = 0; i < attempts; i++) {
            try {
                netConnector.startConnection(host, serverPort);
                return;
            } catch (RefusedConnectionException e) {
                GraphicsMain.printCommandResult("Sorry, now server is not available! We will try to reconnect in " + delay + " seconds!");
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } catch (InterruptedException ex) {
                    ClientLog.log(Level.WARNING, "EXCEPTION in tryToConnect " + ex);
                }
            }
        }
        GraphicsMain.printCommandResult("Can't connect to server! Try again later!");
        netConnector.stopConnection();
        System.exit(0);
    }

    public static String readNewCommand(CommandArgs command) {
        if(command.getCommandName().equals("add")) {
            command.setElement(AddElementStage.addElementScene());
        }
       return newCommand(command);
    }

    public static String getMessageFromCommandResponse(CommandResponse command){
        return command.getResponse();
    }
}

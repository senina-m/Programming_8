package ru.senina.itmo.lab8;

import com.fasterxml.jackson.databind.ObjectMapper;
import ru.senina.itmo.lab8.parser.JsonParser;
import ru.senina.itmo.lab8.stages.AddElementStage;


import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class CommandsController {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ClientNetConnector netConnector = new ClientNetConnector();
    private static final JsonParser<CommandResponse> responseParser = new JsonParser<>(objectMapper, CommandResponse.class);
    private static final JsonParser<CommandArgs> commandArgsJsonParser = new JsonParser<>(objectMapper, CommandArgs.class);
    private static int recursionLevel = ClientMain.RECURSION_LEVEL;
    private static final Map<String, String[]> commandArgs = createCommandMap();
    private static final TerminalKeeper terminalKeeper = new TerminalKeeper(commandArgs);

    //todo: process exceptions
    private static String newCommand(CommandArgs command) throws InvalidServerAnswer, RefusedConnectionException {
        if ("execute_script".equals(command.getCommandName())) {
            String resultMessage = "Execute script command finished!";
            if (recursionLevel < ClientMain.RECURSION_LEVEL) {
                recursionLevel++;
                try {
                    LinkedList<CommandArgs> scriptCommands = terminalKeeper.executeScript(command.getArgs()[1]);
                    for (CommandArgs c : scriptCommands) {
                        newCommand(c);
                    }
                } catch (FileAccessException e) {
                    resultMessage = e.getMessage();
                }
            } else {
                resultMessage = "You have stacked in the recursion! It's not allowed to deep in more then 10 levels. " +
                                "\n No more recursive scripts would be executed!";
                recursionLevel = 0;
            }
            return resultMessage;
        }
        command.setToken(ClientMain.TOKEN);
        String message = commandArgsJsonParser.fromObjectToString(command);
        tryToConnect(ClientMain.HOST, ClientMain.PORT, ClientMain.ATTEMPTS_TO_CONNECT, ClientMain.DELAY_TO_CONNECT);
        netConnector.sendMessage(message);
        String response = Optional.ofNullable(netConnector.receiveMessage()).orElseThrow(InvalidServerAnswer::new);
        netConnector.stopConnection();
        CommandResponse commandAnswer = responseParser.fromStringToObject(response);
//        if (commandAnswer.getCommandName().equals("save")) {
            //todo сохрянять коллекцию в объект
//        }
        return commandAnswer.getResponse();
    }

    //fixme deal with output phrases
    public static void tryToConnect(String host, int serverPort, int attempts, int delay) {
        for (int i = 0; i < attempts; i++) {
            try {
                netConnector.startConnection(host, serverPort);
                return;
            } catch (RefusedConnectionException e) {
//                GraphicsMain.printCommandResult("Sorry, now server is not available! We will try to reconnect in " + delay + " seconds!");
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } catch (InterruptedException ex) {
                    ClientLog.log(Level.WARNING, "EXCEPTION in tryToConnect " + ex);
                }
            }
        }
//        GraphicsMain.printCommandResult("Can't connect to server! Try again later!");
        netConnector.stopConnection();
        System.exit(0);
    }

    public static String readNewCommand(CommandArgs command) throws WindowCloseException{
        for(String arg : commandArgs.get(command.getCommandName())){
            if(arg.equals("element")) {
                command.setElement(AddElementStage.addElementScene());
            }
        }
       return newCommand(command);
    }

    private static Map<String, String[]> createCommandMap() {
        Map<String, String[]> commandMap = new HashMap<>();
        commandMap.put("info", new String[]{});
        commandMap.put("show", new String[]{});
        commandMap.put("add", new String[]{"element"});
        commandMap.put("update", new String[]{"element"});
        commandMap.put("remove_by_id", new String[]{});
        commandMap.put("clear", new String[]{});
        commandMap.put("save", new String[]{});
        commandMap.put("remove_at", new String[]{});
        commandMap.put("remove_greater", new String[]{"element"});
        commandMap.put("sort", new String[]{});
        commandMap.put("min_by_difficulty", new String[]{});
        commandMap.put("filter_by_description", new String[]{});
        commandMap.put("print_descending", new String[]{});
        commandMap.put("execute_script", new String[]{});
        commandMap.put("exit", new String[]{});
        commandMap.put("help", new String[]{});
        return commandMap;
    }
}

package ru.senina.itmo.lab8;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.parser.JsonParser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class CommandsController {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static TerminalKeeper terminalKeeper;
    private static final ClientNetConnector netConnector = new ClientNetConnector();
    private static final JsonParser<CommandResponse> responseParser = new JsonParser<>(objectMapper, CommandResponse.class);
    private static final JsonParser<CommandArgs> commandArgsJsonParser = new JsonParser<>(objectMapper, CommandArgs.class);
    private static int recursionLevel = ClientMain.RECURSION_LEVEL;
    private static boolean working = true;

    //todo: process exceptions
    private static void newCommand(CommandArgs command) throws InvalidServerAnswer, RefusedConnectionException {
        switch (command.getCommandName()) {
            case ("execute_script"):
                if ( recursionLevel < ClientMain.RECURSION_LEVEL) {
                    recursionLevel++;
                    try {
                        LinkedList<CommandArgs> scriptCommands = terminalKeeper.executeScript(command.getArgs()[1]);
                        for (CommandArgs c : scriptCommands) {
                            newCommand(c);
                        }
                    } catch (FileAccessException e) {
                        terminalKeeper.printResponse(new CommandResponse(Status.ACCESS_EXCEPTION, command.getCommandName(), e.getMessage()));
                    }
                } else {
                    terminalKeeper.printResponse(new CommandResponse(Status.SCRIPT_RECURSION_EXCEPTION, "execute_script",
                            "You have stacked in the recursion! It's not allowed to deep in more then 10 levels. " +
                                    "\n No more recursive scripts would be executed!"));
                    recursionLevel = 0;
                }
                break;
            case ("exit"):
                working = false;
            default:
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
                terminalKeeper.printResponse(commandAnswer);
        }
    }

    public static void tryToConnect(String host, int serverPort, int attempts, int delay) {
        for (int i = 0; i < attempts; i++) {
            try {
                netConnector.startConnection(host, serverPort);
                return;
            } catch (RefusedConnectionException e) {
                terminalKeeper.printResponse(new CommandResponse(Status.NETWORK_EXCEPTION, "connect to server",
                        "Sorry, now server is not available! We will try to reconnect in " + delay + " seconds!"));
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } catch (InterruptedException ex) {
                    ClientLog.log(Level.WARNING, "EXCEPTION in tryToConnect " + ex);
                }
            }
        }
        terminalKeeper.printResponse(new CommandResponse(Status.NETWORK_EXCEPTION, "connect to server", "Can't connect to server! Try again later!"));
        netConnector.stopConnection();
        System.exit(0);
    }

    public static void readNewCommand(String commandName){
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(GraphicsMain.getAddElementSceneParent()));
            GraphicsMain.setStageAppearance(stage);
            stage.show();
        } catch (IOException e) {
            ClientLog.log(Level.WARNING, "There is no required resource in method start() in switchToSignUpStage!");
            e.printStackTrace();
        }
//        newCommand(new CommandArgs("add", new String[]{"add"}));
    }
}

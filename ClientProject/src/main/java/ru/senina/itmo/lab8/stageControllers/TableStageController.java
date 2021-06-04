package ru.senina.itmo.lab8.stageControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.parser.JsonParser;
import ru.senina.itmo.lab8.parser.Parser;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

//todo: make buttons equal size by the size of the window
public class TableStageController {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private TerminalKeeper terminalKeeper;
    private final ClientNetConnector netConnector = new ClientNetConnector();
    private final JsonParser<CommandResponse> responseParser = new JsonParser<>(objectMapper, CommandResponse.class);
    private final JsonParser<CommandArgs> commandArgsJsonParser = new JsonParser<>(objectMapper, CommandArgs.class);
    public Button helpButton;
    public Button showButton;
    public Button addButton;
    public Button updateByIdButton;
    public Button removeByIdButton;
    public Button clearButton;
    public Button saveButton;
    public Button executeScriptButton;
    public Button removeAtButton;
    public Button removeGreaterButton;
    public Button SortButton;
    public Button exitButton;
    public TextField updateByIdField;
    public Label updateByIdLabelID;
    public Label removeByIdLabelID;
    public TextField removeByIdField;
    public TextField removeAtField;
    public Label removeAtLabelIndex;
    public Button printDescendingButton;
    public Button filterByDescriptionButton;
    public Button minByDifficultyButton;
    public TableView table;
    private int recursionLevel = ClientMain.RECURSION_LEVEL;
    private boolean working = true;

//    <!--help : вывести справку по доступным командам
//    info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
//    show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении
//    add {element} : добавить новый элемент в коллекцию
//    update id {element} : обновить значение элемента коллекции, id которого равен заданному
//    remove_by_id id : удалить элемент из коллекции по его id
//    clear : очистить коллекцию
//    save : сохранить коллекцию в файл
//    execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.
//            exit : завершить программу (без сохранения в файл)
//    remove_at index : удалить элемент, находящийся в заданной позиции коллекции (index)
//    remove_greater {element} : удалить из коллекции все элементы, превышающие заданный
//    sort : отсортировать коллекцию в естественном порядке
//    min_by_difficulty : вывести любой объект из коллекции, значение поля difficulty которого является минимальным
//    filter_by_description description : вывести элементы, значение поля description которых равно заданному
//    print_descending : вывести элементы коллекции в порядке убывания-->


    public void exitButtonClicked(ActionEvent actionEvent) {
        //todo:how to exit? do the asking window kinda (do you want to exit? are you sure?)
    }

    public void printDescendingButtonClicked(ActionEvent actionEvent) {
    }

    public void filterByDescriptionButtonClicked(ActionEvent actionEvent) {
    }

    public void minByDifficultyButtonClicked(ActionEvent actionEvent) {
    }

    public void SortButtonClicked(ActionEvent actionEvent) {
    }

    public void removeGreaterButtonClicked(ActionEvent actionEvent) {
    }

    public void removeAtButtonClicked(ActionEvent actionEvent) {
    }

    public void saveButtonClicked(ActionEvent actionEvent) {
    }

    public void executeScriptButtonClicked(ActionEvent actionEvent) {
    }

    public void clearButtonClicked(ActionEvent actionEvent) {
    }

    public void removeByIdButtonClicked(ActionEvent actionEvent) {
    }

    public void updateByIdButtonClicked(ActionEvent actionEvent) {
    }

    public void addButtonClicked(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(GraphicsMain.getRegisterSceneParent()));
        } catch (IOException e) {
            ClientLog.log(Level.WARNING, "There is no required resource in method start() in switchToSignUpStage!");
            e.printStackTrace();
        }
        newCommand(new CommandArgs("add", new String[]{"add"}));
    }

    public void showButtonClicked(ActionEvent actionEvent) {
        newCommand(new CommandArgs("show", new String[]{"show"}));
    }

    public void helpButtonClicked(ActionEvent actionEvent) {
        newCommand(new CommandArgs("help", new String[]{"help"}));
    }

    //todo: process exceptions
    private void newCommand(CommandArgs command) throws InvalidServerAnswer, RefusedConnectionException {
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

    public void tryToConnect(String host, int serverPort, int attempts, int delay) {
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
}


package ru.senina.itmo.lab8.sceneControllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BubbleChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.CommandArgs;
import ru.senina.itmo.lab8.CommandsController;
import ru.senina.itmo.lab8.GraphicsMain;
import ru.senina.itmo.lab8.WindowCloseException;
import ru.senina.itmo.lab8.stages.DescriptionAskingStage;
import ru.senina.itmo.lab8.stages.ExitStage;
import ru.senina.itmo.lab8.stages.FileAskingStage;

import java.io.IOException;

public class PlotSceneController {
    public ImageView userColorImage;
    public Button helpButton;
    public Button showButton;
    public Button addButton;
    public Button updateByIdButton;
    public Button removeByIdButton;
    public Button clearButton;
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
    public TextArea consoleField;
    public BubbleChart plotArea;
    public Button switchTableStage;
    public Button infoButton;


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


    public void exitButtonClicked() {
        ExitStage.tryToExit();
    }

    public void printDescendingButtonClicked() {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("print_descending", new String[]{"print_descending"})));
    }

    public void filterByDescriptionButtonClicked() {
        try {
            consoleField.setText(CommandsController.readNewCommand(new CommandArgs("filter_by_description", new String[]{"filter_by_description", DescriptionAskingStage.getDescription()})));
        } catch (WindowCloseException ignored) {
        }
    }

    public void minByDifficultyButtonClicked() {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("min_by_difficulty", new String[]{"min_by_difficulty"})));
    }

    public void SortButtonClicked() {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("sort", new String[]{"sort"})));
    }

    public void removeGreaterButtonClicked() {
        try {
            consoleField.setText(CommandsController.readNewCommand(new CommandArgs("remove_greater", new String[]{"remove_greater"})));
        } catch (WindowCloseException ignored) {
        }
    }

    public void removeAtButtonClicked() {
        try {
            long index = Long.parseLong(removeByIdField.getText());
            consoleField.setText(CommandsController.readNewCommand(new CommandArgs("remove_at", new String[]{"remove_at", String.valueOf(index)})));
        } catch (NumberFormatException e) {
            consoleField.setText("Id in \"remove at index\" has to be long number");
        }
    }

    public void executeScriptButtonClicked() {
        try {
            consoleField.setText(CommandsController.readNewCommand(new CommandArgs("execute_script", new String[]{"execute_script", FileAskingStage.getFilePath()})));
        } catch (WindowCloseException ignored) {
        }
    }

    public void clearButtonClicked() {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("clear", new String[]{"clear"})));
    }

    public void removeByIdButtonClicked() {
        try {
            long id = Long.parseLong(removeByIdField.getText());
            consoleField.setText(CommandsController.readNewCommand(new CommandArgs("remove_by_id", new String[]{"remove_by_id", String.valueOf(id)})));
        } catch (NumberFormatException e) {
            consoleField.setText("Id in \"remove by id\" has to be long number");
        }
    }

    public void updateByIdButtonClicked() {
        try {
            long id = Long.parseLong(updateByIdField.getText());
            consoleField.setText(CommandsController.readNewCommand(new CommandArgs("update", new String[]{"update", String.valueOf(id)})));
        } catch (NumberFormatException e) {
            consoleField.setText("Id in \"update\" has to be long number");
        } catch (WindowCloseException ignored) {
        }
    }

    public void addButtonClicked() {
        try {
            consoleField.setText(CommandsController.readNewCommand(new CommandArgs("add", new String[]{"add"})));
        } catch (WindowCloseException ignored) {
        }
    }

    public void showButtonClicked() {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("show", new String[]{"show"})));
    }

    public void helpButtonClicked() {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("help", new String[]{"help"})));
    }

    public void infoButtonClicked() {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("info", new String[]{"info"})));
    }

    public void switchToTableStageButtonClicked(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(GraphicsMain.getTableSceneParent()));
            stage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);  //todo: think about such killing termination
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

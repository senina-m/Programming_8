package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.*;

import java.io.IOException;
import java.util.logging.Level;

//todo: make buttons equal size by the size of the window
public class TableSceneController {


    public ImageView userColorImage;
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
    public TextArea consoleField;
    public Button switchToPlotStage;


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
        try {
//            Stage stage1 = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Stage stage = new Stage();
            stage.setScene(new Scene(GraphicsMain.getExitSceneParent()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printDescendingButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("print_descending", new String[]{"print_descending"})));
    }

    public void filterByDescriptionButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("filter_by_description", new String[]{"filter_by_description"})));
    }

    public void minByDifficultyButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("min_by_difficulty", new String[]{"min_by_difficulty"})));
    }

    public void SortButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("sort", new String[]{"sort"})));
    }

    public void removeGreaterButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("execute_script", new String[]{"execute_script"})));
    }

    public void removeAtButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("remove_at", new String[]{"remove_at"})));
    }

    public void executeScriptButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("execute_script", new String[]{"execute_script"})));
    }

    public void clearButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("clear", new String[]{"clear"})));
    }

    public void removeByIdButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("remove_by_id", new String[]{"remove_by_id"})));
    }

    public void updateByIdButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("update", new String[]{"update"})));
        //fixme add to list
    }

    public void addButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("add", new String[]{"add"})));
    }

    public void showButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("show", new String[]{"show"})));
    }

    public void helpButtonClicked(ActionEvent actionEvent) {
        consoleField.setText(CommandsController.readNewCommand(new CommandArgs("help", new String[]{"help"})));
    }


    public void switchToPlotStageButtonClicked(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(GraphicsMain.getPlotSceneParent()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


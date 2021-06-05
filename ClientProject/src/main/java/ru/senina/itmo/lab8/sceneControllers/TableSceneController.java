package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import ru.senina.itmo.lab8.*;

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
        CommandsController.readNewCommand("add");
    }

    public void showButtonClicked(ActionEvent actionEvent) {
//        newCommand(new CommandArgs("show", new String[]{"show"}));
    }

    public void helpButtonClicked(ActionEvent actionEvent) {
//        newCommand(new CommandArgs("help", new String[]{"help"}));
    }

    public void switchToPlotStageButtonClicked(ActionEvent event) {
    }
}


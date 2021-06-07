package ru.senina.itmo.lab8.sceneControllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.labwork.Difficulty;
import ru.senina.itmo.lab8.labwork.LabWork;
import ru.senina.itmo.lab8.stages.DescriptionAskingStage;
import ru.senina.itmo.lab8.stages.ExitStage;
import ru.senina.itmo.lab8.stages.FileAskingStage;


import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

//todo: make buttons equal size by the size of the window
public class TableSceneController {


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
    public TableView<LabWork> table;
    public TextArea consoleField;
    public Button switchToPlotStage;
    public Button infoButton;
    private final Timer timer = new Timer();
    private final TimerTask task = new TimerTask(){
        public void run()
        {
            ArrayList<LabWork> collection = CommandsController.updateCollection();
            ObservableList<LabWork> list = FXCollections.observableList(collection);
            table.setItems(list);
        }

    };
    @FXML private TableColumn<LabWork, Integer> index;
    @FXML private TableColumn<LabWork, String> owner;
    @FXML private TableColumn<LabWork, Long> id;
    @FXML private TableColumn<LabWork, String> name;
    @FXML private TableColumn<LabWork, Integer> x;
    @FXML private TableColumn<LabWork, Long> y;
    @FXML private TableColumn<LabWork, LocalDateTime> time;
    @FXML private TableColumn<LabWork, Float> minimalPoint;
    @FXML private TableColumn<LabWork, String> description;
    @FXML private TableColumn<LabWork, Integer> averagePoint;
    @FXML private TableColumn<LabWork, Difficulty> difficulty;
    @FXML private TableColumn<LabWork, String> disciplineName;
    @FXML private TableColumn<LabWork, Long> disciplineLectureHours;
    @FXML private TableColumn<LabWork, Integer> disciplinePracticeHours;
    @FXML private TableColumn<LabWork, Integer> disciplineSelfStudyHours;

    @FXML
    public void initialize() {
        owner.setCellValueFactory(new PropertyValueFactory<>("ownerLogin"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        x.setCellValueFactory(new PropertyValueFactory<>("x"));
        y.setCellValueFactory(new PropertyValueFactory<>("y"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        minimalPoint.setCellValueFactory(new PropertyValueFactory<>("minimalPoint"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        averagePoint.setCellValueFactory(new PropertyValueFactory<>("averagePoint"));
        difficulty.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
        disciplineName.setCellValueFactory(new PropertyValueFactory<>("disciplineName"));
        disciplineLectureHours.setCellValueFactory(new PropertyValueFactory<>("disciplineLectureHours"));
        disciplinePracticeHours.setCellValueFactory(new PropertyValueFactory<>("disciplinePracticeHours"));
        disciplineSelfStudyHours.setCellValueFactory(new PropertyValueFactory<>("disciplineSelfStudyHours"));
        timerUpdateMethod();
    }

    public void stopTimer(){
        timer.cancel();
    }

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


    public void switchToPlotStageButtonClicked(ActionEvent event) {
        GraphicsMain.getPlotScene((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    private void timerUpdateMethod(){
        timer.scheduleAtFixedRate(task, new Date(), 1000L);
    }
}


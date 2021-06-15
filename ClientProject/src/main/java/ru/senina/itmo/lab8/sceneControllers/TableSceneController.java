package ru.senina.itmo.lab8.sceneControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.exceptions.AddingWindowCloseException;
import ru.senina.itmo.lab8.labwork.Difficulty;
import ru.senina.itmo.lab8.labwork.LabWork;
import ru.senina.itmo.lab8.stages.DescriptionAskingStage;
import ru.senina.itmo.lab8.stages.ExitStage;
import ru.senina.itmo.lab8.stages.FileAskingStage;


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
//    public Button removeAtButton;
    public Button removeGreaterButton;
    public Button SortButton;
    public Button exitButton;
    public TextField updateByIdField;
    public Label updateByIdLabelID;
    public Label removeByIdLabelID;
    public TextField removeByIdField;
//    public TextField removeAtField;
//    public Label removeAtLabelIndex;
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
        initLabels();
        owner.setCellValueFactory(new PropertyValueFactory<>("ownerLogin"));
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        x.setCellValueFactory(new PropertyValueFactory<>("x"));
        y.setCellValueFactory(new PropertyValueFactory<>("y"));
        time.setCellValueFactory(new PropertyValueFactory<>("creationDate"));
        time.setText("time");
        minimalPoint.setCellValueFactory(new PropertyValueFactory<>("minimalPoint"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        averagePoint.setCellValueFactory(new PropertyValueFactory<>("averagePoint"));
        difficulty.setCellValueFactory(new PropertyValueFactory<>("stringDifficulty"));
        difficulty.setText(ClientMain.getRB().getString("difficulty"));
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
        } catch (AddingWindowCloseException ignored) {
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
        } catch (AddingWindowCloseException ignored) {
        }
    }

//    public void removeAtButtonClicked() {
//        try {
//            long index = Long.parseLong(removeByIdField.getText());
//            consoleField.setText(CommandsController.readNewCommand(new CommandArgs("remove_at", new String[]{"remove_at", String.valueOf(index)})));
//        } catch (NumberFormatException e) {
//            consoleField.setText("Id in \"remove at index\" has to be long number");
//        }
//    }

    public void executeScriptButtonClicked() {
        try {
            consoleField.setText(CommandsController.readNewCommand(new CommandArgs("execute_script", new String[]{"execute_script", FileAskingStage.getFilePath()})));
        } catch (AddingWindowCloseException ignored) {
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
        } catch (AddingWindowCloseException ignored) {
        }
    }

    public void addButtonClicked() {
        try {
            consoleField.setText(CommandsController.readNewCommand(new CommandArgs("add", new String[]{"add"})));
        } catch (AddingWindowCloseException ignored) {
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

    private void initLabels() {
        switchToPlotStage.setText(ClientMain.getRB().getString("plotView"));
        helpButton.setText(ClientMain.getRB().getString("help"));
        showButton.setText(ClientMain.getRB().getString("show"));
        addButton.setText(ClientMain.getRB().getString("add"));
        infoButton.setText(ClientMain.getRB().getString("info"));
        clearButton.setText(ClientMain.getRB().getString("clear"));
        executeScriptButton.setText(ClientMain.getRB().getString("executeScript"));
        removeGreaterButton.setText(ClientMain.getRB().getString("removeGreater"));
        SortButton.setText(ClientMain.getRB().getString("sort"));
        minByDifficultyButton.setText(ClientMain.getRB().getString("minByDifficulty"));
        filterByDescriptionButton.setText(ClientMain.getRB().getString("filterByDescription"));
        printDescendingButton.setText(ClientMain.getRB().getString("printDescending"));
//        removeAtButton.setText(ClientMain.getRB().getString("removeAt"));
//        removeAtLabelIndex.setText(ClientMain.getRB().getString("index") + ":");
        updateByIdButton.setText(ClientMain.getRB().getString("update"));
        updateByIdLabelID.setText(ClientMain.getRB().getString("id"));
        removeByIdButton.setText(ClientMain.getRB().getString("removeById"));
        removeByIdLabelID.setText(ClientMain.getRB().getString("id"));
        exitButton.setText(ClientMain.getRB().getString("exit"));
        owner.setText(ClientMain.getRB().getString("owner"));
        id.setText(ClientMain.getRB().getString("id"));
        name.setText(ClientMain.getRB().getString("name"));
        x.setText(ClientMain.getRB().getString("coordinateX"));
        y.setText(ClientMain.getRB().getString("coordinateY"));
        time.setText(ClientMain.getRB().getString("time"));
        minimalPoint.setText(ClientMain.getRB().getString("minimalPoint"));
        averagePoint.setText(ClientMain.getRB().getString("averagePoint"));
        description.setText(ClientMain.getRB().getString("description"));
        disciplineName.setText(ClientMain.getRB().getString("disciplineName"));
        disciplineLectureHours.setText(ClientMain.getRB().getString("disciplineLectureHours"));
        disciplinePracticeHours.setText(ClientMain.getRB().getString("disciplinePracticeHours"));
        disciplineSelfStudyHours.setText(ClientMain.getRB().getString("disciplineSelfStudyHours"));
    }
}


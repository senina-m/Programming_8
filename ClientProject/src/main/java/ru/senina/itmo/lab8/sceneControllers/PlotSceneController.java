package ru.senina.itmo.lab8.sceneControllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.labwork.Coordinates;
import ru.senina.itmo.lab8.labwork.Difficulty;
import ru.senina.itmo.lab8.labwork.Discipline;
import ru.senina.itmo.lab8.labwork.LabWork;
import ru.senina.itmo.lab8.stages.DescriptionAskingStage;
import ru.senina.itmo.lab8.stages.ExitStage;
import ru.senina.itmo.lab8.stages.FileAskingStage;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class PlotSceneController {
    @FXML
    public ImageView userColorImage;
    @FXML
    public Button helpButton;
    @FXML
    public Button showButton;
    @FXML
    public Button addButton;
    @FXML
    public Button updateByIdButton;
    @FXML
    public Button removeByIdButton;
    @FXML
    public Button clearButton;
    @FXML
    public Button executeScriptButton;
    @FXML
    public Button removeAtButton;
    @FXML
    public Button removeGreaterButton;
    @FXML
    public Button SortButton;
    @FXML
    public Button exitButton;
    @FXML
    public TextField updateByIdField;
    @FXML
    public Label updateByIdLabelID;
    @FXML
    public Label removeByIdLabelID;
    @FXML
    public TextField removeByIdField;
    @FXML
    public TextField removeAtField;
    @FXML
    public Label removeAtLabelIndex;
    @FXML
    public Button printDescendingButton;
    @FXML
    public Button filterByDescriptionButton;
    @FXML
    public Button minByDifficultyButton;
    @FXML
    public TextArea consoleField;
    @FXML
    public Button switchTableStage;
    @FXML
    public Button infoButton;
    @FXML
    public Canvas canvas;
    @FXML
    public StackPane pane;
    private GraphicsContext gc;
    private final Timer timer = new Timer();
    private final double xScale = 0.2;
    private final double yScale = 0.2;
    private final int wBorder = 20;
    private final int hBorder = 20;
    private final int axisX = 74;
    private final int axisY = -47;
    private final TimerTask task = new TimerTask() {
        public void run() {

//                ArrayList<LabWork> collection = CommandsController.updateCollection();
        }
    };
//    private double w = canvas.getWidth();
//    private double h = canvas.getHeight();

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        redraw();
//        timerUpdateMethod();
        canvas.widthProperty().addListener(observable -> redraw());
        canvas.heightProperty().addListener(observable -> redraw());
    }

    @FXML
    public double prefWidth(double height) {
        return canvas.getWidth();
    }

    @FXML
    public double prefHeight(double width) {
        return canvas.getHeight();
    }

    @FXML
    public boolean isResizable() {
        return true;
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

    public void switchToTableStageButtonClicked(ActionEvent event) {
        GraphicsMain.getTableScene((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    //it this method height and width - is 50 less then real, in order to ges some space on the right and bottom
    private void redraw() {
        double h = canvas.getHeight();
        double w = canvas.getWidth();
        //background
        gc.clearRect(0, 0, w, h);
        gc.setFill(Color.ANTIQUEWHITE);
        gc.fillRect(0, 0, w, h);

        drawArrow(0, (int) h - hBorder, (int) w, (int) h - hBorder);
        drawArrow((int) w - wBorder, (int) h, (int) w - wBorder, 0);

        drawLabwork(convertX(50), convertY(50), createElement(Difficulty.VERY_EASY));
        drawLabwork(200, 300, createElement(Difficulty.HOPELESS));
        //todo: recount canvas size and redraw the chart
    }

    private void timerUpdateMethod() {
        timer.scheduleAtFixedRate(task, new Date(), 1000L);
    }

    //x, y - center of labWork
    private void drawLabwork(int x, int y, LabWork element) {
        double widthScale = canvas.getWidth() / (400);
        int minimalSize = 20;
        double percentageDifference = 1.2;
        double size = percentageDifference * (minimalSize + element.getDifficultyIntValue());
        double widthSize = size * widthScale;
        double heightSize = size * widthScale * 1.5;
        System.out.println(x + " " + y + " width " + widthSize + " height " + heightSize);
        gc.setFill(Color.GRAY);
        gc.fillRect(x - widthSize / 2, y - heightSize / 2, widthSize, heightSize);
        gc.setStroke(Color.FORESTGREEN.brighter());
        gc.setLineWidth(5);
        gc.strokeRect(x - widthSize / 2, y - heightSize / 2, widthSize, heightSize);
        //todo animation that draws and arises letter L
    }

    private LabWork createElement(Difficulty difficulty) {
        String name = "7th lab";
        Coordinates coordinates = new Coordinates(2, 3);
        float minimalPoint = 80;
        String description = "I love my rat - Hory, but because of lab I have no time for her!";
        Integer averagePoint = 60;
        Discipline discipline = new Discipline("Programming", 35, 65, 1000000);
        Owner owner = new Owner();
        owner.setLogin("masha");
        owner.setPassword("kjhfldskjhlieuryhfkjdh");
        owner.setToken("kjhslkjhlfskhjdlkhz");
        LabWork element = new LabWork(name, coordinates, minimalPoint, description, averagePoint, difficulty, discipline);
        element.setOwner(owner);
        return element;
    }

    private void drawArrow(int x1, int y1, int x2, int y2) {
        double ARR_SIZE = 4;
        gc.setFill(Color.BLACK);

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        //
        Transform transform = Transform.translate(x1, y1);
        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
        gc.setTransform(new Affine(transform));

        gc.strokeLine(0, 0, len, 0);

        //fixme рисуем деления
        for(int i=0; i <130; i+=5){
            gc.strokeLine(i*5, 5,i*5, -5);
        }
        gc.fillPolygon(new double[]{len, len - ARR_SIZE, len - ARR_SIZE, len}, new double[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);

        transform = Transform.translate(0, 0);
        gc.setTransform(new Affine((transform)));


    }

    private int convertX(int x) {
        return (int) (canvas.getWidth() - wBorder - (axisX -x) / xScale);
    }

    private int convertY(int y) {
        return (int) (canvas.getHeight() - hBorder - (y - axisY) / yScale);
    }
}

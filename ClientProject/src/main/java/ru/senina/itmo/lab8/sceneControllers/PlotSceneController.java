package ru.senina.itmo.lab8.sceneControllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.labwork.Coordinates;
import ru.senina.itmo.lab8.labwork.Difficulty;
import ru.senina.itmo.lab8.labwork.Discipline;
import ru.senina.itmo.lab8.labwork.LabWork;
import ru.senina.itmo.lab8.stages.DescriptionAskingStage;
import ru.senina.itmo.lab8.stages.ExitStage;
import ru.senina.itmo.lab8.stages.FileAskingStage;
import ru.senina.itmo.lab8.stages.LabInfoStage;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;

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
    private final ArrayList<Color> colors = new ArrayList<>(Arrays.asList(Color.AQUA, Color.MAGENTA, Color.YELLOW, Color.BLUE, Color.BROWN, Color.CORAL, Color.CYAN));
    private int currentColor = 0;
    private final Map<String, Color> colorMap = new HashMap<>();
    private ArrayList<LabWork> currentCollection;
    private long timerStartDate;
    private boolean displayText = true;

    private final TimerTask task = new TimerTask() {
        public void run() {
            ArrayList<LabWork> collection = CommandsController.updateCollection();
            currentCollection = collection;
            int secondsFromStart = (int)(System.currentTimeMillis() - timerStartDate)/1000; //fixme костыль какой-то
            if (secondsFromStart % 3 == 0) {
                displayText = !displayText;
            }
            redraw(collection, displayText);
        }
    };

    private void timerUpdateMethod() {
        timerStartDate = System.currentTimeMillis();
        timer.scheduleAtFixedRate(task, new Date(), 1000L);
    }

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        redrawPlot();
        canvas.widthProperty().addListener(observable -> redrawPlot());
        canvas.heightProperty().addListener(observable -> redrawPlot());

        canvas.setOnMouseClicked(event -> {
            System.out.println("User Clicked!");
            double x = event.getX();
            double y = event.getY();
            try{
                LabWork element = findLabWork(x, y);
                LabInfoStage.startLabInfoStage(element);
            }catch (NoSuchElementException ignored){}
        });

        timerUpdateMethod();
    }

    private LabWork findLabWork(double x, double y) throws NoSuchElementException {
        for( LabWork labWork : currentCollection){
            double[] size = getLabworkWidthHeight(labWork);
            if(convertX(labWork.getX()) < x + size[0]/2 && convertX(labWork.getX()) > x - size[0]/2 &&
                    convertY((int)labWork.getY()) < y + size[1]/2 && convertY((int) labWork.getY()) > y - size[1]/2 ){
                return labWork;
            }
        }
        throw new NoSuchElementException();
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
    private void redraw(ArrayList<LabWork> collection, Boolean setText) {
        redrawPlot();
        for (LabWork labWork : collection) {
            if(!colorMap.containsKey(labWork.getOwnerLogin())){
                colorMap.put(labWork.getOwnerLogin(), getNewColor());
            }
            drawLabwork(labWork, colorMap.get(labWork.getOwnerLogin()), setText);
        }
        //todo: recount canvas size and redraw the chart
    }

    private void redrawPlot() {
        double h = canvas.getHeight();
        double w = canvas.getWidth();
        //background
        gc.clearRect(0, 0, w, h);
        gc.setFill(Color.ANTIQUEWHITE);
        gc.fillRect(0, 0, w, h);


        drawArrow(0, (int) h - hBorder, (int) w, (int) h - hBorder);
        drawArrow((int) w - wBorder, (int) h, (int) w - wBorder, 0);

        //todo: recount canvas size and redraw the chart
    }

    //x, y - center of labWork
    private void drawLabwork(LabWork element, Color userColor, Boolean setText) {
        int x = convertX(element.getX());
        int y = convertY((int) element.getY());
        double[] size = getLabworkWidthHeight(element);
        double width = size[0];
        double height = size[1];

        gc.setFill(Color.GRAY);
        gc.fillRect(x - width / 2, y - height / 2, width, height);
        ClientLog.log(Level.INFO,"Lab coordinates " + element.getName() + ": x: " + (x - width / 2) + " y: " + (y - height / 2) );

        if(setText) {
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("null", FontWeight.BOLD, 14));
            gc.fillText("Lab", x - width/4, y);
        }

        gc.setStroke(userColor);
        gc.setLineWidth(2);
        gc.strokeRect(x - width / 2, y - height / 2, width, height);
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

        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        //
        Transform transform = Transform.translate(x1, y1);
        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
        gc.setTransform(new Affine(transform));

        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
        gc.strokeLine(0, 0, len, 0);

        //fixme рисуем деления
        for (int i = 0; i < 130; i += 5) {
            gc.strokeLine(i * 5, 5, i * 5, -5);
        }
        gc.setFill(Color.BLACK);
        gc.fillPolygon(new double[]{len, len - ARR_SIZE, len - ARR_SIZE, len}, new double[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);

        transform = Transform.translate(0, 0);
        gc.setTransform(new Affine((transform)));
    }

    private int convertX(int x) {
        return (int) (canvas.getWidth() - wBorder - (axisX - x) / xScale);
    }

    private int convertY(int y) {
        return (int) (canvas.getHeight() - hBorder - (y - axisY) / yScale);
    }

    private Color getNewColor() {
        Color color = colors.get(currentColor);
        currentColor++;
        return color;
    }

    private double[] getLabworkWidthHeight(LabWork element){
        double widthScale = canvas.getWidth() / (400);
        int minimalSize = 10;
        double percentageDifference = 2;
        double size = percentageDifference * (minimalSize + element.getDifficultyIntValue());
        double widthSize = size * widthScale;
        double heightSize = size * widthScale * 1.5;
        return new double[]{widthSize, heightSize};
    }
}

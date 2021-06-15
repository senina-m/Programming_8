package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
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
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.exceptions.AddingWindowCloseException;
import ru.senina.itmo.lab8.labwork.LabWork;
import ru.senina.itmo.lab8.stages.DescriptionAskingStage;
import ru.senina.itmo.lab8.stages.ExitStage;
import ru.senina.itmo.lab8.stages.FileAskingStage;
import ru.senina.itmo.lab8.stages.LabInfoStage;

import java.util.*;
import java.util.logging.Level;

//todo:insert the user image (user color)
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
    //    @FXML
//    public Button removeAtButton;
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
    //    @FXML
//    public TextField removeAtField;
//    @FXML
//    public Label removeAtLabelIndex;
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
    private static double xScale = 0.2;
    private static double yScale = 0.2;
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
    private double minX;
    private double maxY;

    private final TimerTask task = new TimerTask() {
        public void run() {
            ArrayList<LabWork> collection = CommandsController.updateCollection();
            currentCollection = collection;
            int secondsFromStart = (int) (System.currentTimeMillis() - timerStartDate) / 1000; //fixme костыль какой-то
            if (secondsFromStart % 3 == 0) {
                displayText = !displayText;
            }
            redraw(displayText);
        }
    };

    private void timerUpdateMethod() {
        timerStartDate = System.currentTimeMillis();
        timer.scheduleAtFixedRate(task, new Date(), 1000L);
    }

    @FXML
    public void initialize() {
        initLabels();
        gc = canvas.getGraphicsContext2D();
        redrawPlot();
        canvas.widthProperty().addListener(observable -> {
            redraw(displayText);
        });
        canvas.heightProperty().addListener(observable -> {
            redraw(displayText);
        });

        canvas.setOnMouseClicked(event -> {
            double x = event.getX();
            double y = event.getY();
            try {
                LabWork element = findLabWork(x, y);
                LabInfoStage.startLabInfoStage(element);
            } catch (NoSuchElementException ignored) {
            }
        });

        timerUpdateMethod();
    }

    private double geXScale() {
        return minX /canvas.getWidth();
    }

    private double getYScale() {
        return maxY/canvas.getHeight();
    }

    private LabWork findLabWork(double x, double y) throws NoSuchElementException {
        for (LabWork labWork : currentCollection) {
            double[] size = getLabworkWidthHeight(labWork);
            if (convertX(labWork.getX()) < x + size[0] / 2 && convertX(labWork.getX()) > x - size[0] / 2 &&
                    convertY((int) labWork.getY()) < y + size[1] / 2 && convertY((int) labWork.getY()) > y - size[1] / 2) {
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
//            consoleField.setText(ClientMain.getRB().getString("idIn") + " \"" + ClientMain.getRB().getString("removeById")
//                    + "\""+ ClientMain.getRB().getString("hasToBeLongNumber"));
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
            consoleField.setText(ClientMain.getRB().getString("idIn") + " \"" + ClientMain.getRB().getString("removeById")
                    + "\"" + ClientMain.getRB().getString("hasToBeLongNumber"));
        }
    }

    public void updateByIdButtonClicked() {
        try {
            long id = Long.parseLong(updateByIdField.getText());
            consoleField.setText(CommandsController.readNewCommand(new CommandArgs("update", new String[]{"update", String.valueOf(id)})));
        } catch (NumberFormatException e) {
            consoleField.setText(ClientMain.getRB().getString("idIn") + " \"" + ClientMain.getRB().getString("update")
                    + "\"" + ClientMain.getRB().getString("hasToBeLongNumber"));
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

    public void switchToTableStageButtonClicked(ActionEvent event) {
        GraphicsMain.getTableScene((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    private void redraw(Boolean setText) {
        updateScales();
        redrawPlot();
        for (LabWork labWork : currentCollection) {
            if (!colorMap.containsKey(labWork.getOwnerLogin())) {
                colorMap.put(labWork.getOwnerLogin(), getNewColor());
            }
            drawLabwork(labWork, colorMap.get(labWork.getOwnerLogin()), setText);
        }
    }

    private void updateScales() {
        minX = currentCollection.stream().mapToInt(LabWork::getX).min().orElse(0) - 50;
        maxY = currentCollection.stream().mapToLong(LabWork::getY).max().orElse(0) + 50;
        xScale = (axisX - minX)/canvas.getWidth();
        yScale = (maxY - axisY)/canvas.getHeight();
    }

    private void redrawPlot() {
        double h = canvas.getHeight();
        double w = canvas.getWidth();
        //background
        gc.clearRect(0, 0, w, h);
//        gc.setFill(Color.ANTIQUEWHITE);
//        gc.fillRect(0, 0, w, h);

        //начало стрелки по х и y, конец стрелки x и y (в иссскуственных координатах)
        drawArrow(0, (int) h - hBorder, (int) w, (int) h - hBorder,
                (axisX + wBorder * xScale) - xScale * w,
                axisX + wBorder * xScale, xScale
                //начальное значение подписей на стрелке
                //конечное значение подписи на стрелке
                //масштаб вдоль этой оси
        );
        drawArrow((int) w - wBorder, (int) h, (int) w - wBorder, 0,
                axisY - hBorder *yScale,
                axisY + (h -hBorder)*yScale, yScale
        );

        //todo: recount canvas size and redraw the chart
    }

    //x, y - center of labWork
    private void drawLabwork(LabWork element, Color userColor, Boolean setText) {
        int x = (int) convertX(element.getX());
        int y = (int) convertY(element.getY());
        double[] size = getLabworkWidthHeight(element);
        double width = size[0];
        double height = size[1];

        gc.setFill(Color.AZURE);
//        gc.fillOval();
//        gc.fillPolygon();
        gc.fillRect(x - width / 2, y - height / 2, width, height);
        ClientLog.log(Level.INFO, "Lab coordinates " + element.getName() + ": x: " + (x - width / 2) + " y: " + (y - height / 2));

        if (setText) {
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("null", 14));
            gc.fillText(ClientMain.getRB().getString("lab"), x - width / 3, y);
        }

        gc.setStroke(userColor);
        gc.setLineWidth(2);
        gc.strokeRect(x - width / 2, y - height / 2, width, height);
    }

    /**
     * руисует оси - стрелочки + подписи значений
     * @param x1 начало стрелки по x в координатах графика (придуманных)
     * @param y1 начало стрелки по y в координатах графика (придуманных)
     * @param x2 конец стрелки по x в координатах графика (придуманных)
     * @param y2 конец стрелки по y в координатах графика (придуманных)
     * @param startVal значение координат на начале оси (первая подпись на оси)
     * @param stopVal значение координат на конце оси (последняя подпись на оси)
     * @param scale масштаб вдоль оси
     */
    private void drawArrow(int x1, int y1, int x2, int y2, double startVal, double stopVal, double scale) {
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

        drawDivides(startVal, stopVal, scale, len);

        gc.fillPolygon(new double[]{len, len - ARR_SIZE, len - ARR_SIZE, len}, new double[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);

        transform = Transform.translate(0, 0);
        gc.setTransform(new Affine((transform)));
    }



    /**
     * рисует значения на оси
     * @param startVal значение координат на начале оси (первая подпись на оси)
     * @param stopVal значение координат на конце оси (последняя подпись на оси)
     * @param scale масштаб вдоль оси
     * @param lengthInPix длинна оси в пикселях
     * intervalInPix - частота подписей на экране в пикселях
     */
    private void drawDivides(double startVal, double stopVal, double scale, int lengthInPix) {
        double widthOfDividersInPix = 3; // lengthInPix of division lines
        double textSpace = 3; //how far is text from line
        // частота подписей на экране в пикселях
        double intervalInPix = 100;//intervalInPix between divisions in pix
        int numberOfDivides = (int) Math.round(lengthInPix / intervalInPix);
        int intervalInCoords = (int) Math.round((stopVal - startVal) / numberOfDivides );
        gc.setFill(Color.BLACK);
        double textValue = startVal;
        for (int i = 0; i <= lengthInPix; i += intervalInPix) {
            gc.strokeLine(i, -(widthOfDividersInPix / 2), i, (widthOfDividersInPix / 2));
            gc.fillText(String.valueOf(Math.round(textValue)), i, -textSpace);
            textValue += intervalInCoords;
        }
    }

    private double convertX(double x) {
        return (canvas.getWidth() - wBorder - (axisX - x) / xScale);
    }

    private double convertY(double y) {
        return (canvas.getHeight() - hBorder - (y - axisY) / yScale);
    }

    private Color getNewColor() {
        Color color = colors.get(currentColor);
        currentColor++;
        return color;
    }

    private double[] getLabworkWidthHeight(LabWork element) {
        double widthScale = canvas.getWidth() / (400);
        int minimalSize = 15;
        double percentageDifference = 2;
        double size = percentageDifference * (minimalSize + element.getDifficultyIntValue());
        double widthSize = size * widthScale;
        double heightSize = size * widthScale * 1.5;
        return new double[]{widthSize, heightSize};
    }

    private void initLabels() {
        switchTableStage.setText(ClientMain.getRB().getString("tableView"));
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
    }
}

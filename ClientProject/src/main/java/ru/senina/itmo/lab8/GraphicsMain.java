package ru.senina.itmo.lab8;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import ru.senina.itmo.lab8.sceneControllers.PlotSceneController;
import ru.senina.itmo.lab8.sceneControllers.TableSceneController;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class GraphicsMain extends Application {

    public static void main() {
        launch();
    }

    @Override
    public void start(Stage primaryStage){
        try {
            primaryStage.setScene(new Scene(getPlotSceneParent()));
            setStageAppearance(primaryStage);

            primaryStage.show();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    //todo: убрать повторение кода

    public static Parent getLoginSceneParent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = GraphicsMain.class.getResource("/fxmls/loginScene.fxml");
        loader.setLocation(xmlUrl);
        return loader.load();
    }

    public static Parent getRegisterSceneParent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = GraphicsMain.class.getResource("/fxmls/registrerScene.fxml");
        loader.setLocation(xmlUrl);
        return loader.load();
    }

    public static Parent getTableSceneParent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = GraphicsMain.class.getResource("/fxmls/tableScene.fxml");
        loader.setLocation(xmlUrl);
        return loader.load();
    }

    public static Parent getAddElementSceneParent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = GraphicsMain.class.getResource("/fxmls/addingElementStage.fxml");
        loader.setLocation(xmlUrl);
        return loader.load();
    }

    public static Parent getPlotSceneParent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = GraphicsMain.class.getResource("/fxmls/plotScene.fxml");
        loader.setLocation(xmlUrl);
        return loader.load();
    }
    public static Parent getExitSceneParent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = GraphicsMain.class.getResource("/fxmls/exitScene.fxml");
        loader.setLocation(xmlUrl);
        return loader.load();
    }

    public static void setStageAppearance(Stage primaryStage) {
        Image image = new Image(Objects.requireNonNull(GraphicsMain.class.getResourceAsStream("/icon.png")));
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("Collection Keeper App");
//        primaryStage.setMinHeight(350);
//        primaryStage.setMinWidth(350);
//        primaryStage.setFullScreen(true);
    }

    public static void printCommandResult(String message) {
        FXMLLoader tableLoader = new FXMLLoader();
        URL xmlUrlTable = GraphicsMain.class.getResource("/fxmls/tableScene.fxml");
        tableLoader.setLocation(xmlUrlTable);
        TableSceneController tableController = tableLoader.getController();
        FXMLLoader plotLoader = new FXMLLoader();
        URL xmlUrlPlot = GraphicsMain.class.getResource("/fxmls/plotScene.fxml");
        plotLoader.setLocation(xmlUrlPlot);
        PlotSceneController plotController = plotLoader.getController();
        tableController.consoleField.setText(message);
        plotController.consoleField.setText(message);
    }

}

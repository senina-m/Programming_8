package ru.senina.itmo.lab8;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import ru.senina.itmo.lab8.sceneControllers.PlotSceneController;

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

    @Deprecated
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

    public static Parent getAskingDescriptionSceneParent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = GraphicsMain.class.getResource("/fxmls/descriptionAskingScene.fxml");
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

    public static void getPlotScene(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = GraphicsMain.class.getResource("/fxmls/plotScene.fxml");
            loader.setLocation(xmlUrl);
            stage.setScene(new Scene(loader.load()));
            stage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);  //todo: think about such killing termination
            });
            PlotSceneController controller = loader.getController();
            controller.canvas.widthProperty().bind(controller.pane.widthProperty());
            controller.canvas.heightProperty().bind(controller.pane.heightProperty());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getTableScene(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = GraphicsMain.class.getResource("/fxmls/tableScene.fxml");
            loader.setLocation(xmlUrl);
            stage.setScene(new Scene(loader.load()));
            stage.setOnCloseRequest(e -> {
                Platform.exit();
                System.exit(0);  //todo: think about such killing termination
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

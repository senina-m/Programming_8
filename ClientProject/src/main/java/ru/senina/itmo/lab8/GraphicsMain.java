package ru.senina.itmo.lab8;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;

public class GraphicsMain extends Application {

    public static void main() {
        launch();
    }

    @Override
    public void start(Stage primaryStage){
        try {
            primaryStage.setScene(new Scene(getTableSceneParent()));
            setStageAppearance(primaryStage);

            primaryStage.show();
        } catch (IOException e){
            ClientLog.log(Level.WARNING, "There is no required resource in method start() in GraphicsMain!");
            e.printStackTrace();
        }

    }

    //todo: убрать повторение кода

    public static Parent getLoginSceneParent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = GraphicsMain.class.getResource("/loginScene.fxml");
        loader.setLocation(xmlUrl);
        return loader.load();
    }

    public static Parent getRegisterSceneParent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = GraphicsMain.class.getResource("/registrerScene.fxml");
        loader.setLocation(xmlUrl);
        return loader.load();
    }

    public static Parent getTableSceneParent() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL xmlUrl = GraphicsMain.class.getResource("/tableScene.fxml");
        loader.setLocation(xmlUrl);
        return loader.load();
    }

    public static void setStageAppearance(Stage primaryStage) {
        Image image = new Image(Objects.requireNonNull(GraphicsMain.class.getResourceAsStream("/icon.png")));
        primaryStage.getIcons().add(image);
        primaryStage.setTitle("Collection Keeper App");
        primaryStage.setMinHeight(350);
        primaryStage.setMinWidth(350);
//        primaryStage.setFullScreen(true);
    }
}

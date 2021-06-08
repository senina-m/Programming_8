package ru.senina.itmo.lab8.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.GraphicsMain;

import java.io.IOException;
import java.net.URL;

public class LoginStage {

    public static void start(Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = GraphicsMain.class.getResource("/fxmls/loginScene.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

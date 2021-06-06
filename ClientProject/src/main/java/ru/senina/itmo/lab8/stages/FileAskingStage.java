package ru.senina.itmo.lab8.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.GraphicsMain;
import ru.senina.itmo.lab8.WindowCloseException;
import ru.senina.itmo.lab8.sceneControllers.FileAskingController;

import java.io.IOException;
import java.net.URL;

public class FileAskingStage {
    public static String getFilePath(){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = GraphicsMain.class.getResource("/fxmls/fileAskingScene.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Stage stage = new Stage();
            FileAskingController controller = loader.getController();
            stage.initModality(Modality.APPLICATION_MODAL); //fixme обработать ситуацию, когда закрывают окно
            stage.setScene(new Scene(root));
            stage.setOnCloseRequest(event -> {
                throw new WindowCloseException();
            });
            GraphicsMain.setStageAppearance(stage);
            stage.showAndWait();
            return controller.getFilepath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

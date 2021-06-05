package ru.senina.itmo.lab8.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.GraphicsMain;
import ru.senina.itmo.lab8.sceneControllers.DescriptionAskingController;
import ru.senina.itmo.lab8.sceneControllers.FileAskingController;

import java.io.IOException;
import java.net.URL;

public class DescriptionAskingStage {
    public static String getDescription(){
        try {
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = GraphicsMain.class.getResource("/fxmls/descriptionAskingScene.fxml");
            loader.setLocation(xmlUrl);
            Parent root = loader.load();
            Stage stage = new Stage();
            DescriptionAskingController controller = loader.getController();
            stage.initModality(Modality.APPLICATION_MODAL); //fixme обработать ситуацию, когда закрывают окно
            stage.setScene(new Scene(root));
            GraphicsMain.setStageAppearance(stage);
            stage.showAndWait();
            return controller.getDescription();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

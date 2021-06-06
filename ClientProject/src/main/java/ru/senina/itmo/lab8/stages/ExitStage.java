package ru.senina.itmo.lab8.stages;

import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.GraphicsMain;

import java.io.IOException;

public class ExitStage {

    public static void tryToExit(){
        try {
            Stage stage = new Stage();
            stage.setScene(new Scene(GraphicsMain.getExitSceneParent()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

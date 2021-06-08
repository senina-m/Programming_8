package ru.senina.itmo.lab8.stages;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.GraphicsMain;
import ru.senina.itmo.lab8.labwork.LabWork;
import ru.senina.itmo.lab8.sceneControllers.LabInfoController;

import java.io.IOException;
import java.net.URL;

public class LabInfoStage {

    public static void startLabInfoStage(LabWork labWork){
        try {
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            URL xmlUrl = GraphicsMain.class.getResource("/fxmls/labDescriptionScene.fxml");
            loader.setLocation(xmlUrl);
            stage.setScene(new Scene(loader.load()));

            LabInfoController controller = loader.getController();
            controller.setElement(labWork);
            controller.nameValueLabel.setText(labWork.getName());
            controller.xCoordinateValueLabel.setText(String.valueOf(labWork.getX()));
            controller.yCoordinateValueLabel.setText(String.valueOf(labWork.getY()));
            controller.minimalPointValueLabel.setText(String.valueOf(labWork.getMinimalPoint()));
            controller.averagePointValueLabel.setText(String.valueOf(labWork.getAveragePoint()));
            controller.disciplineNameArea.setText(String.valueOf(labWork.getDescription()));
            controller.difficultyValueLabel.setText(labWork.getStringDifficulty());
            controller.disciplineNameArea.setText(labWork.getDisciplineName());
            controller.lectureHoursValueLabel.setText(String.valueOf(labWork.getDisciplineLectureHours()));
            controller.practiceHoursValueLabel.setText(String.valueOf(labWork.getDisciplinePracticeHours()));
            controller.selfStudyHoursValueLabel.setText(String.valueOf(labWork.getDisciplineSelfStudyHours()));

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

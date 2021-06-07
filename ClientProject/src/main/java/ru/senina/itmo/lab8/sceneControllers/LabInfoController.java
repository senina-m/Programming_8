package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.labwork.LabWork;

public class LabInfoController {
    public Button exitButton;
    public Label selfStudyHoursValueLabel;
    public Label selfStudyHoursLabel;
    public Label practiceHoursValueLabel;
    public Label practiceHoursLabel;
    public Label lectureHoursValueLabel;
    public Label lectureHoursLabel;
    public Label disciplineNameValueLabel;
    public Label disciplineNLabel;
    public Label difficultyValueLabel;
    public Label difficultyLabel;
    public Label descriptionLabel;
    public Label averagePointLabel;
    public Label minimalPointValueLabel;
    public Label minimalPointLabel;
    public Label titleAddingElementLabel;
    public Label nameLabel;
    public Label nameValueLabel;
    public Label CoordinatesLabel;
    public Label xCoordinateValueLabel;
    public Label yCoordinateValueLabel;
    public Label averagePointValueLabel;
    public TextArea disciplineNameArea;
    private LabWork element;

    public void setElement(LabWork element) {
        this.element = element;
    }

    public void exitButtonClicked(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    @FXML
    public void initialize() {
    }
}

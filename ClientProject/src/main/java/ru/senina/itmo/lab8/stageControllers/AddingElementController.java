package ru.senina.itmo.lab8.stageControllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

//fixme подвинуть все плашечки от края
public class AddingElementController {
    public Label titleAddingElementLabel;
    public Button applyButton;
    public Label warningLabel;
    public TextField selfStudyHoursField;
    public Label selfStudyHoursLabel;
    public TextField practiceHoursField;
    public Label practiceHoursLabel;
    public TextField lectureHoursField;
    public Label lectureHoursLabel;
    public TextField disciplineNameField;
    public Label disciplineNLabel;
    public Label difficultyLabel;
    public TextArea descriptionField;
    public Label descriptionLabel;
    public TextField averagePointField;
    public Label averagePointLabel;
    public TextField minimalPointField;
    public Label minimalPointLabel;
    public TextField yCoordinateField;
    public TextField xCoordinateField;
    public Label CoordinatesLabel;
    public TextField nameField;
    public Label nameLabel;

    public void addElementButton(ActionEvent event) {
        warningLabel.setTextFill(Color.color(1, 0, 0));
        warningLabel.setText("ho-ho you clicked the button!");
    }
}

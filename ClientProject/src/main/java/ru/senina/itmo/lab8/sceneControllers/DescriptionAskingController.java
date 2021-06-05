package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DescriptionAskingController {
    public Button applyButton;
    public TextArea descriptionTextArea;
    public Label enterDescriptionLabel;
    private String description;

    public String getDescription() {
        return description;
    }

    public void applyButtonClicked(ActionEvent event) {
        description = descriptionTextArea.getText();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

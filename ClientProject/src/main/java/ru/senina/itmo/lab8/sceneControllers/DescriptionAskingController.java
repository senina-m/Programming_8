package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.ClientMain;

public class DescriptionAskingController {
    public Button applyButton;
    public TextArea descriptionTextArea;
    public Label enterDescriptionLabel;
    private String description;

    @FXML
    public void initialize() {
        description = null;
        initLabels();
    }

    public String getDescription() {
        return description;
    }

    public void applyButtonClicked(ActionEvent event) {
        description = descriptionTextArea.getText();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private void initLabels() {
        applyButton.setText(ClientMain.getRB().getString("apply"));
        enterDescriptionLabel.setText(ClientMain.getRB().getString("enterDescription"));
    }
}

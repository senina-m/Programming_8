package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FileAskingController {
    public Label attachFilepathLabel;
    public TextField filepathField;
    public Button applyButton;
    private String filepath;

    public String getFilepath() {
        return filepath;
    }

    //fixme closing of the window

    public void applyButtonClicked(ActionEvent event) {
        filepath = filepathField.getText().trim();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

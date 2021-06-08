package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.ClientMain;

public class FileAskingController {
    public Label attachFilepathLabel;
    public TextField filepathField;
    public Button applyButton;
    private String filepath;

    public String getFilepath() {
        return filepath;
    }

    @FXML
    public void initialize() {
        filepath = null;
        initLabels();
    }

    private void initLabels() {
        attachFilepathLabel.setText(ClientMain.getRB().getString("copyFilepathHere"));
        applyButton.setText(ClientMain.getRB().getString("apply") );
    }

    public void applyButtonClicked(ActionEvent event) {
        filepath = filepathField.getText().trim();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}

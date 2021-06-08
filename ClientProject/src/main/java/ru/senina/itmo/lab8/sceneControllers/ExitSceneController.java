package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.ClientMain;


public class ExitSceneController {
    public Button cancelButton;
    public Button exitButton;
    public Label exitLabel;

    @FXML
    public void initialize() {
        initLabels();
    }

    public void cancelButtonClicked(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void exitButtonClicked() {
        System.exit(0);
    }

    private void initLabels() {
        cancelButton.setText(ClientMain.getRB().getString("cancel"));
        exitButton.setText(ClientMain.getRB().getString("exit"));
        exitLabel.setText(ClientMain.getRB().getString("doYouWantToExit"));
    }
}

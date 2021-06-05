package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class ExitSceneController {
    public Button cancelButton;
    public Button exitButton;

    public void cancelButtonClicked(ActionEvent event) {
        ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();
    }

    public void exitButtonClicked(ActionEvent event) {
        System.exit(0);
    }
}

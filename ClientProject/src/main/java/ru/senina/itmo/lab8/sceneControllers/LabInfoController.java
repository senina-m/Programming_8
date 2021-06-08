package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.ClientMain;
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
    public Label disciplineNameLabel;
    public Label difficultyValueLabel;
    public Label difficultyLabel;
    public Label descriptionLabel;
    public Label averagePointLabel;
    public Label minimalPointValueLabel;
    public Label minimalPointLabel;
    public Label titleElementLabel;
    public Label nameLabel;
    public Label nameValueLabel;
    public Label coordinatesLabel;
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
        initLabels();
    }

    private void initLabels() {
        titleElementLabel.setText(ClientMain.getRB().getString("elementValues"));
        nameLabel.setText(ClientMain.getRB().getString("name") + ":");
        coordinatesLabel.setText(ClientMain.getRB().getString("coordinates") + ":");
        xCoordinateValueLabel.setText(ClientMain.getRB().getString("coordinateX") + ":");
        yCoordinateValueLabel.setText(ClientMain.getRB().getString("coordinateY") + ":");
        minimalPointLabel.setText(ClientMain.getRB().getString("minimalPoint") + ":");
        averagePointLabel.setText(ClientMain.getRB().getString("averagePoint") + ":");
        descriptionLabel.setText(ClientMain.getRB().getString("description") + ":");
        difficultyLabel.setText(ClientMain.getRB().getString("difficulty") + ":");
        disciplineNameLabel.setText(ClientMain.getRB().getString("disciplineName") + ":");
        lectureHoursLabel.setText(ClientMain.getRB().getString("lectureHours") + ":");
        practiceHoursLabel.setText(ClientMain.getRB().getString("practiceHours") + ":");
        selfStudyHoursLabel.setText(ClientMain.getRB().getString("selfStudyHours") + ":");
        exitButton.setText(ClientMain.getRB().getString("exit"));

    }
}

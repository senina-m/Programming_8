package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.ClientMain;
import ru.senina.itmo.lab8.exceptions.InvalidArgumentsException;
import ru.senina.itmo.lab8.labwork.Coordinates;
import ru.senina.itmo.lab8.labwork.Difficulty;
import ru.senina.itmo.lab8.labwork.Discipline;
import ru.senina.itmo.lab8.labwork.LabWork;

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
    public Label disciplineNameLabel;
    public Label difficultyLabel;
    public TextArea descriptionField;
    public Label descriptionLabel;
    public TextField averagePointField;
    public Label averagePointLabel;
    public TextField minimalPointField;
    public Label minimalPointLabel;
    public TextField yCoordinateField;
    public TextField xCoordinateField;
    public Label coordinatesLabel;
    public TextField nameField;
    public Label nameLabel;
    public ChoiceBox difficulty;
    private LabWork element;

    public LabWork getElement() {
        return element;
    }

    @FXML
    public void initialize() {
        element = null;
        initLabels();
    }

    private void initLabels() {
        titleAddingElementLabel.setText(ClientMain.getRB().getString("enterElementValues"));
        nameLabel.setText(ClientMain.getRB().getString("name"));
        coordinatesLabel.setText(ClientMain.getRB().getString("coordinates"));
        minimalPointLabel.setText(ClientMain.getRB().getString("minimalPoint"));
        averagePointLabel.setText(ClientMain.getRB().getString("averagePoint"));
        descriptionLabel.setText(ClientMain.getRB().getString("description"));
        difficultyLabel.setText(ClientMain.getRB().getString("difficulty"));
        difficulty.setValue(ClientMain.getRB().getString("chooseDifficulty"));
        difficulty.getItems().add(0,ClientMain.getRB().getString("veryEasy"));
        difficulty.getItems().add(0,ClientMain.getRB().getString("normal"));
        difficulty.getItems().add(0,ClientMain.getRB().getString("veryHard"));
        difficulty.getItems().add(0,ClientMain.getRB().getString("insane"));
        difficulty.getItems().add(0,ClientMain.getRB().getString("hopeless"));
        disciplineNameLabel.setText(ClientMain.getRB().getString("disciplineName"));
        lectureHoursLabel.setText(ClientMain.getRB().getString("lectureHours"));
        practiceHoursLabel.setText(ClientMain.getRB().getString("practiceHours"));
        selfStudyHoursLabel.setText(ClientMain.getRB().getString("selfStudyHours"));
    }

    public void addElementButton(ActionEvent event) {
        try {
            element = createElement();
        } catch (InvalidArgumentsException e) {
            warningLabel.setText(e.getMessage());
            return;
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    private LabWork createElement() throws InvalidArgumentsException {
        warningLabel.setTextFill(Color.color(1, 0, 0));
        LabWork element = new LabWork();
        try {
            element.setName(nameField.getText());
        } catch (InvalidArgumentsException e){
            throw new InvalidArgumentsException(ClientMain.getRB().getString("nameCantBeEmptyWarning"));
        }
//--------------------------------------------------------------------------------
        try {
            int x = Integer.parseInt(xCoordinateField.getText());
            long y = Long.parseLong(yCoordinateField.getText());
            element.setCoordinates(new Coordinates(x, y));
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException(ClientMain.getRB().getString("coordinatesAreNumbersWarning"));
        } catch (InvalidArgumentsException e) {
            throw new InvalidArgumentsException(ClientMain.getRB().getString("coordinatesBoundsWarning"));
        }
//--------------------------------------------------------------------------------
        try {
            element.setMinimalPoint(Float.parseFloat(minimalPointField.getText()));
        } catch (NumberFormatException e) {
            minimalPointField.setText("");
            throw new InvalidArgumentsException(ClientMain.getRB().getString("minimalPointIsFloatWarning"));
        } catch (InvalidArgumentsException e) {
            minimalPointField.setText("");
            throw new InvalidArgumentsException(ClientMain.getRB().getString("minimalPointGreaterZeroWarning"));
        }
//--------------------------------------------------------------------------------
        try {
            element.setDescription(descriptionField.getText());
        } catch (InvalidArgumentsException e) {
            minimalPointField.setText("");
            throw new InvalidArgumentsException(ClientMain.getRB().getString("descriptionIsn'tEmptyWarning"));
        }
//--------------------------------------------------------------------------------
        try {
            element.setAveragePoint(Integer.parseInt(averagePointField.getText()));
        } catch (NumberFormatException e) {
            averagePointField.setText("");
            throw new InvalidArgumentsException(ClientMain.getRB().getString("averagePointIsIntWarning"));
        }catch (InvalidArgumentsException e){
            minimalPointField.setText("");
            throw new InvalidArgumentsException(ClientMain.getRB().getString("averagePointGreaterZeroWarning"));
        }

//--------------------------------------------------------------------------------
        try {
            element.setDifficulty(Difficulty.of(difficulty.getSelectionModel().getSelectedIndex()));
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentsException(ClientMain.getRB().getString("chooseDifficultyWarning"));
        }
//--------------------------------------------------------------------------------
        Discipline discipline = new Discipline();

        try{
            discipline.setName(disciplineNameField.getText());
        }catch (InvalidArgumentsException e){
            throw new InvalidArgumentsException(ClientMain.getRB().getString("disciplineCantBeEmptyWarning"));
        }
//--------------------------------------------------------------------------------
        try {
            discipline.setLectureHours(Long.parseLong(lectureHoursField.getText()));
        } catch (NumberFormatException e) {
            lectureHoursField.setText("");
            throw new InvalidArgumentsException(ClientMain.getRB().getString("lectureHoursIsLongWarning"));
        }catch (InvalidArgumentsException e){
            throw new InvalidArgumentsException(ClientMain.getRB().getString("lectureHoursGreaterZeroWarning"));
        }
//--------------------------------------------------------------------------------
        try {
            discipline.setPracticeHours(Integer.parseInt(practiceHoursField.getText()));
        } catch (NumberFormatException e) {
            practiceHoursField.setText("");
            throw new InvalidArgumentsException(ClientMain.getRB().getString("practiceStudyHoursIsIntWarning"));
        }catch (InvalidArgumentsException e){
            throw new InvalidArgumentsException(ClientMain.getRB().getString("practiceHoursGreaterZeroWarning"));
        }
//--------------------------------------------------------------------------------
        try {
            discipline.setSelfStudyHours(Integer.parseInt(selfStudyHoursField.getText()));
        } catch (NumberFormatException e) {
            selfStudyHoursField.setText("");
            throw new InvalidArgumentsException(ClientMain.getRB().getString("selfStudyHoursIsIntWarning"));
        }catch (InvalidArgumentsException e){
            throw new InvalidArgumentsException(ClientMain.getRB().getString("selfStudyHoursGreaterZeroWarning"));
        }
//--------------------------------------------------------------------------------
        element.setDiscipline(discipline);
        return element;
    }
}

package ru.senina.itmo.lab8.sceneControllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.InvalidArgumentsException;
import ru.senina.itmo.lab8.labwork.Coordinates;
import ru.senina.itmo.lab8.labwork.Difficulty;
import ru.senina.itmo.lab8.labwork.Discipline;
import ru.senina.itmo.lab8.labwork.LabWork;

//fixme убрать кашу из метода createElement
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
    public ChoiceBox difficulty;
    private LabWork element;

    public LabWork getElement() {
        return element;
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
            throw new InvalidArgumentsException("Name can't be empty!");
        }
//--------------------------------------------------------------------------------
        try {
            int x = Integer.parseInt(xCoordinateField.getText());
            long y = Long.parseLong(yCoordinateField.getText());
            element.setCoordinates(new Coordinates(x, y));
        } catch (NumberFormatException e) {
            throw new InvalidArgumentsException("first coordinate has to be int number and second has to be long number");
        } catch (InvalidArgumentsException e) {
            throw new InvalidArgumentsException("First coordinate has to be <= 74 and second >= -47");
        }
//--------------------------------------------------------------------------------
        try {
            element.setMinimalPoint(Float.parseFloat(minimalPointField.getText()));
        } catch (NumberFormatException e) {
            minimalPointField.setText("");
            throw new InvalidArgumentsException("minimal point has to be float number");
        } catch (InvalidArgumentsException e) {
            minimalPointField.setText("");
            throw new InvalidArgumentsException("minimal point has to be greater then 0");
        }
//--------------------------------------------------------------------------------
        try {
            element.setDescription(descriptionField.getText());
        } catch (InvalidArgumentsException e) {
            minimalPointField.setText("");
            throw new InvalidArgumentsException("description hasn't be empty");
        }
//--------------------------------------------------------------------------------
        try {
            element.setAveragePoint(Integer.parseInt(averagePointField.getText()));
        } catch (NumberFormatException e) {
            averagePointField.setText("");
            throw new InvalidArgumentsException("average point has to be int number");
        }catch (InvalidArgumentsException e){
            minimalPointField.setText("");
            throw new InvalidArgumentsException("average point has to be greater then 0");
        }

//--------------------------------------------------------------------------------
        try {
            element.setDifficulty(Difficulty.of(difficulty.getSelectionModel().getSelectedIndex()));
        } catch (IllegalArgumentException e) {
            throw new InvalidArgumentsException("choose difficulty");
        }
//--------------------------------------------------------------------------------
        Discipline discipline = new Discipline();

        try{
            discipline.setName(disciplineNameField.getText());
        }catch (InvalidArgumentsException e){
            throw new InvalidArgumentsException("discipline name can't be empty");
        }
//--------------------------------------------------------------------------------
        try {
            discipline.setLectureHours(Long.parseLong(lectureHoursField.getText()));
        } catch (NumberFormatException e) {
            lectureHoursField.setText("");
            throw new InvalidArgumentsException("lecture hours have to be a long number");
        }catch (InvalidArgumentsException e){
            throw new InvalidArgumentsException("lecture hours have to be greater then 0");
        }
//--------------------------------------------------------------------------------
        try {
            discipline.setPracticeHours(Integer.parseInt(practiceHoursField.getText()));
        } catch (NumberFormatException e) {
            practiceHoursField.setText("");
            throw new InvalidArgumentsException("lecture hours have to be a long number");
        }catch (InvalidArgumentsException e){
            throw new InvalidArgumentsException("practice hours have to be greater then 0");
        }
//--------------------------------------------------------------------------------
        try {
            discipline.setSelfStudyHours(Integer.parseInt(selfStudyHoursField.getText()));
        } catch (NumberFormatException e) {
            selfStudyHoursField.setText("");
            throw new InvalidArgumentsException("self-study hours have to be a long number");
        }catch (InvalidArgumentsException e){
            throw new InvalidArgumentsException("self-study hours have to be greater then 0");
        }
//--------------------------------------------------------------------------------
        element.setDiscipline(discipline);
        return element;
    }


}

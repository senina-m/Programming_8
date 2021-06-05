package ru.senina.itmo.lab8.sceneControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.parser.JsonParser;

import java.io.IOException;
import java.util.logging.Level;

public class RegisterSceneController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ClientNetConnector netConnector = new ClientNetConnector();
    private final JsonParser<CommandResponse> responseParser = new JsonParser<>(objectMapper, CommandResponse.class);
    private final JsonParser<CommandArgs> commandArgsJsonParser = new JsonParser<>(objectMapper, CommandArgs.class);
    public Button switchToLoginButton;
    public Label loginLabel;
    public Label passwordLabel;
    public Label repeatPasswordLabel;
    public TextField logInField;
    public PasswordField passwordField;
    public PasswordField repeatPasswordField;
    public Button signUpButton;
    public Label warningLabel;


    //todo: adding titles to buttons here
    @FXML
    private void signUpButtonClicked(ActionEvent event) {
        signUpButton.setText("Connecting to server...");
        String login = logInField.getText();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();
        checkLoginPassword(login, password, repeatPassword);
        CommandArgs registerCommand = new CommandArgs("register", new String[]{"register", login, Encryptor.encrypt(password)});
        registerCommand.setLogin(login);
        tryToConnect(ClientMain.HOST, ClientMain.PORT, ClientMain.ATTEMPTS_TO_CONNECT, ClientMain.DELAY_TO_CONNECT);
        netConnector.sendMessage(commandArgsJsonParser.fromObjectToString(registerCommand));
        CommandResponse authResponse = responseParser.fromStringToObject(netConnector.receiveMessage());
        netConnector.stopConnection();
        if (authResponse.getCode().equals(Status.REGISTRATION_FAIL)) {
            warningLabel.setText("User with such login already exist! Try to register again!");
        } else {
            warningLabel.setTextFill(Color.color(0, 1, 0));
            warningLabel.setText("You was successfully registered!");
            ClientMain.TOKEN = authResponse.getResponse();
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(GraphicsMain.getTableSceneParent()));
            } catch (IOException e) {
                ClientLog.log(Level.WARNING, "There is no required resource in method start() in signUpButtonClicked!");
                e.printStackTrace();
            }
            //todo: на следующем экране написать в терминале приглашение ко вводу
        }
        signUpButton.setText("sign up");
    }

    private void checkLoginPassword(String login, String password, String repeatPassword) {
        warningLabel.setTextFill(Color.color(1, 0, 0));
        if(password.length() < 8){
            warningLabel.setText("Your password is too short!");
        }
        if(!password.equals(repeatPassword)){
            warningLabel.setText("Passwords aren't equal!");
        }
        if(login.length() == 0){
            warningLabel.setText("You forgot to enter login");
        }
    }

    @FXML
    private void switchToLogInStage(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(GraphicsMain.getLoginSceneParent()));
        } catch (IOException e) {
            ClientLog.log(Level.WARNING, "There is no required resource in method start() in switchToLogInStage!");
            e.printStackTrace();
        }
    }

    public void tryToConnect(String host, int serverPort, int attempts, int delay) {
        LogInSceneController.tryToConnect(host, serverPort, attempts, delay, netConnector, warningLabel);
    }

}

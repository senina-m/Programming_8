package ru.senina.itmo.lab8.sceneControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import ru.senina.itmo.lab8.*;
import ru.senina.itmo.lab8.parser.JsonParser;
import ru.senina.itmo.lab8.stages.LoginStage;

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
    public ChoiceBox choiceBox;
    public Label askToLoginLabel;
    public Label registrationLabel;

    @FXML
    public void initialize() {
        initLabels();
    }

    private void initLabels() {
        passwordLabel.setText(ClientMain.getRB().getString("createPassword"));
        repeatPasswordLabel.setText(ClientMain.getRB().getString("repeatPassword"));
        choiceBox.setValue(ClientMain.getRB().getString("choiceBox"));
        choiceBox.getItems().add(0,ClientMain.getRB().getString("russianLanguage"));
        choiceBox.getItems().add(0,ClientMain.getRB().getString("englishLanguage"));
        choiceBox.getItems().add(0,ClientMain.getRB().getString("albanianLanguage"));
        choiceBox.getItems().add(0,ClientMain.getRB().getString("spanishLanguage"));
        choiceBox.getItems().add(0,ClientMain.getRB().getString("serbianLanguage"));
        loginLabel.setText(ClientMain.getRB().getString("login"));
        signUpButton.setText(ClientMain.getRB().getString("signUp"));
        askToLoginLabel.setText(ClientMain.getRB().getString("askToLogin"));
        switchToLoginButton.setText(ClientMain.getRB().getString("logIn"));
        registrationLabel.setText(ClientMain.getRB().getString("registration"));
    }


    //todo: adding titles to buttons here
    @FXML
    private void signUpButtonClicked(ActionEvent event) {
        String login = logInField.getText();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();
        checkLoginPassword(login, password, repeatPassword);
        CommandArgs registerCommand = new CommandArgs("register", new String[]{"register", login, Encryptor.encrypt(password)});
        registerCommand.setLocale(ClientMain.getLOCALE().toString());
        registerCommand.setLogin(login);
        tryToConnect(ClientMain.HOST, ClientMain.PORT, ClientMain.ATTEMPTS_TO_CONNECT, ClientMain.DELAY_TO_CONNECT);
        netConnector.sendMessage(commandArgsJsonParser.fromObjectToString(registerCommand));
        CommandResponse authResponse = responseParser.fromStringToObject(netConnector.receiveMessage());
        netConnector.stopConnection();
        if (authResponse.getCode().equals(Status.REGISTRATION_FAIL)) {
            warningLabel.setText(ClientMain.getRB().getString("suchUserAlreadyExistsWarning") +
                    ClientMain.getRB().getString("tryToRegisterAgain"));
        } else {
            warningLabel.setTextFill(Color.color(0, 1, 0));
            warningLabel.setText(ClientMain.getRB().getString("youWasSuccessfullyRegistered"));
            ClientMain.TOKEN = authResponse.getResponse();
            try {
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(new Scene(GraphicsMain.getTableSceneParent()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //todo: на следующем экране написать в терминале приглашение ко вводу
        }
    }

    private void checkLoginPassword(String login, String password, String repeatPassword) {
        warningLabel.setTextFill(Color.color(1, 0, 0));
        if(password.length() < 8){
            warningLabel.setText(ClientMain.getRB().getString("yourPasswordIsTooShortWarning"));
        }
        if(!password.equals(repeatPassword)){
            warningLabel.setText(ClientMain.getRB().getString("passwordsArentEqualWarning"));
        }
        if(login.length() == 0){
            warningLabel.setText(ClientMain.getRB().getString("youForgotToEnterLoginWarning"));
        }
    }

    @FXML
    private void switchToLogInStage(ActionEvent event) {
        LoginStage.start((Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    public void tryToConnect(String host, int serverPort, int attempts, int delay) {
        LogInSceneController.tryToConnect(host, serverPort, attempts, delay, netConnector, warningLabel);
    }

}

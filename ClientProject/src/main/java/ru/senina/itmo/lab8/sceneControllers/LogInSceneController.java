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
import ru.senina.itmo.lab8.exceptions.RefusedConnectionException;
import ru.senina.itmo.lab8.parser.JsonParser;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class LogInSceneController {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ClientNetConnector netConnector = new ClientNetConnector();
    private final JsonParser<CommandResponse> responseParser = new JsonParser<>(objectMapper, CommandResponse.class);
    private final JsonParser<CommandArgs> commandArgsJsonParser = new JsonParser<>(objectMapper, CommandArgs.class);
    public Label loginLabel;
    public Label passwordLabel;
    public TextField logInField;
    public PasswordField passwordField;
    public Button logInButton;
    public Label warningLabel;
    public Button switchToSignUpButton;
    public Label suggestToSignUpLabel;
    public Label authorizationLabel;
    public ChoiceBox<Language> choiceBox;
    private boolean isInitialized = false;

    @FXML
    public void initialize() {
        initLabels();
    }

    private void initLabels() {
        loginLabel.setText(ClientMain.getRB().getString("login"));
        passwordLabel.setText(ClientMain.getRB().getString("password"));
        switchToSignUpButton.setText(ClientMain.getRB().getString("signUp"));
        logInButton.setText(ClientMain.getRB().getString("logIn"));
        suggestToSignUpLabel.setText(ClientMain.getRB().getString("suggestToSignUpLabel") + "?");
        authorizationLabel.setText(ClientMain.getRB().getString("authorizationLabel"));
        choiceBox.setValue(ClientMain.getCurrentLanguage());
        choiceBox.getItems().add(0, Language.ENGLISH);
        choiceBox.getItems().add(1, Language.RUSSIAN);
        choiceBox.getItems().add(2, Language.SERBIAN);
        choiceBox.getItems().add(3, Language.ALBANIAN);
        choiceBox.getItems().add(4, Language.SPANISH);
        choiceBox.getItems().add(5, Language.TROLL);
        isInitialized = true;
    }

    @FXML
    private void logInButtonClicked(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
//        scene.setCursor(Cursor.WAIT);
//        logInButton.setText("Connecting to server...");
        String login = logInField.getText();
        String password = passwordField.getText();
        if (password.length() == 0 || login.length() == 0) {
            warningLabel.setText(ClientMain.getRB().getString("forgotToEnterPasswordWarning"));
            warningLabel.setTextFill(Color.color(1, 0, 0));
            return;
        }
        CommandArgs loginCommand = new CommandArgs("authorize", new String[]{"authorize", login, Encryptor.encrypt(password)});
        loginCommand.setLogin(login);
        loginCommand.setLocale(ClientMain.getLOCALE().toString());
        tryToConnect(ClientMain.HOST, ClientMain.PORT, ClientMain.ATTEMPTS_TO_CONNECT, ClientMain.DELAY_TO_CONNECT);
        netConnector.sendMessage(commandArgsJsonParser.fromObjectToString(loginCommand));
        CommandResponse authResponse = responseParser.fromStringToObject(netConnector.receiveMessage());
        netConnector.stopConnection();
        if (authResponse.getCode().equals(Status.REGISTRATION_FAIL)) {
            warningLabel.setText(ClientMain.getRB().getString("incorrectLoginWarning"));
        } else {
            ClientMain.TOKEN = authResponse.getResponse();
            GraphicsMain.getTableScene((Stage) scene.getWindow());
        }
    }

    @FXML
    private void switchToSignUpStage(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(GraphicsMain.getSceneParent("/fxmls/registrerScene.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tryToConnect(String host, int serverPort, int attempts, int delay) {
        tryToConnect(host, serverPort, attempts, delay, netConnector, warningLabel);
    }

    //todo: костыль какой-то с этим методом надо бы вынести его в отдельный класс
    static void tryToConnect(String host, int serverPort, int attempts, int delay, ClientNetConnector netConnector, Label warningLabel) {
        for (int i = 0; i < attempts; i++) {
            try {
                netConnector.startConnection(host, serverPort);
                return;
            } catch (RefusedConnectionException e) {
                warningLabel.setText(ClientMain.getRB().getString("waitToServerAnswerWarning1") + delay + ClientMain.getRB().getString("waitToServerAnswerWarning2"));
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } catch (InterruptedException ex) {
                    ClientLog.log(Level.WARNING, "EXCEPTION in tryToConnect " + ex);
                }
            }
        }
        warningLabel.setText(ClientMain.getRB().getString("cantConnectToServerWarning"));
        netConnector.stopConnection();
        System.exit(0);
    }

    public void changeLocale(ActionEvent event) {
        Language value = choiceBox.getValue();
        if(value != null && isInitialized) {
            ClientMain.setLOCALE(value.getLocale(), value);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            GraphicsMain.setTitle(stage, ClientMain.getRB().getString("appTitle"));
            try {
                stage.setScene(new Scene(GraphicsMain.getSceneParent("/fxmls/loginScene.fxml")));
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}

package ru.senina.itmo.lab8.sceneControllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
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

    @FXML
    private void logInButtonClicked(ActionEvent event) {
        Scene scene = ((Node) event.getSource()).getScene();
        scene.setCursor(Cursor.WAIT);
        logInButton.setText("Connecting to server...");
        String login = logInField.getText();
        String password = passwordField.getText();
        if (password.length() == 0 || login.length() == 0) {
            warningLabel.setText("You forgot to enter your login or password!");
            warningLabel.setTextFill(Color.color(1, 0, 0));
        }
        CommandArgs loginCommand = new CommandArgs("authorize", new String[]{"authorize", login, Encryptor.encrypt(password)});
        loginCommand.setLogin(login);
        tryToConnect(ClientMain.HOST, ClientMain.PORT, ClientMain.ATTEMPTS_TO_CONNECT, ClientMain.DELAY_TO_CONNECT);
        netConnector.sendMessage(commandArgsJsonParser.fromObjectToString(loginCommand));
        CommandResponse authResponse = responseParser.fromStringToObject(netConnector.receiveMessage());
        netConnector.stopConnection();
        if (authResponse.getCode().equals(Status.REGISTRATION_FAIL)) {
            warningLabel.setText("Your password or login isn't correct! Try to log in again!");
        } else {
            warningLabel.setTextFill(Color.color(0, 1, 0));
            warningLabel.setText("You are in!");
            ClientMain.TOKEN = authResponse.getResponse();
            try {
                Stage stage = (Stage) scene.getWindow();
                stage.setScene(new Scene(GraphicsMain.getTableSceneParent()));
            } catch (IOException e) {
                ClientLog.log(Level.WARNING, "There is no required resource in method start() in logInButtonClicked!");
                e.printStackTrace();
            }
            //todo: на следующем экране написать в терминале приглашение ко вводу
            logInButton.setText("log in");
        }
    }

    @FXML
    private void switchToSignUpStage(ActionEvent event) {
        try {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(GraphicsMain.getRegisterSceneParent()));
        } catch (IOException e) {
            ClientLog.log(Level.WARNING, "There is no required resource in method start() in switchToSignUpStage!");
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
                warningLabel.setText("Sorry, now server is not available! We will try to reconnect in " + delay + " seconds!");
                try {
                    TimeUnit.SECONDS.sleep(delay);
                } catch (InterruptedException ex) {
                    ClientLog.log(Level.WARNING, "EXCEPTION in tryToConnect " + ex);
                }
            }
        }
        warningLabel.setText("Can't connect to server! Try again later!");
        netConnector.stopConnection();
        System.exit(0);
    }

}

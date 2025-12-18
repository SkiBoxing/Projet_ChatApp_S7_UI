package com.webapp.projet_webapp_ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

public class ClientController {
    @FXML
    private TextField pseudoTextField;

    @FXML
    private TextField messageTextField;

    @FXML
    private TextArea messagesTextArea;

    @FXML
    private Label connectStatusLabel;

    @FXML
    private Button connectButton;

    @FXML
    private Button sendButton;

    private ClientService clientService = new ClientService();

    @FXML
    protected void onConnectButtonClick() {
        try {
            if (!clientService.isConnected()) {
                if (!pseudoTextField.getText().isEmpty()) {
                    clientService.connect(pseudoTextField.getText(), msg -> javafx.application.Platform.runLater(() -> messagesTextArea.appendText(msg + "\n")));

                    connectStatusLabel.setText("Connectée");
                    connectStatusLabel.setTextFill(Paint.valueOf("00ff00"));
                    connectButton.setText("Se déconnecter");
                    pseudoTextField.setDisable(true);
                    messagesTextArea.setDisable(false);
                    messageTextField.setDisable(false);
                    sendButton.setDisable(false);
                } else {
                    infoPopUp("Un pseudo est obligatoire pour se connecter");
                }
            } else {
                clientService.disconnect();

                connectStatusLabel.setText("Déconnectée");
                connectStatusLabel.setTextFill(Paint.valueOf("ff0000"));
                messagesTextArea.setText("");
                connectButton.setText("Se connecter");
                pseudoTextField.setDisable(false);
                messagesTextArea.setDisable(true);
                messageTextField.setDisable(true);
                sendButton.setDisable(true);
            }
        } catch (Exception e) {
            errorPopUp(e.getMessage());
        }
    }

    @FXML
    protected void onSendButtonClick() {
        try {
            clientService.sendMessage(messageTextField.getText());
            messageTextField.clear();
        } catch (Exception e) {
            errorPopUp(e.getMessage());
        }
    }

    public static void infoPopUp(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void errorPopUp(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public ClientService getClientService() {
        return clientService;
    }
}

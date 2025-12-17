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

    private ClientService clientService = new ClientService();

    @FXML
    protected void onConnectButtonClick() {
        try {
            if (!clientService.isConnected() && !pseudoTextField.getText().isEmpty()) {
                clientService.connect(pseudoTextField.getText(), messagesTextArea);

                connectStatusLabel.setText("Connectée");
                connectStatusLabel.setTextFill(Paint.valueOf("00ff00"));
                connectButton.setText("Se déconnecter");
            } else {
                clientService.disconnect();

                connectStatusLabel.setText("Déconnectée");
                connectStatusLabel.setTextFill(Paint.valueOf("ff0000"));
                messagesTextArea.setText("");
                connectButton.setText("Se connecter");
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

    public static void errorPopUp(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

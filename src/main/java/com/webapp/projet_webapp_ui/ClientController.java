package com.webapp.projet_webapp_ui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

public class ClientController {
    // Definition des elements UI
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

    // Recuperation de ClientService
    private ClientService clientService = new ClientService();

    // Fonction du bouton de connexion
    @FXML
    protected void onConnectButtonClick() {
        try {
            // Si le client n'est pas connecte
            if (!clientService.isConnected()) {
                // Si un pseudo est renseigne
                if (!pseudoTextField.getText().isEmpty()) {
                    // Lance la connection et defini la fonction d'ecriture de ClientThread
                    clientService.connect(pseudoTextField.getText(), msg -> javafx.application.Platform.runLater(() -> messagesTextArea.appendText(msg + "\n")));

                    // Initialise l'UI lorsque le client est connecte
                    connectStatusLabel.setText("Connectée");
                    connectStatusLabel.setTextFill(Paint.valueOf("00ff00"));
                    connectButton.setText("Se déconnecter");
                    pseudoTextField.setDisable(true);
                    messagesTextArea.setDisable(false);
                    messageTextField.setDisable(false);
                    sendButton.setDisable(false);
                } else {
                    // Informe que un pseudo est obligatoire
                    infoPopUp("Un pseudo est obligatoire pour se connecter");
                }
            } else {
                // Deconnexion de client
                clientService.disconnect();

                // Initialise l'UI lorsque le client est deconnecte
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
            // Informe d'une erreur
            errorPopUp(e.getMessage());
        }
    }

    // Fonction du bouton d'envoi de message
    @FXML
    protected void onSendButtonClick() {
        try {
            // Si le message n'est pas vide
            if (!messageTextField.getText().isEmpty()) {
                // Envoi d'un message au serveur
                clientService.sendMessage(messageTextField.getText());

                // Efface le message envoye
                messageTextField.clear();
            }
        } catch (Exception e) {
            // Informe d'une erreur
            errorPopUp(e.getMessage());
        }
    }

    // Fonction de Pop-Up pour informer
    public static void infoPopUp(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Fonction de Pop-Up pour signaler ou souci
    public static void errorPopUp(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Fonction de recuperation de clientService
    public ClientService getClientService() {
        return clientService;
    }
}

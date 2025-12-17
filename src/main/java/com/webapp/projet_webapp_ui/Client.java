package com.webapp.projet_webapp_ui;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Client extends Application {
    DataOutputStream Out;
    DataInputStream In;
    Socket client;

    @FXML
    private TextField pseudoTextField;

    @FXML
    private TextField messageTextField;

    @FXML
    private TextArea messagesTextArea;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("V1.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Demo");
        stage.setScene(scene);
        stage.show();
    }

    public void connect() {
        if (!pseudoTextField.getText().isEmpty()) {
            try {
                this.client = new Socket("localhost", 7777);

                // TODO : Changer apparence bouton connect

                this.Out = new DataOutputStream(client.getOutputStream());
                this.In = new DataInputStream(client.getInputStream());

                // Lecture des messages
                ClientThread service = new ClientThread(client, messagesTextArea);
                service.start();

                // Ecriture du pseudo
                String pseudo = pseudoTextField.getText();
                this.sendMessage(pseudo);

            }
            catch (Exception e) {
                this.errorPopUp(e.getMessage());
            }
        }
    }

    public void disconnect() {
        if (client.isConnected()) {
            try {
                In.close();
                Out.close();
                client.close();
                client = null;
            }
            catch (IOException e) {
                this.errorPopUp(e.getMessage());
            }
        }
    }

    public void sendMessage(String msg) {
        if (client.isConnected()) {
            try {
                Out.writeUTF(msg);
                Out.flush();
            }
            catch (IOException e) {
                this.errorPopUp(e.getMessage());
            }
        }
    }

    @FXML
    protected void onConnectButtonClick() {
        if (client == null) {
            connect();
        } else {
            disconnect();
        }
    }

    @FXML
    protected void onSendButtonClick() {
        if (client.isConnected()) {
            this.sendMessage(messageTextField.getText());
        }
    }

    public void errorPopUp(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setContentText(message);

        alert.showAndWait();
    }
}
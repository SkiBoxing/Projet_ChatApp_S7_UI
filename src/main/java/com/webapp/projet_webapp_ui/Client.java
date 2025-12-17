package com.webapp.projet_webapp_ui;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import static java.lang.Math.floor;

public class Client {
    DataOutputStream Out;
    DataInputStream In;
    Socket client;

    @FXML
    private TextField pseudoTextField;

    @FXML
    private TextField messageTextField;

    @FXML
    private TextArea messagesTextArea;

    public void connect() {
        if (!pseudoTextField.getText().equals("")) {
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
            catch (UnknownHostException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("ERROR");
                alert.setContentText(e.getMessage());

                alert.showAndWait();
            }
            catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("ERROR");
                alert.setContentText(e.getMessage());

                alert.showAndWait();
            }
        }
    }

    public void disconnect() {
        if (client.isConnected()) {
            try {
                In.close();
                Out.close();
                client.close();
            }
            catch (IOException e) {}
        }
    }

    public void sendMessage(String msg) {
        if (client.isConnected()) {
            try {
                Out.writeUTF(msg);
                Out.flush();
            }
            catch (IOException e) {}
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
}
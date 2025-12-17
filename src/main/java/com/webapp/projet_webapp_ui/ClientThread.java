package com.webapp.projet_webapp_ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientThread extends Thread {
    private Socket clientSocket;

    private DataInputStream is;
    private DataOutputStream os;

    @FXML
    private TextArea messagesTextArea;

    public ClientThread(Socket clientSocket, TextArea messagesTextArea) {
        try {
            // Reception du socket du client
            this.clientSocket = clientSocket;
            this.messagesTextArea = messagesTextArea;

            // Récuperation des Data Stream pour le client lié au socket
            this.is = new DataInputStream(clientSocket.getInputStream());
            this.os = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        super.run();

        try {
            while (true) {
                String message = is.readUTF();

                messagesTextArea.appendText(message + "\n");
            }
        } catch (IOException e) {
            System.out.println("Client déconnecté : " + clientSocket.getInetAddress());
        } finally {
            try {
                is.close();
                os.close();
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }
}

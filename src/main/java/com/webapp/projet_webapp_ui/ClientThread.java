package com.webapp.projet_webapp_ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class ClientThread extends Thread {
    private Socket clientSocket;

    private DataInputStream is;
    private DataOutputStream os;

    private Consumer<String> messageHandler;

    public ClientThread(Socket clientSocket, Consumer<String> messageHandler) {
        try {
            // Reception du socket du client
            this.clientSocket = clientSocket;
            this.messageHandler = messageHandler;

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
                messageHandler.accept(message);
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

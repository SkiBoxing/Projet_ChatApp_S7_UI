package com.webapp.projet_webapp_ui;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientService {
    private Socket client;
    private DataOutputStream out;
    private DataInputStream in;
    private ClientThread listener;

    public void connect(String pseudo, TextArea messagesTextArea) throws IOException {
        this.client = new Socket("localhost", 7777);
        this.out = new DataOutputStream(client.getOutputStream());
        this.in = new DataInputStream(client.getInputStream());

        this.listener = new ClientThread(this.client, messagesTextArea);
        listener.start();

        sendMessage(pseudo);
    }

    public void sendMessage(String msg) throws IOException {
        if (client != null && client.isConnected()) {
            out.writeUTF(msg);
            out.flush();
        }
    }

    public void disconnect() throws IOException {
        if (this.client.isConnected()) {
            try {
                in.close();
                out.close();
                client.close();
                client = null;
            }
            catch (IOException e) {
                ClientController.errorPopUp(e.getMessage());
            }
        }
    }

    public boolean isConnected() {
        return client != null && client.isConnected();
    }

    public Socket getSocket() {
        return client;
    }
}

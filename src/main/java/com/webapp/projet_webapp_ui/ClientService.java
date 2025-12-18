package com.webapp.projet_webapp_ui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class ClientService {
    private Socket client;
    private DataOutputStream out;
    private DataInputStream in;

    public void connect(String pseudo, Consumer<String> messageHandler) throws IOException {
        this.client = new Socket("localhost", 7777);
        this.out = new DataOutputStream(client.getOutputStream());
        this.in = new DataInputStream(client.getInputStream());

        ClientThread listener = new ClientThread(this.client, messageHandler);
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
            in.close();
            out.close();
            client.close();
            client = null;
        }
    }

    public boolean isConnected() {
        return client != null && client.isConnected();
    }
}

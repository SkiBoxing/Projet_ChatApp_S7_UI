package com.webapp.projet_webapp_ui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class ClientService {
    // Initialisation des variables
    private Socket client;
    private DataOutputStream out;
    private DataInputStream in;

    // Fonction de connexion au serveur
    public void connect(String pseudo, Consumer<String> messageHandler) throws IOException {
        // Creation de la connexion Socket
        this.client = new Socket("localhost", 7777);

        // Initialisation des flux de donnees
        this.out = new DataOutputStream(client.getOutputStream());
        this.in = new DataInputStream(client.getInputStream());

        // Creation de ClientThread pour la lecture des messages recu
        ClientThread listener = new ClientThread(this.client, messageHandler);
        listener.start();

        // Informe le serveur du pseudo
        sendMessage(pseudo);
    }

    // Fonction d'envoi d'un message
    public void sendMessage(String msg) throws IOException {
        // Si le client est connecte
        if (client != null && client.isConnected()) {
            // Envoie au serveur le message
            out.writeUTF(msg);
            out.flush();
        }
    }

    // Fonction de deconnexion
    public void disconnect() throws IOException {
        // Si le client est connecte
        if (this.client.isConnected()) {
            // Fermeture de la connexion
            in.close();
            out.close();
            client.close();
            client = null;
        }
    }

    // Fonction informant si le client est connecte
    public boolean isConnected() {
        return client != null && client.isConnected();
    }
}

package com.webapp.projet_webapp_ui;

import java.io.*;
import java.net.Socket;
import java.util.function.Consumer;

public class ClientThread extends Thread {
    // Initialisation des variables
    private Socket clientSocket;

    private DataInputStream is;
    private DataOutputStream os;

    private Consumer<String> messageHandler;

    // Constructeur de ClientThread
    public ClientThread(Socket clientSocket, Consumer<String> messageHandler) {
        try {
            // Reception du socket du client
            this.clientSocket = clientSocket;

            // Recuperation des flux de donnees
            this.is = new DataInputStream(clientSocket.getInputStream());
            this.os = new DataOutputStream(clientSocket.getOutputStream());

            // Reception de la fonction a executer pour l'affichage des messages dans l'UI
            this.messageHandler = messageHandler;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fonction principale de ClientThread
    @Override
    public void run() {
        // Lancement du Thread
        super.run();

        try {
            // Boucle infini
            while (true) {
                // Reception des messages recu du serveur
                String message = is.readUTF();

                // Execution de la fonction d'ecriture des messages dans l'UI
                messageHandler.accept(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Fermeture de la connexion
                is.close();
                os.close();
                clientSocket.close();
            } catch (IOException ignored) {}
        }
    }
}

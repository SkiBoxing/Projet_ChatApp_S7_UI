package com.webapp.projet_webapp_ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {
    // Recuperation de ClientService
    private ClientService clientService;

    // Fonction de lqncement de l'qpplicqtion
    @Override
    public void start(Stage stage) throws Exception {
        // Chargement de lq vue FXML
        FXMLLoader loader = new FXMLLoader(
                ClientApplication.class.getResource("view.fxml")
        );
        Scene scene = new Scene(loader.load());
        stage.setTitle("ChatApp");
        stage.setScene(scene);
        stage.setResizable(false);

        // Recuperation de ClientService afin de pouvoir correctement fermer l'application
        ClientController controller = loader.getController();
        this.clientService = controller.getClientService();

        // Affiche l'application
        stage.show();
    }

    // Fonction de fermeture de l'application
    @Override
    public void stop() {
        // Si le client est connecte au serveur
        if (clientService != null && clientService.isConnected()) {
            try {
                // Ferme la connexion
                clientService.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package com.webapp.projet_webapp_ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {
    private ClientService clientService;

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                ClientApplication.class.getResource("view.fxml")
        );
        Scene scene = new Scene(loader.load());
        stage.setTitle("ChatApp");
        stage.setScene(scene);
        stage.setResizable(false);

        ClientController controller = loader.getController();
        this.clientService = controller.getClientService();

        stage.show();
    }

    @Override
    public void stop() {
        if (clientService != null && clientService.isConnected()) {
            try {
                clientService.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

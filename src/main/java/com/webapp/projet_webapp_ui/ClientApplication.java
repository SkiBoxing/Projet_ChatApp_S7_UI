package com.webapp.projet_webapp_ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                ClientApplication.class.getResource("V2.fxml")
        );
        Scene scene = new Scene(loader.load());
        stage.setTitle("ChatApp");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}

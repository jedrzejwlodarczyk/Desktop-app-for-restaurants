package com.example.restaurant_app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeStage {

    public void newStage(Stage stage, String resourceName) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource(resourceName));
        Scene scene = new Scene(fxmlLoader.load(),1920,1080);
        stage.setTitle("Restaurant");
        stage.setScene(scene);
        stage.setResizable(false);
    }


}

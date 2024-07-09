package com.example.restaurant_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCodeCombination;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import java.sql.Connection;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("loginPanel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),1920,1080);
        stage.setTitle("Restaurant");
        stage.setScene(scene);
        stage.setResizable(false);


        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}
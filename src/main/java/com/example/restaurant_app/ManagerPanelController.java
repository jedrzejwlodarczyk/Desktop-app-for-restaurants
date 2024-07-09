package com.example.restaurant_app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerPanelController {
    @FXML
    private Button reservationButton;
    @FXML
    private Button acivateButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button sendTaskPanel;

    @FXML
    void acivateAccount(MouseEvent event) throws IOException {
        ChangeStage changeStage = new ChangeStage();
        Stage stage = (Stage) acivateButton.getScene().getWindow();
        changeStage.newStage(stage,"accountActivationPanel.fxml");
    }

    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void logOut(MouseEvent event) throws IOException {
        ChangeStage changeStage = new ChangeStage();
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        changeStage.newStage(stage,"loginPanel.fxml");
    }

    @FXML
    void createReservation(MouseEvent event) throws IOException {
        ChangeStage changeStage = new ChangeStage();
        Stage stage = (Stage) reservationButton.getScene().getWindow();
        changeStage.newStage(stage,"reservationsPanel.fxml");
    }

    @FXML
    void sendTask(MouseEvent event) throws IOException {
        ChangeStage changeStage = new ChangeStage();
        Stage stage = (Stage) sendTaskPanel.getScene().getWindow();
        changeStage.newStage(stage,"taskSendingPanel.fxml");
    }

}

package com.example.restaurant_app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateAccountController implements Initializable {

    @FXML
    private ChoiceBox<String> getPosition;
    @FXML
    private Text errorMessage;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginInput;

    @FXML
    private TextField nameInput;

    @FXML
    private PasswordField passwordInput;

    @FXML
    private TextField phoneNumberInput;

    @FXML
    private TextField surnameInput;


    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getPosition.getItems().addAll("manager","cook","waiter");
    }

    @FXML
    void checkLoginData(MouseEvent event) throws SQLException, IOException {
        String name = nameInput.getText();
        String surname = surnameInput.getText();
        String login = loginInput.getText();
        String password = passwordInput.getText();
        String phoneNumber = phoneNumberInput.getText();
        String position = getPosition.getValue();

        if (!name.isEmpty() && !surname.isEmpty() && !login.isEmpty() && !password.isEmpty() && !phoneNumber.isEmpty() && getPosition.getValue() != null){
            DataBaseConnection connectNow = new DataBaseConnection();
            Connection connection = connectNow.getConnection();
            String insertData = "INSERT INTO `users` (`name`, `surname`, `login`, `phone_number`, `password`, `activity`, `position`) VALUES (?, ?, ?, ?, ?, 'inactive', ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertData);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, login);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, position);

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ChangeStage changeStage = new ChangeStage();
                Stage stage = (Stage) loginButton.getScene().getWindow();
                changeStage.newStage(stage, "loginPanel.fxml");
            } else {
                errorMessage.setText("Failed to create account.");
                errorMessage.setVisible(true);
            }

            ChangeStage changeStage = new ChangeStage();
            Stage stage = (Stage) loginButton.getScene().getWindow();
            changeStage.newStage(stage,"loginPanel.fxml");

            connection.close();
        }
        else{
            errorMessage.setVisible(true);
        }


    }
}

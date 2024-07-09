package com.example.restaurant_app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AccountActivateController implements Initializable {

    @FXML
    private ListView<String> listOfAccounts;
    @FXML
    private Button logOutButton;

    @FXML
    void acivateAccount(MouseEvent event) throws SQLException {
        int selectedID = listOfAccounts.getSelectionModel().getSelectedIndex();
        String selectedItem = listOfAccounts.getSelectionModel().getSelectedItem();
        String[] parts = selectedItem.split(" ");
        String name = parts[0];
        String surname = parts[1];
        String position = parts[2];


        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();

        String activatingAccount = "UPDATE users SET activity = 'active' WHERE name = ? AND surname = ? AND position = ? ;";
        PreparedStatement preparedStatement = connection.prepareStatement(activatingAccount);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,surname);
        preparedStatement.setString(3,position);

        preparedStatement.executeUpdate();

        listOfAccounts.getItems().remove(selectedID);

        connection.close();


    }

    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        ChangeStage changeStage = new ChangeStage();
        Stage stage = (Stage) logOutButton.getScene().getWindow();
        changeStage.newStage(stage,"managerPanel.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();

        String inactiveAccounts = "SELECT name, surname, position FROM users WHERE activity = 'inactive';";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(inactiveAccounts);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String position = resultSet.getString("position");

                listOfAccounts.getItems().add(name + " " + surname + " " + position);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

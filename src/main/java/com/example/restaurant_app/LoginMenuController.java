package com.example.restaurant_app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class LoginMenuController {

    @FXML
    private Text errorMessage;

    @FXML
    private Button loginButton;
    @FXML
    private Text textToCreateAccount;

    @FXML
    private TextField loginInput;
    @FXML
    private Text inactiveMassage;

    @FXML
    private PasswordField passwordInput;

    @FXML
    void checkLoginData(MouseEvent event) throws SQLException, IOException {
        String username = loginInput.getText();
        String password = passwordInput.getText();

        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();

        String verifyLogin = "SELECT activity, position FROM users WHERE login = ? AND password = ? ;";

        PreparedStatement preparedStatement = connection.prepareStatement(verifyLogin);
        preparedStatement.setString(1,username);
        preparedStatement.setString(2,password);

        ResultSet resultSet = preparedStatement.executeQuery();



        if (resultSet.next()){
            String activity = resultSet.getString("activity");
            System.out.println(activity);
            if (activity.equals("active")){
                String position = resultSet.getString("position");
                ChangeStage changeStage = new ChangeStage();
                Stage stage = (Stage) loginButton.getScene().getWindow();
                if(position.equals("manager")){
                    changeStage.newStage(stage,"managerPanel.fxml");
                }
            }else {
                inactiveMassage.setVisible(true);
            }
        } else {
            errorMessage.setVisible(true);
        }

        connection.close();
    }
    @FXML
    void createAccount(MouseEvent event) throws IOException {

        Stage stage = (Stage) textToCreateAccount.getScene().getWindow();
        ChangeStage changeStage = new ChangeStage();
        changeStage.newStage(stage,"createAccount.fxml");
        stage.show();
    }

    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    }

}

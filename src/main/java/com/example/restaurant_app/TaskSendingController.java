package com.example.restaurant_app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.ResourceBundle;

public class TaskSendingController implements Initializable {

    @FXML
    private Button backButton;

    @FXML
    private Button detailsButton;

    @FXML
    private TextArea messageInput;

    @FXML
    private ChoiceBox<String> recipient;

    @FXML
    private TextField subjectInput;

    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        ChangeStage changeStage = new ChangeStage();
        Stage stage = (Stage) backButton.getScene().getWindow();
        changeStage.newStage(stage,"managerPanel.fxml");
    }

    @FXML
    void sendTask(MouseEvent event) throws SQLException {
        if (messageInput.getText() != "" && subjectInput.getText() != "" && !recipient.getValue().isEmpty()){
            DataBaseConnection connectNow = new DataBaseConnection();
            Connection connection = connectNow.getConnection();
            String sendTask = "INSERT INTO tasks(`group_id`, `subject`, `message`) VALUES (?,?,?);";

            PreparedStatement preparedStatement = connection.prepareStatement(sendTask);
            preparedStatement.setInt(1, (recipient.getSelectionModel().getSelectedIndex() + 1));
            preparedStatement.setString(2, subjectInput.getText());
            preparedStatement.setString(3, messageInput.getText());

            preparedStatement.executeUpdate();

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();

        String recipies = "SELECT name FROM groups";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(recipies);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String name = resultSet.getString("name");
                recipient.getItems().add(name);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

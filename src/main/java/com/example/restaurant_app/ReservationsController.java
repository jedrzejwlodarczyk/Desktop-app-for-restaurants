package com.example.restaurant_app;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationsController implements Initializable {

    @FXML
    private AnchorPane bookingDetails;

    @FXML
    private TextField customerName;

    @FXML
    private Button detailsButton;

    @FXML
    private Text success;

    @FXML
    private ListView<String> listOfHours;

    @FXML
    private Button backButton;

    @FXML
    private ChoiceBox<Integer> numberOfGuests;

    @FXML
    private Button reservationButton;

    @FXML
    private DatePicker reservationDate;

    @FXML
    void showReservaionDetails(MouseEvent event) {
        if(!numberOfGuests.getValue().equals(null)){
            bookingDetails.setVisible(true);
        }
    }

    @FXML
    void exit(MouseEvent event) {
        System.exit(0);
    }

    @FXML
    void findAvaliableTimes(MouseEvent event) throws SQLException {
        LocalDate date = reservationDate.getValue();


        DataBaseConnection connectNow = new DataBaseConnection();
        Connection connection = connectNow.getConnection();

        LocalTime openingTime = LocalTime.of(10,0);
        LocalTime closingTime = LocalTime.of(21,0);

        LocalTime time = openingTime;

        String allPossibilities = "SELECT id FROM tables WHERE number_of_seats = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(allPossibilities);
        preparedStatement.setString(1, String.valueOf(numberOfGuests.getValue()));
        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> tableNumbers = new ArrayList<>();

        while (resultSet.next()){
            String tableNumber = resultSet.getString("id");
            tableNumbers.add(tableNumber);
        }


        while (!time.isAfter(closingTime)) {
            for (String tableNumber : tableNumbers) {
                listOfHours.getItems().add(time + " Table number: " + tableNumber);
            }
            time = time.plusHours(1);
        }

        String removeBusy = "SELECT table_id, time FROM reservations WHERE date = ?";
        PreparedStatement preparedStatement1 = connection.prepareStatement(removeBusy);
        preparedStatement1.setString(1, String.valueOf(date));

        ResultSet resultSet1 = preparedStatement1.executeQuery();

        while (resultSet1.next()){
            String table = resultSet1.getString("table_id");
            String reservationTime = resultSet1.getString("time");
            listOfHours.getItems().removeIf(booking -> booking == reservationTime + " Table number: " + table);
        }
        connection.close();


    }

    @FXML
    void goBack(MouseEvent event) throws IOException {
        ChangeStage changeStage = new ChangeStage();
        Stage stage = (Stage) backButton.getScene().getWindow();
        changeStage.newStage(stage,"managerPanel.fxml");
    }
    @FXML
    void closeDetailsWindow(MouseEvent event) {
        bookingDetails.setVisible(false);
        customerName.setText("");
        reservationDate.setValue(null);
        listOfHours.getItems().clear();
        success.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numberOfGuests.getItems().addAll(2,4,6);
    }

    @FXML
    void createReservation(MouseEvent event) throws SQLException {

        if(customerName.getText() != "" && reservationDate.getValue() != null && listOfHours.getSelectionModel().getSelectedItem() != null){
            DataBaseConnection connectNow = new DataBaseConnection();
            Connection connection = connectNow.getConnection();

            String selectedTable = listOfHours.getSelectionModel().getSelectedItem();
            String parts[] = selectedTable.split(" ");
            String tableNumber = parts[3];
            String time = parts[0];


            String addReservation = "INSERT INTO reservations (`table_id`, `name`, `date`, `time`) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(addReservation);
            preparedStatement.setInt(1, Integer.parseInt(tableNumber));
            preparedStatement.setString(2, customerName.getText());
            preparedStatement.setString(3, String.valueOf(reservationDate.getValue()));
            preparedStatement.setString(4, time);

            preparedStatement.executeUpdate();

            connection.close();

            success.setVisible(true);


        }

    }
}

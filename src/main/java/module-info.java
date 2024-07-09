module com.example.restaurant_app {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;

    requires java.sql;

    opens com.example.restaurant_app to javafx.fxml;
    exports com.example.restaurant_app;
}
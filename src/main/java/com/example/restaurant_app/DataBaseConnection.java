package com.example.restaurant_app;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    public Connection databaselink;

    public Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaselink = DriverManager.getConnection("jdbc:mysql://localhost/restaurant","root","root");
        }catch (Exception e){
            e.printStackTrace();
        }
        return databaselink;
    }
}

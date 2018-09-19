package com.itechart.example.Contacts;

import java.sql.*;

/**
 * Created by Admin on 07.09.2018
 */
public class JDBC {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/books_db";
        String username = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM reviews");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("review_comment"));
                //System.out.println(resultSet);
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

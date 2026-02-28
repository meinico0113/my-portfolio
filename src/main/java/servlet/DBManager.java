package servlet;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {

    public static Connection getConnection() throws Exception {

        String url = "jdbc:mysql://localhost:3306/myloginapp_db?useSSL=false&serverTimezone=Asia/Tokyo";
        String user = "root";
        String password = "koyu0104";

        Class.forName("com.mysql.cj.jdbc.Driver");

        return DriverManager.getConnection(url, user, password);
    }
}
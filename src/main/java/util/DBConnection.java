package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // データベースの住所（myloginapp_db を指定）
    private static final String URL = "jdbc:mysql://localhost:3306/myloginapp_db?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "koyu0104";

    public static Connection getConnection() throws SQLException {
        try {
            // MySQLに繋ぐためのドライバー（部品）を読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 接続を開始して、接続情報を返す
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (ClassNotFoundException e) {
            // ドライバーが見つからない場合のエラー
            throw new SQLException(e);
        }
    }
}
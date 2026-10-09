package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    // データベース
    private static final String URL = "jdbc:mysql://db:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Tokyo&allowPublicKeyRetrieval=true";
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
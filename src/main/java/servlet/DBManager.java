package servlet;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {

    public static Connection getConnection() throws Exception {

        // 接続先（URL）の設定
        // どこにある、何のDBに、どんな設定で繋ぐかを指定する「接続文字列（JDBC URL）」
        // ・localhost:3306 → 自分のパソコンの3306番ポートで動いている
        // ・myloginapp_db →「myloginapp_db」という名前のDBに繋ぐ
        // ・useUnicode=true&characterEncoding=UTF-8 → 日本語の文字化けを防ぐ設定
        // ・serverTimezone=Asia/Tokyo → 時間の基準を日本時間（東京）にする設定
        String url = "jdbc:mysql://db:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Tokyo&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "koyu0104";

        // JDBCドライバのロード
        // JavaとDBが会話できるようにするための「通訳（ドライバ）」をメモリ上に読み込んで有効化する
        Class.forName("com.mysql.cj.jdbc.Driver");

        // 接続の確立と返却
        // 設定したURL、ユーザー名、パスワードを使って実際にDBへ接続
        // 成功すると、DBへの「土管（パイプライン）」のようなオブジェクトが完成し、それをDAOへ送り返す
        return DriverManager.getConnection(url, user, password);
    }
}
package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/* @WebServlet("/LoginServlet") 
 ブラウザからこのプログラムを呼び出せるようになる */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // DB接続設定（環境に合わせて変更してください）
    private final String JDBC_URL = "jdbc:mysql://localhost:3306/myloginapp_db";
    private final String DB_USER = "root";
    private final String DB_PASS = "koyu0104";

    // ログイン画面の「送信」ボタンが押された時にここが動く
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 日本語が化けないようにする設定
        request.setCharacterEncoding("UTF-8");
        // index.jspの入力欄から値を取り出す
        String inputUser = request.getParameter("username");
        String inputPass = request.getParameter("password");

        // 文字数チェック
        if (inputUser != null && inputUser.length() >= 255) {
            response.sendRedirect("index.jsp?error=2");
            return;
        }

        Connection conn = null;
        try {
            // 1. JDBCドライバのロード（MySQL 8.0以降の場合）
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. DB接続
            conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

            // 3. SQLの準備（nameとpasswordが一致するユーザーを検索）
            // 画像のテーブル定義に合わせて「name」カラムを参照
            String sql = "SELECT role FROM users WHERE name = ? AND password = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputUser);
            pstmt.setString(2, inputPass);


            // 4. 実行
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // ログイン成功：DBから取得した role を確認
                String role = rs.getString("role");
                
                // セッションにユーザー情報を保存（必要に応じて）
                HttpSession session = request.getSession();
                session.setAttribute("user", inputUser);

                if ("admin".equals(role)) {
                    response.sendRedirect("AdminServlet");
                } else {
                    response.sendRedirect("user.jsp");
                }
            } else {
                // ログイン失敗（レコードが見つからない）
                response.sendRedirect("index.jsp?error=1");
            }

            // LoginServlet.java の catch 部分を一時的に修正
            } catch (Exception e) {
                e.printStackTrace();
            // エラー詳細を画面に出す（本番ではNGですが、デバッグ用に）
                response.getWriter().println("Error: " + e.getMessage());
                return; // sendRedirectはさせない
            } finally {
            // 接続を閉じる
            try { if (conn != null) conn.close(); } catch (Exception e) {}
            }
    }
}
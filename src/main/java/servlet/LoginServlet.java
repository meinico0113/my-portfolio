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

            // 3. SQLの準備（likesとnameも取得するように変更）
            String sql = "SELECT * FROM users WHERE name = ? AND password = ? AND is_deleted = 0";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, inputUser);
            pstmt.setString(2, inputPass);

            // 4. 実行
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // DBから必要な情報を取得
                int id = rs.getInt("id");
                String role = rs.getString("role");
                int likes = rs.getInt("likes");
                String name = rs.getString("name");

                // Accountモデル（JavaBeans）に詰め込む
                model.Account account = new model.Account();
                account.setId(id);
                account.setName(name);
                account.setRole(role);
                account.setLikes(likes);
                
                // セッションに「account」という名前でオブジェクトごと保存
                HttpSession session = request.getSession();
                session.setAttribute("account", account);

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
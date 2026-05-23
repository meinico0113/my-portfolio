package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SettingsServlet")
public class SettingsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        // セッションから現在のユーザー名を特定する
        HttpSession session = request.getSession();
        String currentUserName = (String) session.getAttribute("user");

        if (currentUserName == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // バリデーション
        if (userName == null || userName.length() > 255) {
            request.setAttribute("message", "名前は255文字以内で入力してください");
            request.getRequestDispatcher("settings.jsp").forward(request, response);
            return;
        }

        if (email == null || email.length() > 255 ||
            !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            request.setAttribute("message", "メールアドレスの形式が正しくありません");
            request.getRequestDispatcher("settings.jsp").forward(request, response);
            return;
        }

        if (pass == null || !pass.matches("^[a-zA-Z0-9_-]{8,32}$")) {
            request.setAttribute("message", "パスワードは8〜32文字の半角英数字（_-含む）で入力してください");
            request.getRequestDispatcher("settings.jsp").forward(request, response);
            return;
        }

        // DB保存処理 (UPDATE)
        String url = "jdbc:mysql://localhost:3306/myloginapp_db"; 
        String user = "root";
        String dbPass = "koyu0104";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, dbPass)) {
                
                System.out.println("--- デバッグ開始 ---");
                System.out.println("セッションのユーザー名: [" + currentUserName + "]");

                // SQL: ここで現在の名前(currentUserName)を条件に更新
                String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE name = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, userName);
                pstmt.setString(2, email);
                pstmt.setString(3, pass);
                pstmt.setString(4, currentUserName);

                int result = pstmt.executeUpdate();

                System.out.println("実行したSQLの更新件数: " + result);
                System.out.println("--- デバッグ終了 ---");

                if (result > 0) {
                    session.setAttribute("user", userName);
                    request.setAttribute("message", "設定を保存しました");
                } else {
                    request.setAttribute("message", "DBの更新に失敗しました（一致するユーザーが見つかりません）");
                }
            } // ← ここで try-with-resources (conn) を閉じる
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "エラーが発生しました: " + e.getMessage());
        } // ← ここで catch を閉じる

        // JSPへ戻る
        request.getRequestDispatcher("settings.jsp").forward(request, response);
    } // ← ここで doPost メソッドを閉じる
} // ← ここで クラスを閉じる
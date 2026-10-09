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

        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        // セッションから現在のユーザーを特定する
        HttpSession session = request.getSession();
        // LoginServletが保存した "account" オブジェクトを取得する
        model.Account currentAccount = (model.Account) session.getAttribute("account");

        if (currentAccount == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // オブジェクトから「ID」を取得する
        int userId = currentAccount.getId();
        String errorMsg = null;

        // メールアドレスのバリデーション
        String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        if (email == null || email.trim().isEmpty()) {
            errorMsg = "メールアドレスを入力してください。";
        } else if (email.length() > 255) {
            errorMsg = "メールアドレスは255文字以内で入力してください。";
        } else if (!email.matches(emailPattern)) {
            errorMsg = "正しいメールアドレスの形式で入力してください。";
        }

        //　パスワードのバリデーション
        if (errorMsg == null) {
            String passPattern = "^[a-zA-Z0-9_-]{8,32}$";
            if (pass == null || pass.trim().isEmpty()) {
                errorMsg = "新しいパスワードを入力してください。";
            } else if (!pass.matches(passPattern)) {
                errorMsg = "パスワードは8〜32文字の半角英数字と_（アンダーバー）-（ハイフン）のみ使用可能です。";
            }
        }

        // エラーがあった場合は入力値を保持してJSPへ戻す
        if (errorMsg != null) {
            request.setAttribute("message", errorMsg);
            request.setAttribute("email", email);
            request.setAttribute("password", pass);
            request.getRequestDispatcher("settings.jsp").forward(request, response);
            return;
        }

        // DB保存処理 (UPDATE)
        String url = "jdbc:mysql://db:3306/test?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=Asia/Tokyo&allowPublicKeyRetrieval=true"; 
        String user = "root";
        String dbPass = "koyu0104";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(url, user, dbPass)) {
                
                System.out.println("--- デバッグ開始 ---");
                System.out.println("更新対象ID: [" + userId + "]");

               // emailとpasswordを更新するSQL
                String sql = "UPDATE users SET email = ?, password = ? WHERE id = ?";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, email);
                pstmt.setString(2, pass);
                pstmt.setInt(3, userId);

                int result = pstmt.executeUpdate();

                System.out.println("実行したSQLの更新件数: " + result);
                System.out.println("--- デバッグ終了 ---");

                if (result > 0) {
                    currentAccount.setEmail(email);
                    currentAccount.setPassword(pass);
                    request.setAttribute("message", "設定を保存しました");
                } else {
                    request.setAttribute("message", "DBの更新に失敗しました（一致するユーザーが見つかりません）");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "エラーが発生しました: " + e.getMessage());
        }

        request.getRequestDispatcher("settings.jsp").forward(request, response);
    }
}
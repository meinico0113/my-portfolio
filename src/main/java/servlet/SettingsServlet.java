package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/SettingsServlet")
public class SettingsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String userName = request.getParameter("userName");
        String email = request.getParameter("email");
        String pass = request.getParameter("password");

       // ---- バリデーション ----

// 名前：255文字以内
if (userName == null || userName.length() > 255) {
    request.setAttribute("message", "名前は255文字以内で入力してください");
    request.getRequestDispatcher("settings.jsp").forward(request, response);
    return;
}

// メールアドレス：形式チェック + 255文字以内
if (email == null || email.length() > 255 ||
    !email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
    request.setAttribute("message", "メールアドレスの形式が正しくありません");
    request.getRequestDispatcher("settings.jsp").forward(request, response);
    return;
}

// パスワード：8〜32文字、半角英数字と _-
if (pass == null || !pass.matches("^[a-zA-Z0-9_-]{8,32}$")) {
    request.setAttribute("message", "パスワードは8〜32文字の半角英数字（_-含む）で入力してください");
    request.getRequestDispatcher("settings.jsp").forward(request, response);
    return;
}

        // 成功したらメッセージを出して戻る
        request.setAttribute("message", "設定を保存しました");
        request.getRequestDispatcher("settings.jsp").forward(request, response);
    }
}
package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import model.Account;

@WebServlet("/SettingsServlet")
public class SettingsServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String pass = request.getParameter("password");

        // セッションからユーザー情報を取得
        HttpSession session = request.getSession();
        Account currentAccount = (Account) session.getAttribute("account");

        if (currentAccount == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
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

        // DB保存処理
        AccountDAO dao = new AccountDAO();
        
        try {
            boolean isSuccess = dao.updateEmailAndPassword(userId, email, pass);

            if (isSuccess) {
                // セッション内のオブジェクトも最新の値に更新
                currentAccount.setEmail(email);
                currentAccount.setPassword(pass);
                session.setAttribute("account", currentAccount); // セッション上書き
                
                request.setAttribute("message", "設定を保存しました");
            } else {
                request.setAttribute("message", "DBの更新に失敗しました（一致するユーザーが見つかりません）");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "エラーが発生しました: " + e.getMessage());
        }

        request.getRequestDispatcher("settings.jsp").forward(request, response);
    }
}
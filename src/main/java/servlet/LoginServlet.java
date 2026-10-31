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

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        // index.jspの入力欄から値を取り出す
        // パラメータ取得
        // 画面から送られてきた各入力内容を、文字列や数値に変換して受け取る
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        String errorMsg = null;

        // 名前のバリデーション
            if (errorMsg == null) {
                if (name == null || name.trim().isEmpty()) {
                    errorMsg = "名前を入力してください。";
                } else if (name.trim().length() > 255) {
                    errorMsg = "名前は255文字以内で入力してください。";
                }
            }

            // パスワードのバリデーション
            if (errorMsg == null) {
                String passPattern = "^[a-zA-Z0-9_-]{8,32}$";
                if (password == null || password.trim().isEmpty()) {
                    errorMsg = "新しいパスワードを入力してください。";
                } else if (!password.matches(passPattern)) {
                    errorMsg = "パスワードは8〜32文字の半角英数字と_（アンダーバー）-（ハイフン）のみ使用可能です。";
                }
            }

            // バリデーションエラーがある場合はJSPに差し戻す
        if (errorMsg != null) {
            request.setAttribute("error", errorMsg);
            request.setAttribute("name", name);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        try {
            // DAOをインスタンス化してログイン処理を実行
            AccountDAO dao = new AccountDAO();
            Account account = dao.findByStatus(name, password);

            if (account != null) {
                // ステータスチェック（0: アクセス禁止）
                if (account.getStatus() == 0) {
                    request.setAttribute("error", "このアカウントはアクセスが禁止されています。");
                    request.setAttribute("name", name);
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
                    return;
                }

                // ログイン成功：セッションに保存
                HttpSession session = request.getSession();
                session.setAttribute("account", account);

                // 権限によってリダイレクト先を分岐
                if ("admin".equals(account.getRole())) {
                    response.sendRedirect("AdminServlet");
                } else {
                    response.sendRedirect("UserServlet");
                }

            } else {
                // ログイン失敗（ユーザー名かパスワードが違う、または削除されている）
                request.setAttribute("error", "ユーザー名かパスワードが違います。");
                request.setAttribute("name", name);
                request.getRequestDispatcher("/index.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("error", "ログイン処理中にエラーが発生しました: ");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
    }
}
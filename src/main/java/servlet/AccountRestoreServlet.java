package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.AccountDAO;

@WebServlet("/admin/accountRestore")
public class AccountRestoreServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // 画面から送られてきた「id」という名前の文字列を取得し、Javaの数値（int型）に変換
            // 例：URLが「.../accountRestore?id=8」なら、数値の 8 が入る
            int id = Integer.parseInt(request.getParameter("id"));
            
            // DAOで復元を実行
            AccountDAO dao = new AccountDAO();
            dao.restore(id); // is_deletedを0に戻す

            // 削除一覧ページへ戻る
            // 復元処理が終わったら、ユーザーを削除済みアカウント一覧画面へ自動的に転送する
            // これにより、今復元した人がゴミ箱リストからパッと消え、元の通常一覧に戻ったように見せることができる
            response.sendRedirect(request.getContextPath() + "/admin/accountDeletedList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
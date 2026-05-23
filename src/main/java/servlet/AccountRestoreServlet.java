package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AccountDAO;

@WebServlet("/admin/accountRestore")
public class AccountRestoreServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            AccountDAO dao = new AccountDAO();
            dao.restore(id); // is_deletedを0に戻す

            // 削除一覧ページへ戻る
            response.sendRedirect(request.getContextPath() + "/admin/accountDeletedList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
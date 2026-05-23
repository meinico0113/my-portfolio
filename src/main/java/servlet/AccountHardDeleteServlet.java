package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AccountDAO;

@WebServlet("/admin/accountHardDelete")
public class AccountHardDeleteServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            AccountDAO dao = new AccountDAO();
            dao.hardDelete(id); // 物理削除を実行

            // 削除一覧ページへ戻る
            response.sendRedirect(request.getContextPath() + "/admin/accountDeletedList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
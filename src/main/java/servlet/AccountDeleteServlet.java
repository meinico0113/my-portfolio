package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.AccountDAO;

@WebServlet("/admin/accountDelete")
public class AccountDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            AccountDAO dao = new AccountDAO();
            dao.delete(id);

            // アカウント一覧へ
            response.sendRedirect(request.getContextPath() + "/admin/accountList");

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("error", "アカウントの削除に失敗しました。");
            request.getRequestDispatcher("/adminAccountList.jsp").forward(request, response);
        }
    }
}
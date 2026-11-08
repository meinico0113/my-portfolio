package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.AccountDAO;
import model.Account;

@WebServlet("/admin/accountStatusToggle")
public class AccountStatusToggleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            AccountDAO dao = new AccountDAO();
            Account account = dao.findById(id);

            // 今のステータスが 1（許可）なら → 0（禁止）にする、そうでなければ → 1（許可）にする
            int newStatus = (account.getStatus() == 1) ? 0 : 1;

            dao.updateStatus(id, newStatus);

            response.sendRedirect(request.getContextPath() + "/admin/accountList");

        } catch (Exception e) {
            e.printStackTrace();
        }
        request.setAttribute("error", "アカウントの切替に失敗しました。");
            request.getRequestDispatcher("/adminAccountList.jsp").forward(request, response);
    }
}

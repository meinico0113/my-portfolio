package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AccountDAO;
import model.Account;

@WebServlet("/admin/accountStatusToggle")
public class AccountStatusToggleServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));

            AccountDAO dao = new AccountDAO();

            // ① 今のデータ取得
            Account account = dao.findById(id);

            // ② ステータス反転
            int newStatus = (account.getStatus() == 1) ? 0 : 1;

            // ③ 更新
            dao.updateStatus(id, newStatus);

            // ④ 一覧へ
            response.sendRedirect(request.getContextPath() + "/admin/accountList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

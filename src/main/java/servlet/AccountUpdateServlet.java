package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AccountDAO;

@WebServlet("/admin/accountUpdate")
public class AccountUpdateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // ① パラメータ取得
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");

            // ② DAO呼び出し
            AccountDAO dao = new AccountDAO();
            dao.update(id, name, email);

            // ③ 一覧へリダイレクト
            response.sendRedirect(request.getContextPath() + "/admin/accountList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
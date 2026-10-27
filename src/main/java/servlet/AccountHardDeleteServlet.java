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

            // 削除処理が終わったら、ユーザーを削除済みアカウント一覧画面へ自動的に転送する
            response.sendRedirect(request.getContextPath() + "/admin/accountDeletedList");

        } catch (Exception e) {
            e.printStackTrace();

            // 失敗したらエラーを表示して編集画面に戻す
            request.setAttribute("error", "完全削除に失敗しました。");
            request.getRequestDispatcher("/adminAccountDeletedList.jsp").forward(request, response);
        }
    }
}
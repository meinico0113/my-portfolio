package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.AccountDAO; // 後でContactDAOに分けても良いが、一旦AccountDAOに追加する

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {

    // 画面を表示する
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("contact.jsp").forward(request, response);
    }

    // 送信ボタンが押されたとき
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String category = request.getParameter("category");
        String content = request.getParameter("content");

        try {
            // 1. DBに保存（ステータスはデフォルトの「未対応」で保存）
            AccountDAO dao = new AccountDAO();
            dao.insertContact(category, content);

            // 2. 運営メールへの送信処理
            // sendEmailToAdmin(category, content);

            // 完了画面へ（簡易的にランキングに戻す）
            response.sendRedirect("UserListServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("contact.jsp?error=1");
        }
    }
}
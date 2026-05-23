package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.ContactDAO;

@WebServlet("/admin/updateStatus")
public class AdminUpdateStatusServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // リクエストの文字コードをUTF-8に設定する
        request.setCharacterEncoding("UTF-8");

        try {

            int id = Integer.parseInt(request.getParameter("id"));
            String status = request.getParameter("status");

            ContactDAO dao = new ContactDAO();
            dao.updateStatus(id, status);

            response.sendRedirect(request.getContextPath() + "/admin/contactDetail?id=" + id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.AccountDAO;
import model.Account;

@WebServlet("/UserDetailServlet")
public class UserDetailServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("id");
        
        if (idStr != null) {
            try {
                int id = Integer.parseInt(idStr);
                AccountDAO dao = new AccountDAO();
                // あなたが書いた DAO の findById を呼び出します
                Account user = dao.findById(id);
                
                request.setAttribute("user", user);
                request.getRequestDispatcher("userDetail.jsp").forward(request, response);
                return;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("UserListServlet");
    }
}
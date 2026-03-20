package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AccountDAO;

@WebServlet("/admin/accountCreate")
public class AccountCreateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try{

            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");

            AccountDAO dao = new AccountDAO();

            dao.insert(name,email,password);

            response.sendRedirect(
                request.getContextPath()+"/admin/accountList"
            );

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AccountDAO;
import model.Account;

@WebServlet("/admin/accountList")
public class AccountListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try{

            AccountDAO dao = new AccountDAO();

            List<Account> list = dao.findAll();

            request.setAttribute("accountList", list);

            request.getRequestDispatcher("/adminAccountList.jsp")
                   .forward(request, response);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

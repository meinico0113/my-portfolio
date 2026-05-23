package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;
import model.Account;

@WebServlet("/UserListServlet")
public class UserListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        AccountDAO dao = new AccountDAO();
        
        try {
            // 1. DAOを呼び出して「いいね順」のリストを取得
            List<Account> userList = dao.findGeneralUsersOrderByLikes();
            
            // 2. 取得したリストを「userList」という名前でJSPに渡す準備
            request.setAttribute("userList", userList);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        request.getRequestDispatcher("top.jsp").forward(request, response);
    }
}
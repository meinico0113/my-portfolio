package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Account;
import dao.AccountDAO;

@WebServlet("/UserEditServlet")
public class UserEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // セッションからログイン中のユーザー情報を取得
        HttpSession session = request.getSession();
        Account loginUser = (Account) session.getAttribute("account");
        
        if (loginUser == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
        }
        
        AccountDAO dao = new AccountDAO();
        Account account = dao.findById(loginUser.getId());

        // エラーメッセージがセッションにあればリクエストスコープへ移動して削除
        String error = (String) session.getAttribute("error");
        if (error != null) {
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }

        request.setAttribute("account", account);
        request.getRequestDispatcher("/userEdit.jsp").forward(request, response);
    }
}
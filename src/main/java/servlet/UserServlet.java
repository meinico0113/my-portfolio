package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AccountDAO;
import model.Account;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        Account loginUser = (Account) session.getAttribute("account");
        if (loginUser == null) {
            loginUser = (Account) session.getAttribute("loginUser");
        }

        // 未ログインならログイン画面へ
        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        // DBから最新のユーザー情報を取得
        AccountDAO dao = new AccountDAO();
        Account freshAccount = dao.findById(loginUser.getId());

        // セッションを最新情報に更新
        session.setAttribute("account", freshAccount);
        session.setAttribute("loginUser", freshAccount);

        // user.jsp へフォワード
        request.getRequestDispatcher("/user.jsp").forward(request, response);
    }

    // POSTで送られてきたのをそのままdogetメソッドに丸投げ
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}
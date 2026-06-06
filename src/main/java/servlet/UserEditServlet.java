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
        
        // 1. セッションからログイン中のユーザー情報を取得
        HttpSession session = request.getSession();
        Account loginUser = (Account) session.getAttribute("account"); // ログイン処理時にセットした名前
        
        if (loginUser == null) {
            // 未ログインの場合はログイン画面へ戻す
            response.sendRedirect("index.jsp");
            return;
        }
        
        // 2. 既存の findById メソッドを使って最新の情報をDBから取得
        AccountDAO dao = new AccountDAO();
        Account userProfile = dao.findById(loginUser.getId());

        // 3. 取得したデータをリクエストスコープにセット
        request.setAttribute("user", userProfile);

        // 4. 編集画面へフォワード
        request.getRequestDispatcher("userProfile.jsp").forward(request, response);
    }
}
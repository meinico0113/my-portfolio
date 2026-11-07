package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;
import dao.AccountDAO;

@WebServlet("/UserDetailServlet")
public class UserDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idStr = request.getParameter("id");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                
                // DAOから指定IDのユーザー情報を取得
                AccountDAO dao = new AccountDAO();
                Account user = dao.findById(id); // ID検索メソッドを実行
                
                request.setAttribute("user", user);
            
            // 起きるエラーが分かっているためピンポイントで指定(Integer.parseInt(idStr))
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        
        // 詳細画面へフォワード
        request.getRequestDispatcher("/userDetail.jsp").forward(request, response);
    }
}
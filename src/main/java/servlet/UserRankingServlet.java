package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO; // これでAccountDAOのエラーが消えます

@WebServlet("/LikeServlet")
public class UserRankingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 文字化け防止
        request.setCharacterEncoding("UTF-8");

        String idStr = request.getParameter("targetId");
        
        if (idStr != null) {
            try {
                int targetId = Integer.parseInt(idStr);
                AccountDAO dao = new AccountDAO();
                
                // いいねを+1する
                dao.incrementLikes(targetId);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        // 更新後、一覧を表示するサーブレット（例: UserListServlet）に飛ばす
        response.sendRedirect("UserListServlet"); 
    }
}
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

@WebServlet("/UserRankingServlet")
public class UserRankingServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ランキング画面を表示する処理
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            AccountDAO dao = new AccountDAO();
            List<Account> userList = dao.findGeneralUsersOrderByLikes();

            // JSPに渡すデータをセット
            request.setAttribute("userList", userList);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // top.jspに画面遷移（フォワード）
        request.getRequestDispatcher("top.jsp").forward(request, response);
    }

    // いいねボタンが押された時の処理
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
        
        // リダイレクトは行わず、クライアントJavaScriptにレスポンスを返す
        response.setContentType("text/plain; charset=UTF-8");

        response.getWriter().write("ok");
    }
}
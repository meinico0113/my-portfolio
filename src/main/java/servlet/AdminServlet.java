package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        List<String> rankingList = new ArrayList<>();

        try (Connection conn = DBManager.getConnection()) {

            String sql =
                "SELECT title, like_count FROM posts ORDER BY like_count DESC";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String title = rs.getString("title");
                int count = rs.getInt("like_count");

                rankingList.add(title + "（" + count + "いいね）");
            }

            System.out.println("取得件数：" + rankingList.size());

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("rankingList", rankingList);

        request.getRequestDispatcher("success.jsp")
               .forward(request, response);
    }
}
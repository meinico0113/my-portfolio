package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CategoryDAO;
import model.Category;

@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
    
    // DBから「いいね」が多い順にユーザーを取得する
    List<String> rankingList = new ArrayList<>();
    
    // DB接続情報の定義（LoginServletと同じものを使用）
    String url = "jdbc:mysql://localhost:3306/myloginapp_db";
    String user = "root";
    String pass = "koyu0104";

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(url, user, pass);

        // SQL: いいね数(likes)の降順で取得
        // COALESCEを使うと、kanaがNULLならnameを表示する、という動きができる
        String sql = "SELECT COALESCE(kana, name) AS display_name, likes FROM users ORDER BY likes DESC";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            String displayName = rs.getString("display_name");
            int likes = rs.getInt("likes");
    
            // 文字列を作成してリストに追加
            String record = displayName + " いいね数：" + likes;
            rankingList.add(record);
    
            // デバッグ用：コンソールに取得できたか出力
            System.out.println("取得データ: " + record);
}
            request.setAttribute("rankingList", rankingList);
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
    }

        // 2. カテゴリ一覧取得処理
        try {
            CategoryDAO categoryDAO = new CategoryDAO();
            List<Category> categoryList = categoryDAO.findAll();
            request.setAttribute("categoryList", categoryList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 3. 最後に1回だけフォワードする
        request.getRequestDispatcher("success.jsp").forward(request, response);
        
    }
}
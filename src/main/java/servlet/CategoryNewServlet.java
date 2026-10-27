package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.CategoryDAO;

@WebServlet("/admin/categoryNew")
public class CategoryNewServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/adminCategoryNew.jsp")
               .forward(request, response);
    }

// 登録ボタンが押されたときの処理
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        // 文字化け対策
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        if (name != null) {
            name = name.trim(); // 前後の不要な空白を削除
        }

        // 未入力チェック
        if (name == null || name.isEmpty()) {
            request.setAttribute("error", "カテゴリ名を入力してください。");
            request.getRequestDispatcher("/adminCategoryNew.jsp").forward(request, response);
            return;
        }

        // 255文字以内チェック
        if (name.length() > 255) {
            request.setAttribute("error", "カテゴリ名は255文字以内で入力してください。");
            request.getRequestDispatcher("/adminCategoryNew.jsp").forward(request, response);
        return;
}

        // DB登録処理
        try {
            // DAOにカテゴリー追加メソッドを呼ぶ処理
            CategoryDAO dao = new CategoryDAO();
            dao.insertCategory(name);

            // 成功したら完了画面または一覧画面などへリダイレクト
            response.sendRedirect(request.getContextPath() + "/admin/categoryNew?success=1");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "登録処理中にエラーが発生しました。");
            request.getRequestDispatcher("/adminCategoryNew.jsp").forward(request, response);
        }
    }
}
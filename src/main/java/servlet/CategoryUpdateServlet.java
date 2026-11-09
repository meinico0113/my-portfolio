package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.CategoryDAO;
import model.Category;

@WebServlet("/admin/categoryUpdate")
public class CategoryUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

    try {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        if (name != null) {
            name = name.trim(); // 前後の空白を削除
        }

        String errorMsg = null;

        // カテゴリ名のバリデーション
        if (errorMsg == null) {
            if (name == null || name.isEmpty()) {
                errorMsg = "カテゴリ名を入力してください。";
            } else if (name.length() > 255) {
                errorMsg = "カテゴリ名は255文字以内で入力してください。";
            }
        }

        if (errorMsg != null) {
            Category category = new Category();
            category.setId(id);
            // 255文字超えの時は入力内容をクリアにしておくことでコード流出を防ぐ
            category.setName(name.length() > 255 ? "" : name);

            request.setAttribute("error", errorMsg);
            request.setAttribute("category", category);

            request.getRequestDispatcher("/adminCategoryEdit.jsp")
                    .forward(request, response);
            return;
        }

        // DB更新処理
        CategoryDAO dao = new CategoryDAO();
        dao.update(id, name);

        // 一覧へリダイレクト
        response.sendRedirect(request.getContextPath() + "/admin/categoryList");

    } catch (Exception e) {
        e.printStackTrace();
    }
}
}
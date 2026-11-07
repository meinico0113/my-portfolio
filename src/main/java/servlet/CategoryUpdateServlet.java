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
            // パラメーターの取得
            // 画面の <input type="hidden" name="id"> から「誰（どのカテゴリ）を更新するか」のIDを取得
            int id = Integer.parseInt(request.getParameter("id"));
            // <input type="text" name="name"> から、新しく書き換えられたカテゴリ名を取得
            String name = request.getParameter("name");

            if (name != null) {
                name = name.trim(); // 前後の空白を削除
            }

            // バリデーション
            String errorMsg = null;

            if (name == null || name.isEmpty()) {
                errorMsg = "カテゴリ名を入力してください。";
            } else if (name.length() > 255) {
                errorMsg = "カテゴリ名は255文字以内で入力してください。";
            }

            // エラーがある場合はJSPに戻す
            if (errorMsg != null) {
                // 入力内容とIDを保持したCategory オブジェクトを作成してセット
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
package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.CategoryDAO;

@WebServlet("/admin/categoryCreate")
public class CategoryCreateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                request.setCharacterEncoding("UTF-8");

        try {

            String name = request.getParameter("name");

            // バリデーション
            if (name == null || name.trim().length() == 0 || name.length() > 255) {

                request.setAttribute("error", "カテゴリ名は255文字以内で入力してください");

                request.getRequestDispatcher("/adminCategoryNew.jsp")
                        .forward(request, response);

                return;
            }

            CategoryDAO dao = new CategoryDAO();
            dao.insert(name);

            response.sendRedirect(request.getContextPath() + "/admin/categoryList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

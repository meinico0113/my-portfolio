package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.CategoryDAO;
import model.Category;

@WebServlet("/admin/categoryList")
public class CategoryListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException, IOException {

        try {
            CategoryDAO dao = new CategoryDAO();

            List<Category> list = dao.findAll();

            request.setAttribute("categoryList", list);
            request.getRequestDispatcher("/adminCategoryList.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            request.setAttribute("error", "カテゴリ情報の取得に失敗しました。");
            request.getRequestDispatcher("/adminCategoryList.jsp").forward(request, response);
        }
    }
}
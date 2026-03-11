package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.CategoryDAO;
import model.Category;

@WebServlet("/admin/categoryEdit")
public class CategoryEditServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                request.setCharacterEncoding("UTF-8");

        try {

            int id = Integer.parseInt(request.getParameter("id"));

            CategoryDAO dao = new CategoryDAO();
            Category category = dao.findById(id);

            request.setAttribute("category", category);

            request.getRequestDispatcher("/adminCategoryEdit.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

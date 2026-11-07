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

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // カテゴリデータを専門に扱う部品（CategoryDAO）をインスタンス化
            CategoryDAO dao = new CategoryDAO();

            // データベースから全件取得
            // DAOにお願いして、DBにあるすべてのカテゴリを全件取ってきてもらい、Categoryオブジェクトが詰まった「List」として一括で受け取る
            List<Category> list = dao.findAll();
            // JSPへ引き渡す準備
            // 取得したカテゴリリスト（list）を、次のJSP画面に引き渡すために「categoryList」という名前のポケットに詰め込む
            request.setAttribute("categoryList", list);
            // 一覧画面のJSPへフォワード
            // 荷物（リスト）を持ったまま、カテゴリ一覧表示専用のJSP画面へ処理をバトンタッチする
            // これにより、JSP画面側でループ処理（c:forEachなど）を使って、カテゴリを上から順に並べて表示できる
            request.getRequestDispatcher("/adminCategoryList.jsp")
                   .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
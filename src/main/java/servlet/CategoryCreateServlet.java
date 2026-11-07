package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.CategoryDAO;

@WebServlet("/admin/categoryCreate")
public class CategoryCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

                request.setCharacterEncoding("UTF-8");

        try {
            // パラメーターの取得
            // 入力フォームの <input name="name"> から、ユーザーが入力したカテゴリ名を取得
            String name = request.getParameter("name");

            // バリデーション
            if (name == null || name.trim().length() == 0 || name.length() > 255) {
                // チェックに引っかかった場合は、エラーメッセージをポケット（request）に詰める
                request.setAttribute("error", "カテゴリ名は255文字以内で入力してください");
                // 登録画面のJSPへフォワードで戻し、エラーを表示させる
                request.getRequestDispatcher("/adminCategoryNew.jsp")
                        .forward(request, response);
                // 画面を戻した後は、これ以降のDB登録に進まないように処理をここで強制終了する
                return;
            }

            // DBへの登録（すべてのチェックを通過した場合のみ到達）
            // カテゴリ用DAOをインスタンス化し、insertメソッドに入力された名前を渡してDBに保存する
            CategoryDAO dao = new CategoryDAO();
            dao.insert(name);
            // 成功時は一覧画面へリダイレクト
            // 無事に登録が完了したので、ブラウザに対してカテゴリ一覧画面を新しく開き直してと指示する
            response.sendRedirect(request.getContextPath() + "/admin/categoryList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

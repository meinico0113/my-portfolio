package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.ContactDAO;

@WebServlet("/admin/updateStatus")
public class AdminUpdateStatusServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // リクエストの文字コードをUTF-8に設定する
        request.setCharacterEncoding("UTF-8");

        try {
            // パラメータ取得
            // 「どのお問い合わせ」の「どのステータス」にするか、データを受け取る
            int id = Integer.parseInt(request.getParameter("id")); // お問い合わせID（数値に変換）
            String status = request.getParameter("status"); // 新しいステータス名

            // DB更新
            // お問い合わせ専用のDAOをインスタンス化し、updateStatusメソッドを呼び出してDBの値を書き換える
            ContactDAO dao = new ContactDAO();
            dao.updateStatus(id, status);

            // 更新完了後のリダイレクト
            // URLの末尾に「?id=番号」をくっつけることで、更新した直後の最新の状態をそのまま詳細画面で見せることができる
            response.sendRedirect(request.getContextPath() + "/admin/contactDetail?id=" + id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
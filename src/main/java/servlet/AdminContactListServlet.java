package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.ContactDAO;
import model.Contact;

@WebServlet("/admin/contactList")
public class AdminContactListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            ContactDAO dao = new ContactDAO();
            List<Contact> list = dao.findAll();
            // 【JSPへ引き渡す準備】
            // 取得したお問い合わせリスト（list）を、次のJSP画面に引き渡すために「contactList」という名前のポケットに詰め込む
            request.setAttribute("contactList", list);
            request.getRequestDispatcher("/adminContactList.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            // 失敗したらエラーを表示して問い合わせ一覧画面に戻す
            request.setAttribute("error", "問い合わせ情報の取得に失敗しました。");
            request.getRequestDispatcher("/adminContactList.jsp").forward(request, response);
        }
    }
}

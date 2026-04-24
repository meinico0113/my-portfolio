package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AccountDAO;

@WebServlet("/admin/accountUpdate")
@MultipartConfig
public class AccountUpdateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

            request.setCharacterEncoding("UTF-8");
            
        try {
            // ① パラメータ取得
            int id = Integer.parseInt(request.getParameter("id"));
String role = request.getParameter("role");
String name = request.getParameter("name");
String email = request.getParameter("email");
int status = Integer.parseInt(request.getParameter("status"));

AccountDAO dao = new AccountDAO();

if ("admin".equals(role)) {

    // 管理者更新
    dao.updateAdmin(id, name, email, status);

} else {

    // 一般ユーザー
    String kana = request.getParameter("kana");
    String gender = request.getParameter("gender");

    String ageStr = request.getParameter("age");
    int age = 0;
    if (ageStr != null && ageStr.matches("^[0-9]{1,3}$")) {
        age = Integer.parseInt(ageStr);
    }

    String profile = request.getParameter("profile");

    Part image = request.getPart("image"); // 画像

    dao.updateUser(id, name, email, status, kana, gender, age, profile, image);
}

            // ③ 一覧へリダイレクト
            response.sendRedirect(request.getContextPath() + "/admin/accountList");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
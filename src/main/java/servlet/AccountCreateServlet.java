package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AccountDAO;

@WebServlet("/admin/accountCreate")
@MultipartConfig // 画像アップロード用
public class AccountCreateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String role = request.getParameter("role");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int status = Integer.parseInt(request.getParameter("status"));

        if (name == null || name.length() == 0 || name.length() > 255) {
        request.setAttribute("error", "名前は1〜255文字で入力してください");

        // 入力値保持
        request.setAttribute("name", name);
        request.setAttribute("email", email);

        request.getRequestDispatcher("/WEB-INF/jsp/adminAccountNew.jsp")
               .forward(request, response);
        return;
    }

        if (email == null || email.length() == 0 || email.length() > 255 ||
        !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

        request.setAttribute("error", "正しいメールアドレスを255文字以内で入力してください");

        request.setAttribute("name", name);
        request.setAttribute("email", email);

        request.getRequestDispatcher("/WEB-INF/jsp/adminAccountNew.jsp")
           .forward(request, response);
        return;
    }

        AccountDAO dao = new AccountDAO();

        try{

            if("admin".equals(role)){
            dao.insertAdmin(name,email,status); // 管理者

        }else{
            // 一般ユーザー
            String kana = request.getParameter("kana");
            String gender = request.getParameter("gender");
            int age = Integer.parseInt(request.getParameter("age"));
            String profile = request.getParameter("profile");

            Part image = request.getPart("image"); // 画像取得

            dao.insertUser(name, email, status, kana, gender, age, profile, image);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/admin/accountList");
    }
}

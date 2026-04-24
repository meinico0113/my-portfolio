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

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String role = request.getParameter("role");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String kana = request.getParameter("kana");
        String ageStr = request.getParameter("age");
        String profile = request.getParameter("profile");
        String statusStr = request.getParameter("status"); // 数値変換前に文字列として取得
        
        // statusのnullチェックを追加
        if (statusStr == null || !statusStr.matches("^[0-9]+$")) {
            request.setAttribute("error", "ステータスを入力してください");
            request.getRequestDispatcher("/adminAccountNew.jsp").forward(request, response);
            return;
        }
        int status = Integer.parseInt(statusStr);

        // 名前のバリデーション
        if (name == null || name.length() == 0 || name.length() > 255) {
            request.setAttribute("error", "名前は1〜255文字で入力してください");
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/adminAccountNew.jsp").forward(request, response);
            return;
        }

        // メアドのバリデーション
        if (email == null || email.length() == 0 || email.length() > 255 || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            request.setAttribute("error", "正しいメールアドレスを255文字以内で入力してください");
            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/adminAccountNew.jsp").forward(request, response);
            return;
        }

        AccountDAO dao = new AccountDAO();

        try {
            if ("admin".equals(role)) {
                dao.insertAdmin(name, email, "1111", status);
            } else {
                // 一般ユーザーのバリデーション
                if (kana == null || kana.length() == 0 || kana.length() > 255 || !kana.matches("^[ぁ-んー]+$")) {
                    request.setAttribute("error", "ふりがなは255文字以内のひらがなで入力してください");
                    request.getRequestDispatcher("/adminAccountNew.jsp").forward(request, response);
                    return;
                }

                String gender = request.getParameter("gender");
                if (gender == null || !(gender.equals("male") || gender.equals("female"))) {
                    request.setAttribute("error", "性別の選択が不正です");
                    request.getRequestDispatcher("/adminAccountNew.jsp").forward(request, response);
                    return;
                }

                if (ageStr == null || !ageStr.matches("^[0-9]{1,3}$")) {
                    request.setAttribute("error", "年齢は3桁以内の数字で入力してください");
                    request.getRequestDispatcher("/jsp/adminAccountNew.jsp").forward(request, response);
                    return;
                }

                if (profile != null && profile.length() > 1500) {
                    request.setAttribute("error", "自己紹介は1500文字以内で入力してください");
                    request.getRequestDispatcher("/adminAccountNew.jsp").forward(request, response);
                    return;
                }

                Part image = request.getPart("image");
                if (image != null && image.getSize() > 2 * 1024 * 1024) {
                    request.setAttribute("error", "画像は2MB以内にしてください");
                    request.getRequestDispatcher("/adminAccountNew.jsp").forward(request, response);
                    return;
                }

                dao.insertUser(name, email, "1111", status, name, kana, gender, Integer.parseInt(ageStr), profile, image);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/admin/accountList");
    }
}
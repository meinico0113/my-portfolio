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

    // protected~IOExceotionは決まってる形
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String role = request.getParameter("role");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        int status = Integer.parseInt(request.getParameter("status"));

        // 名前のバリデーション
        if (name == null || name.length() == 0 || name.length() > 255) {
        request.setAttribute("error", "名前は1〜255文字で入力してください");

        // 入力値保持（これがないとまた最初からかよ！となる(^-^)）
        request.setAttribute("name", name);
        request.setAttribute("email", email);

        request.getRequestDispatcher("/WEB-INF/jsp/adminAccountNew.jsp")
               .forward(request, response);
        return;
    }

        // メアドのバリデーション
        // ^はここから始まり、$はここで終わりという意味
        if (email == null || email.length() == 0 || email.length() > 255 || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

        request.setAttribute("error", "正しいメールアドレスを255文字以内で入力してください");

        request.setAttribute("name", name);
        request.setAttribute("email", email);

        request.getRequestDispatcher("/WEB-INF/jsp/adminAccountNew.jsp")
           .forward(request, response);
        return;
    }

        AccountDAO dao = new AccountDAO();

        // try-catchがないとDB接続に失敗した瞬間に強制終了してしまう
        try{

            if("admin".equals(role)){
            dao.insertAdmin(name,email,status); // 管理者

        }else{
            // 一般ユーザー
            String kana = request.getParameter("kana");

            // ふりがなのバリデーション
            if (kana == null || kana.length() == 0 || kana.length() > 255 || !kana.matches("^[ぁ-んー]+$")) {

            request.setAttribute("error", "ふりがなは255文字以内のひらがなで入力してください");

            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("kana", kana);

            request.getRequestDispatcher("/WEB-INF/jsp/adminAccountNew.jsp")
                .forward(request, response);
            return;
             }
            
            String gender = request.getParameter("gender");

            // 性別のバリデーション
            // 男性 or 女性のみの選択肢しかないため必要ないのでは？
            // →HTMLのみだと簡単に書き換えられてしまう
            if (gender == null || !(gender.equals("male") || gender.equals("female"))) {

            request.setAttribute("error", "性別の選択が不正です");

            request.setAttribute("name", name);
            request.setAttribute("email", email);
            request.setAttribute("kana", request.getParameter("kana"));

            request.getRequestDispatcher("/WEB-INF/jsp/adminAccountNew.jsp")
                .forward(request, response);
            return;
            }

            int age = Integer.parseInt(request.getParameter("age"));
            String profile = request.getParameter("profile");

            Part image = request.getPart("image"); // 画像取得

            // 画像のバリデーション
            // 2MB = 2 * 1024 * 1024 = 2097152バイト
            // 豆知識💡 1024B = 1KB  1024KB = 1MB なので上記の式になる
            if (image != null && image.getSize() > 2 * 1024 * 1024) {

            request.setAttribute("error", "画像は2MB以内にしてください");

            request.setAttribute("name", name);
            request.setAttribute("email", email);

            request.getRequestDispatcher("/WEB-INF/jsp/adminAccountNew.jsp")
                    .forward(request, response);
            return;
            }

            dao.insertUser(name, email, status, kana, gender, age, profile, image);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/admin/accountList");
    }
}

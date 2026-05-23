package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import model.Account;
import dao.AccountDAO;

@WebServlet("/ProfileUpdateServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2, // 2MB
    maxFileSize = 1024 * 1024 * 10,      // 10MB
    maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class UserUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");

        // 1. セッションからユーザー情報を取得
        HttpSession session = request.getSession();
        Account loginUser = (Account) session.getAttribute("loginUser");
        if (loginUser == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // 2. フォームからの入力値を取得
        String name = request.getParameter("name");
        String kana = request.getParameter("furigana"); // JSPのname属性「furigana」に合わせる
        String gender = request.getParameter("gender");
        int age = Integer.parseInt(request.getParameter("age"));
        String profileText = request.getParameter("introduction"); // JSPのname属性「introduction」に合わせる
        Part imagePart = request.getPart("profileImage");

        // 3. 画像ファイルが選択されている場合はサーバーに物理保存する
        if (imagePart != null && imagePart.getSize() > 0) {
            String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            // ファイル名を取得して保存
            String fileName = Paths.get(imagePart.getSubmittedFileName()).getFileName().toString();
            imagePart.write(uploadPath + File.separator + fileName);
        }

        // 4. 既存の updateUser メソッドを実行
        AccountDAO dao = new AccountDAO();
        try {
            // id, name, email, status, kana, gender, age, profile, image
            // ※emailとstatus、現在の画像は変更しないため、既存の loginUser から値を引き継ぎます
            dao.updateUser(
                loginUser.getId(),
                name,
                loginUser.getEmail(),
                loginUser.getStatus(),
                kana,
                gender,
                age,
                profileText,
                imagePart
            );

            // 5. 成功したら一般画面へリダイレクト
            response.sendRedirect("user.jsp");

        } catch (Exception e) {
            e.printStackTrace();
            // 失敗したらエラーを表示して編集画面に戻す
            request.setAttribute("error", "プロフィールの更新に失敗しました。");
            request.getRequestDispatcher("profileEdit.jsp").forward(request, response);
        }
    }
}
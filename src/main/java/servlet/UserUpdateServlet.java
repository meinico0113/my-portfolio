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

@WebServlet("/UserUpdateServlet")
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

        // セッションからユーザー情報を取得
        HttpSession session = request.getSession();
        Account loginUser = (Account) session.getAttribute("loginUser");

        if (loginUser == null) {
            loginUser = (Account) session.getAttribute("account");
        }

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        AccountDAO dao = new AccountDAO();

        try {
            // パラメータ取得
            String name = request.getParameter("name");
            String kana = request.getParameter("kana");
            String gender = request.getParameter("gender");
            String ageStr = request.getParameter("age");
            String profile = request.getParameter("introduction");

            // エラーメッセージ用変数
            String errorMsg = null;

            // 名前のバリデーション
            if (errorMsg == null) {
                if (name == null || name.trim().isEmpty()) {
                    errorMsg = "名前を入力してください。";
                } else if (name.trim().length() > 255) {
                    errorMsg = "名前は255文字以内で入力してください。";
                }
            }

            // ふりがなのバリデーション
            if (errorMsg == null) {
                String kanaPattern = "^[\\u3041-\\u3096ー]*$";
                if (kana == null || kana.trim().isEmpty()) {
                    errorMsg = "ふりがなを入力してください。";
                } else if (kana.length() > 255) {
                    errorMsg = "ふりがなは255文字以内で入力してください。";
                } else if (!kana.matches(kanaPattern)) {
                    errorMsg = "ふりがなは「ひらがな」で入力してください。";
                }
            }

            // 性別のバリデーション
            if (errorMsg == null) {
                if (gender == null || gender.trim().isEmpty()) {
                    errorMsg = "性別を選択してください。";
                } else if (!"male".equals(gender) && !"female".equals(gender)) {
                    errorMsg = "性別を正しく選択してください。";
                }
            }

            // 年齢のバリデーション
            if (errorMsg == null) {
                if (ageStr == null || ageStr.trim().isEmpty()) {
                    errorMsg = "年齢を入力してください。";
                } else if (!ageStr.matches("^[0-9]{1,3}$")) {
                    errorMsg = "年齢は3桁以内の数字で入力してください。";
                }
            }

            // 自己紹介のバリデーション
            if (errorMsg == null) {
                if (profile != null && profile.length() > 1500) {
                    errorMsg = "自己紹介は1500文字以内で入力してください。";
                }
            }

            // 画像のバリデーション（2MB制限）
            Part image = null;
            if (errorMsg == null) {
                try {
                    image = request.getPart("profileImage");
                    if (image != null && image.getSize() > 0) {
                        long maxSize = 2 * 1024 * 1024; // 2MB
                        if (image.getSize() > maxSize) {
                            errorMsg = "プロフィール画像は2MB以内のファイルを選択してください。";
                        }
                    }
                } catch (Exception e) {
                    errorMsg = "画像の読み込み中にエラーが発生しました。";
                }
            }

            // --- エラーがあった場合の処理（入力内容を保持して画面に戻す） ---
            if (errorMsg != null) {
                Account userProfile = new Account();
                userProfile.setId(loginUser.getId());
                userProfile.setName(name);
                userProfile.setKana(kana);
                userProfile.setGender(gender);
                
                if (ageStr != null && ageStr.matches("^[0-9]{1,3}$")) {
                    userProfile.setAge(Integer.parseInt(ageStr));
                } else {
                    userProfile.setAge(0);
                }
                userProfile.setProfile(profile);
                userProfile.setImagePath(loginUser.getImagePath()); // 既存の画像を維持

                request.setAttribute("userProfile", userProfile);
                request.setAttribute("error", errorMsg);
                request.getRequestDispatcher("/userEdit.jsp").forward(request, response);
                return; 
            }

        // 画像ファイルが選択されている場合はサーバーに保存
            if (image != null && image.getSize() > 0) {
                String uploadPath = getServletContext().getRealPath("") + File.separator + "uploads";
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String fileName = Paths.get(image.getSubmittedFileName()).getFileName().toString();
                image.write(uploadPath + File.separator + fileName);
            }

            int age = Integer.parseInt(ageStr);

            // DBのプロフィール情報を更新
            dao.updateUser(
                loginUser.getId(),
                name,
                loginUser.getEmail(),
                loginUser.getStatus(),
                loginUser.getRole(),
                kana,
                gender,
                age,
                profile,
                image
            );

            // DBから最新のデータを取得してセッションを更新
            Account dbAccount = dao.findById(loginUser.getId());
            session.setAttribute("account", dbAccount);
            session.setAttribute("loginUser", dbAccount);

            // 更新成功時は一般画面へリダイレクト
            response.sendRedirect(request.getContextPath() + "/UserServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "プロフィールの更新に失敗しました。");
            request.setAttribute("userProfile", loginUser);
            request.getRequestDispatcher("/userEdit.jsp").forward(request, response);
        }
    }
}
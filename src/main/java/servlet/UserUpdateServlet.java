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
        Account loginUser = (Account) session.getAttribute("account");

        if (loginUser == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        AccountDAO dao = new AccountDAO();

        try {
            // パラメータ取得
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String kana = request.getParameter("kana");
            String gender = request.getParameter("gender");
            String ageStr = request.getParameter("age");
            String profile = request.getParameter("profile");

            // エラーメッセージ用変数
            String errorMsg = null;

            // 名前のバリデーション
            if (errorMsg == null) {
                if (name == null || name.trim().isEmpty()) {
                    errorMsg = "名前を入力してください。";
            } else if (name.trim().length() > 255) {
                    errorMsg = "名前は255文字以内で入力してください。";
            } else if (dao.checknameDuplicate(name.trim(), id)) {
                    errorMsg = "この名前はすでに使用されています。";
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
                    image = request.getPart("image");
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

            // エラーがあった場合の入力保持処理
            if (errorMsg != null) {
                Account account = new Account();
                account.setId(id);
                account.setName(name);
                account.setKana(kana);
                account.setGender(gender);
                
                if (ageStr != null && ageStr.matches("^[0-9]{1,3}$")) {
                    account.setAge(Integer.parseInt(ageStr));
                } else {
                    account.setAge(0);
                }

                account.setProfile(profile);
                
                // 元の画像パスを維持
                if (loginUser != null) {
                    account.setImagePath(loginUser.getImagePath());
                }

                request.setAttribute("account", account);
                request.setAttribute("error", errorMsg);
                request.getRequestDispatcher("/userEdit.jsp").forward(request, response);
                return;
            }

            // 画像ファイルが選択されている場合の保存処理
            String imagePath = null;
            if (image != null && image.getSize() > 0) {
                // 保存先フォルダのパスを特定
                String uploadPath = getServletContext().getRealPath("/uploads");
                File uploadDir = new File(uploadPath);
                // なければ作成
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                // ファイル名を取り出す
                String fileName = Paths.get(image.getSubmittedFileName()).getFileName().toString();
                
                // 実際にファイルをサーバーに書き込む
                image.write(uploadPath + File.separator + fileName);
                
                // DBに保存するパス
                imagePath = "uploads/" + fileName;
            }

            int age = (ageStr != null && ageStr.matches("^[0-9]{1,3}$")) ? Integer.parseInt(ageStr) : 0;

            // DAOに渡すためのAccountオブジェクトを作成
            Account updateAccount = new Account();
            updateAccount.setId(loginUser.getId());
            updateAccount.setName(name);
            updateAccount.setKana(kana);
            updateAccount.setGender(gender);
            updateAccount.setAge(age);
            updateAccount.setProfile(profile);
            
            // 新しい画像がアップロードされた場合のみパスを設定
            if (imagePath != null) {
                updateAccount.setImagePath(imagePath);
            } else {
                updateAccount.setImagePath(loginUser.getImagePath());
            }

            // DBのプロフィール情報を更新
            boolean isSuccess = dao.updateProfile(updateAccount, image);

            if (!isSuccess) {
                // 更新失敗時の処理
                request.setAttribute("error", "プロフィールの更新に失敗しました。");
                request.setAttribute("account", updateAccount);
                request.getRequestDispatcher("/userEdit.jsp").forward(request, response);
                return;
            }

            // DBから最新のデータを取得してセッションを更新
            Account dbAccount = dao.findById(loginUser.getId());
            session.setAttribute("account", dbAccount);

            // 更新成功時は一般画面へリダイレクト
            response.sendRedirect(request.getContextPath() + "/UserServlet");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "プロフィールの更新に失敗しました。");
            request.setAttribute("account", loginUser);
            request.getRequestDispatcher("/userEdit.jsp").forward(request, response);
        }
    }
}
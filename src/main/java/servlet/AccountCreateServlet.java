package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.AccountDAO;
import model.Account;

@WebServlet("/admin/accountCreate")
@MultipartConfig // 画像アップロード用
public class AccountCreateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        AccountDAO dao = new AccountDAO();

        try {
            // パラメータ取得
            String role = request.getParameter("role");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String statusStr = request.getParameter("status");

            // エラーメッセージを最初はnullで用意
            String errorMsg = null;

            // ステータスのチェック
            if (statusStr == null || !statusStr.matches("^[0-9]+$")) {
                errorMsg = "ステータスを入力してください。";
            }
            int status = (statusStr != null && statusStr.matches("^[0-9]+$")) ? Integer.parseInt(statusStr) : 0;

            // 名前のバリデーション
            if (errorMsg == null) {
                if (name == null || name.trim().isEmpty()) {
                    errorMsg = "名前を入力してください。";
                } else if (name.length() > 255) {
                    errorMsg = "名前は255文字以内で入力してください。";
                }
            }

            // メールのバリデーション
            if (errorMsg == null) {
                String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
                if (email == null || email.trim().isEmpty()) {
                    errorMsg = "メールアドレスを入力してください。";
                } else if (email.length() > 255) {
                    errorMsg = "メールアドレスは255文字以内で入力してください。";
                } else if (!email.matches(emailPattern)) {
                     errorMsg = "正しいメールアドレスの形式で入力してください。";
                }
            }

            // --- ここから一般ユーザーのみのチェック ---
            String kana = request.getParameter("kana");
            String gender = request.getParameter("gender");
            String ageStr = request.getParameter("age");
            String profile = request.getParameter("profile");

            if (!"admin".equals(role)) {
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

                // 画像のバリデーション
                if (errorMsg == null) {
                    try {
                        Part image = request.getPart("image");
                        if (image != null && image.getSize() > 0) {
                            long maxSize = 2 * 1024 * 1024; 
                            if (image.getSize() > maxSize) {
                                errorMsg = "プロフィール画像は2MB以内のファイルを選択してください。";
                            }
                        }
                    } catch (Exception e) {
                        errorMsg = "画像の読み込み中にエラーが発生しました。";
                    }
                }
            }

            // エラーがあった場合の処理
            if (errorMsg != null) {
                // 今回入力された値を「仮のaccountオブジェクト」に詰めてJSPへ返す
                Account account = new Account();
                account.setRole(role);
                account.setName(name);
                account.setEmail(email);
                account.setStatus(status);
                request.setAttribute("kana", kana);
                request.setAttribute("gender", gender);
                request.setAttribute("age", ageStr);
                request.setAttribute("profile", profile);
                request.setAttribute("account", account);
                request.setAttribute("error", errorMsg);
                request.getRequestDispatcher("/adminAccountNew.jsp").forward(request, response);
                return; 
            }

 // バリデーション全通過：DB登録処理
if ("admin".equals(role)) {
    dao.insertAdmin(name, email, password, status);
} else {
    int age = (ageStr != null && ageStr.matches("^[0-9]{1,3}$")) ? Integer.parseInt(ageStr) : 0;
    
    // 保存するファイル名を保持する変数
    String profileImage = null;

    // 画像の処理
    try {
        Part image = request.getPart("image");
        if (image != null && image.getSize() > 0 && image.getSubmittedFileName() != null && !image.getSubmittedFileName().isEmpty()) {
            
            String uploadPath = getServletContext().getRealPath("/uploads");
            java.io.File uploadDir = new java.io.File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }
            
            // ファイル名の取得と保存
            String fileName = java.nio.file.Paths.get(image.getSubmittedFileName()).getFileName().toString();
            // 重複防止用タイムスタンプ付与（推奨）
            profileImage = System.currentTimeMillis() + "_" + fileName;
            image.write(uploadPath + java.io.File.separator + profileImage);
        }
    } catch (Exception e) {
        // 画像がない、または保存失敗時のログ出力（登録処理自体は続行）
        e.printStackTrace();
    }

    dao.insertUser(name, email, password, status, kana, gender, age, profile, profileImage);
}

// 成功時は一覧へリダイレクト
response.sendRedirect(request.getContextPath() + "/admin/accountList");

} catch (Exception e) {
    e.printStackTrace();
    response.sendRedirect(request.getContextPath() + "/admin/accountList");
}
}
}
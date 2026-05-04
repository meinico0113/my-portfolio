package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import dao.AccountDAO;
import model.Account;

@WebServlet("/admin/accountUpdate")
@MultipartConfig
public class AccountUpdateServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        AccountDAO dao = new AccountDAO();

        try {
            // ① パラメータ取得
            int id = Integer.parseInt(request.getParameter("id"));
            String role = request.getParameter("role");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            int status = Integer.parseInt(request.getParameter("status"));

            // バリデーションチェック
            String errorMsg = null;

            // 名前のバリデージョン
            if (name == null || name.trim().isEmpty()) {
                errorMsg = "名前を入力してください。";
            } else if (name.length() > 255) {
                errorMsg = "名前は255文字以内で入力してください。";
            }

            // メールのバリデーション
            if(errorMsg == null){
                String emailPattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

                if (email == null || email.trim().isEmpty()) {
                    errorMsg = "メールアドレスを入力してください。";
                } else if (email.length() > 255) {
                    errorMsg = "メールアドレスは255文字以内で入力してください。";
                } else if (!email.matches(emailPattern)) {
                     errorMsg = "正しいメールアドレスの形式で入力してください。";
                }
            }

            // ふりがなのバリデーション
            if (errorMsg == null && !"admin".equals(role)) {
                String kana = request.getParameter("kana");
                // ひらがな（\u3041-\u3096）と、長音（ー）のみを許可する正規表現
                String kanaPattern = "^[\\u3041-\\u3096ー]*$";

                if (kana != null && !kana.isEmpty()) { // 空文字は許容する場合
                    if (kana.length() > 255) {
                        errorMsg = "ふりがなは255文字以内で入力してください。";
                    } else if (!kana.matches(kanaPattern)) {
                        errorMsg = "ふりがなは「ひらがな」で入力してください。";
                    }
                }
            }

            // 性別のバリデーション
            if (errorMsg == null && !"admin".equals(role)) {
                String gender = request.getParameter("gender");
    
                // 許可する値のリスト（JSPのvalue属性と一致させる）
                if (gender != null && !gender.isEmpty()) {
                    if (!"男性".equals(gender) && !"女性".equals(gender) && !"その他".equals(gender)) {
                        errorMsg = "性別を正しく選択してください。";
                    }
                }
            }

            // 画像のバリデーション
            if (errorMsg == null && !"admin".equals(role)) {
                try {
                    Part image = request.getPart("image");
                    if (image != null && image.getSize() > 0) {
                        // 2MB = 2 * 1024 * 1024 bytes
                        long maxSize = 2 * 1024 * 1024; 
                        if (image.getSize() > maxSize) {
                            errorMsg = "プロフィール画像は2MB以内のファイルを選択してください。";
                        }
                    }
                } catch (Exception e) {
                    // getPart() でエラーが出た場合など
                    errorMsg = "画像の読み込み中にエラーが発生しました。";
                }
            }

            if (errorMsg != null) {
                // エラーがある場合は編集画面のJSPへ戻す
                Account account = dao.findById(id); // 最新の情報をDBから再取得
                // もし「入力中の名前」を保持したい場合は、ここでaccountにセットし直す
                // account.setName(name); 

                request.setAttribute("account", account);
                request.setAttribute("error", errorMsg);
                request.getRequestDispatcher("/adminAccountEdit.jsp").forward(request, response);
                return; // 処理を終了
            }
            // ----------------------------

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
                Part image = request.getPart("image");

                dao.updateUser(id, name, email, status, kana, gender, age, profile, image);
            }

            // ③ 成功時は一覧へリダイレクト
            response.sendRedirect(request.getContextPath() + "/admin/accountList");

        } catch (Exception e) {
            e.printStackTrace();
            // エラー時も一覧に戻すか、エラーページへ
            response.sendRedirect(request.getContextPath() + "/admin/accountList");
        }
    }
}
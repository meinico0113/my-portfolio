package servlet;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AccountDAO;
import model.Category;

@WebServlet("/ContactServlet")
public class ContactServlet extends HttpServlet {

    // 画面を表示する
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            // DBからカテゴリー一覧を取得
            AccountDAO dao = new AccountDAO();
            List<Category> categoryList = dao.findAllCategories();

            // リクエストスコープにセット
            request.setAttribute("categoryList", categoryList);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        request.getRequestDispatcher("contact.jsp").forward(request, response);
    }

    // 送信ボタンが押されたとき
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String category = request.getParameter("category");
        String content = request.getParameter("content");

        try {
            // DBに保存
            AccountDAO dao = new AccountDAO();
            dao.insertContact(category, content);

            // 運営メールへの送信処理
            sendEmailToAdmin(category, content);

            response.sendRedirect("UserListServlet");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("contact.jsp?error=1");
        }
    }

    // 問い合わせ通知メール送信メソッド
    private void sendEmailToAdmin(String category, String content) {
        // 送信先・送信元の設定
        final String toEmail = "k.kawara@oplan.co.jp";
        final String fromEmail = "k.kawara@oplan.co.jp"; 
        // 本番のアプリ開発では環境変数（.envファイル等）や設定ファイルから読み込ませるのが○
        final String password = "Koyu0104";

        // SMTPサーバーの設定 (Outlook / Microsoft 365用)
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.office365.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // 認証情報の作成
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            // メールの内容を作成
            Message message = new MimeMessage(session);
            //差出人
            message.setFrom(new InternetAddress(fromEmail, "お問い合わせシステム", "UTF-8"));
            // 宛先
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            // 件名
            message.setSubject("【通知】新しいお問い合わせが届きました");
            // 本文
            StringBuilder sb = new StringBuilder();
            sb.append("管理者様\n\n");
            sb.append("Webサイトから新しくお問い合わせが届きました。\n\n");
            sb.append("--------------------------------------------------\n");
            sb.append("【カテゴリ】: ").append(category).append("\n");
            sb.append("【お問い合わせ内容】:\n").append(content).append("\n");
            sb.append("--------------------------------------------------\n\n");
            sb.append("管理画面より対応ステータスをご確認ください。");

            message.setText(sb.toString());

            // メール送信実行
            Transport.send(message);

        } catch (Exception e) {
            System.err.println("メール送信に失敗しました: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @WebServlet("/LoginServlet") 
 * ブラウザからこのプログラムを呼び出せるようになる
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ログイン画面の「送信」ボタンが押された時にここが動く
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // 日本語が化けないようにする設定
        request.setCharacterEncoding("UTF-8");

        // index.jspの入力欄から値を取り出す
        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        // 文字数チェック
        if (user != null && user.length() >= 255) {
            // 255文字以上なら、エラー番号「2」を付けて戻す
            response.sendRedirect("index.jsp?error=2");
            return; // ここで処理を終了させる
        }

        // ログイン判定と振り分け
        if ("admin".equals(user) && "0000".equals(pass)) {
            // 管理者なら success.jsp へ
            response.sendRedirect("AdminServlet");

        } else if ("guest".equals(user) && "1111".equals(pass)) {
            // 一般ユーザーなら user.jsp へ
            response.sendRedirect("user.jsp");

        } else {
            // どちらでもなければエラー
            response.sendRedirect("index.jsp?error=1");
        }
    }
}

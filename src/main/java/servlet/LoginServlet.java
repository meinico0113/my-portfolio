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

        // 2. 判定（今はテスト用に admin / 0000 で固定）
        if ("admin".equals(user) && "0000".equals(pass)) {
            // 合格：成功画面（success.jsp）へ送る
            response.sendRedirect("success.jsp");
        } else {
            // 不合格：エラーの印（error=1）を付けてログイン画面に戻す
            response.sendRedirect("index.jsp?error=1");
        }
    }
}

package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Account;

// アプリケーション内の全リクエストを対象にする
@WebFilter("/*")
public class AuthFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初期化処理
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // キャッシュの無効化（ログアウト後の「戻るボタン」や「URL直打ち」で古い画面を表示させない対策）
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
        httpResponse.setHeader("Pragma", "no-cache"); // HTTP 1.0
        httpResponse.setDateHeader("Expires", 0); // Proxies

        HttpSession session = httpRequest.getSession(false);

        // リクエストされたURLのパスを取得
        String requestURI = httpRequest.getRequestURI();
        String contextPath = httpRequest.getContextPath();
        String path = requestURI.substring(contextPath.length());

        // ログインしなくてもアクセスできる「公開ページ」や「ログイン画面・処理」のパスを定義
        // 直接URLを入力してアクセスしていいページのパスだけを記載する
        boolean isPublicPage = path.equals("/") || 
                               path.equals("/index.jsp") || 
                               path.equals("/top.jsp") || 
                               path.equals("/contact.jsp") ||
                               path.equals("/userDetail.jsp") ||
                               path.equals("/LoginServlet") ||
                               path.equals("/ContactServlet") ||
                               path.equals( "/UserRankingServlet") ||
                               requestURI.contains("/UserDetailServlet") ||
                               path.startsWith("/uploads/");

        // 2. 「管理者専用」のページやサーブレットのパス定義（例：パスに "/admin/" を含む、または特定のサーブレット）
        boolean isAdminPage = path.startsWith("/admin/") || 
                              path.contains("Admin") || 
                              path.equals("/AdminServlet"); // ← 実際の管理画面のパスに合わせて変更してください

        // セッションからログインユーザー情報（Accountオブジェクト）を取得
Account loginUser = (session != null) ? (Account) session.getAttribute("account") : null;

if (!isPublicPage && loginUser == null) {
    // ① 未ログインの場合
    httpResponse.sendRedirect(contextPath + "/UserRankingServlet");
    return;
}

if (loginUser != null) {
    // Accountオブジェクトからロールを取得する（LoginServletのロジックに合わせる）
    String role = loginUser.getRole(); // "admin" などが入る

    // ② 管理者（"admin"）でログインしている場合
    if ("admin".equals(role)) {
        if (path.equals("/UserServlet") || path.equals("/userServlet")) { // 一般用のページにアクセスした場合
            httpResponse.sendRedirect(contextPath + "/AdminServlet");
            return;
        }
    }
    
    // ③ 一般ユーザー（"admin" 以外）でログインしている場合
    if (!"admin".equals(role)) { // または一般用のロール判定
        // 一般ユーザーが「管理者画面（isAdminPage）」にアクセスした場合
        if (isAdminPage) {
            httpResponse.sendRedirect(contextPath + "/UserServlet");
            return;
        }
    }
}

        // すべてのチェックを通過したらそのまま通す
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {}
}
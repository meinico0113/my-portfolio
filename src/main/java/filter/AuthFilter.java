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

        // セッションからログインユーザー情報を取得
        Account account = (session != null) ? (Account) session.getAttribute("account") : null;

        // 1. 公開ページは誰でもアクセスOK
        if (isPublicPage) {
            chain.doFilter(request, response);
            return;
        }

        // 2. 未ログインの場合のアクセス制御
        if (account == null) {
            httpResponse.sendRedirect(contextPath + "/UserRankingServlet"); 
            return;
        }

        // 3. ログイン済みユーザーの権限（ロール）チェック
        // 管理者専用ページのパス（例: /admin/ で始まるURLや、AdminServletなど）
        boolean isAdminPage = path.startsWith("/admin/") || path.equals("/AdminServlet");

        if (isAdminPage) {
            // 管理者専用ページにアクセスしようとした場合
            if ("admin".equals(account.getRole())) {
                // 管理者であればアクセスを許可
                chain.doFilter(request, response);
            } else {
                // 一般ユーザーが管理者ページに侵入しようとしたら、一般画面へ強制送還
                httpResponse.sendRedirect(contextPath + "/UserServlet");
            }
        } else {
            // 一般ページへのアクセスは、ログインしていれば許可
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        // 破棄処理
    }
}
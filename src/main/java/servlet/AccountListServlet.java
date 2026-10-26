package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import dao.AccountDAO;
import model.Account;

@WebServlet("/admin/accountList")
public class AccountListServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

try{

    int page = 1; // デフォルトは1ページ目を表示する
    int limit = 5; // 1ページあたりに表示する件数は5件と決める

    String pageParam = request.getParameter("page");

    // もしURLに「?page=2」などの指定があれば、その数字を現在のページ番号にする
    // 指定がなければ、初期値の「1」のまま進む
    if(pageParam != null){
        page = Integer.parseInt(pageParam);
    }

    // 取得する開始位置（offset）の計算
    // 1ページ目 → (1 - 1) * 5 = 0件目から（最初から）
    // 2ページ目 → (2 - 1) * 5 = 5件目から
    // 3ページ目 → (3 - 1) * 5 = 10件目から
    int offset = (page - 1) * limit;

    AccountDAO dao = new AccountDAO();

    // 計算した開始位置（offset）から、5件（limit）だけアカウントを取得する
    List<Account> list = dao.findByPage(offset, limit);

    // データベースにある、すべてのアカウントの総件数を取得
    int total = dao.countAll();

    // 総ページ数の計算
    // 例：13件（総件数）÷ 5件（1ページ分） ＝ 2.6ページ
    // 2.6ページ = 3ページ目まで必要 = 端数を切り上げる必要がある
    // Math.ceil() =「小数点以下を切り上げる」命令
    // ※ (double) をつけて一度わざと小数点の計算にしてから切り上げ、最後に (int) で整数（3）に戻す
    int totalPages = (int)Math.ceil((double)total / limit);

    // JSPに渡す
    request.setAttribute("accountList", list); // 今のページに表示する全員分のデータ
    request.setAttribute("currentPage", page); // いま自分が何ページ目を見ているか
    request.setAttribute("totalPages", totalPages); // 全部で何ページあるか

    // アカウント一覧画面へ
    request.getRequestDispatcher("/adminAccountList.jsp").forward(request, response);

}catch(Exception e){
    e.printStackTrace();
}
    }
}

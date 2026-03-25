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

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

try{

    int page = 1;
    int limit = 5;

    // URLパラメータ取得
    String pageParam = request.getParameter("page");
    if(pageParam != null){
        page = Integer.parseInt(pageParam);
    }

    int offset = (page - 1) * limit;

    AccountDAO dao = new AccountDAO();

    // ① 5件だけ取得
    List<Account> list = dao.findByPage(offset, limit);

    // ② 総件数
    int total = dao.countAll();

    // ③ 総ページ数
    int totalPages = (int)Math.ceil((double)total / limit);

    // JSPに渡す
    request.setAttribute("accountList", list);
    request.setAttribute("currentPage", page);
    request.setAttribute("totalPages", totalPages);

    request.getRequestDispatcher("/admin/adminAccountList.jsp")
           .forward(request, response);

}catch(Exception e){
    e.printStackTrace();
}
    }
}

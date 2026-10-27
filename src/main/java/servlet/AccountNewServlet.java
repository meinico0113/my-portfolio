package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;

@WebServlet("/admin/accountNew")
public class AccountNewServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 空のAccountオブジェクトを用意する
        Account account = new Account();
        account.setRole("admin"); // 初期選択を管理者にしておく
        account.setName("");     // 名前の初期値を空文字にする

        // リクエストスコープにセット
        request.setAttribute("account", account);

        // JSPへフォワード
        request.getRequestDispatcher("/adminAccountNew.jsp").forward(request, response);
    }
}
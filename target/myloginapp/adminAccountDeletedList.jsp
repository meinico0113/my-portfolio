<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Account" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>削除済みアカウント一覧</title>
</head>
<body>

<h2>削除済みアカウント一覧</h2>

<div style="margin-bottom: 10px;">
    <a href="<%= request.getContextPath() %>/admin/accountList">
        アカウント一覧（通常）に戻る
    </a>
</div>

<table border="1" style="border-collapse: collapse; width: 100%;">
    <tr style="background-color: #eee;">
        <th>ID</th>
        <th>名前</th>
        <th>メール</th>
        <th>ステータス</th>
        <th>復元</th>
        <th>完全削除</th>
    </tr>

    <%
    List<Account> list = (List<Account>)request.getAttribute("deletedList");
    if (list != null && !list.isEmpty()) {
        for(Account account : list) {
    %>
    <tr>
        <td><%= account.getId() %></td>
        <td><%= account.getName() %></td>
        <td><%= account.getEmail() %></td>
        <td>
            <%-- 削除済みであることを明示 --%>
            <span style="color: red;">削除済み</span>
        </td>
        <td style="text-align: center;">
            <a href="<%= request.getContextPath() %>/admin/accountRestore?id=<%= account.getId() %>">
                復元
            </a>
        </td>
        <td style="text-align: center;">
            <%-- 完全削除は危険な操作なので、JavaScriptで確認ダイアログを出す --%>
            <a href="<%= request.getContextPath() %>/admin/accountHardDelete?id=<%= account.getId() %>" 
               onclick="return confirm('このアカウントをデータベースから完全に削除します。よろしいですか？');" 
               style="color: red;">
                完全削除
            </a>
        </td>
    </tr>
    <% 
        }
    } else { 
    %>
    <tr>
        <td colspan="6" style="text-align: center;">削除済みのデータはありません。</td>
    </tr>
    <% } %>
</table>

</body>
</html>
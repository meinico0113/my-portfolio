<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Account" %>

<h2>アカウント一覧</h2>

<a href="<%= request.getContextPath() %>/admin/accountNew">
アカウント追加
</a>

<table border="1">

<tr>
<th>ID</th>
<th>名前</th>
<th>メール</th>
<th>ステータス</th>
<th>編集</th>
<th>削除</th>
<th>切替</th>
</tr>

<%

List<Account> list =
(List<Account>)request.getAttribute("accountList");

for(Account account : list){

%>

<tr>

<td><%= account.getId() %></td>
<td><%= account.getName() %></td>
<td><%= account.getEmail() %></td>

<td>
<% if(account.getStatus()==1){ %>
アクセス許可
<% }else{ %>
アクセス禁止
<% } %>
</td>

<td>
<a href="<%= request.getContextPath() %>/admin/accountEdit?id=<%= account.getId() %>">
編集
</a>
</td>
<td>削除</td>
<td>切替</td>

</tr>

<% } %>

</table>
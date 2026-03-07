<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>

<h2>カテゴリ一覧</h2>

<a href="<%= request.getContextPath() %>/admin/categoryNew">
カテゴリ追加
</a>

<table border="1">

<tr>
<th>ID</th>
<th>名前</th>
<th>編集</th>
<th>削除</th>
</tr>

<%
List<Category> list = (List<Category>)request.getAttribute("categoryList");

for(Category category : list){
%>

<tr>
<td><%= category.getId() %></td>
<td><%= category.getName() %></td>

<td>
<a href="<%= request.getContextPath() %>/admin/categoryEdit?id=<%= category.getId() %>">
編集
</a>
</td>

<td>
<a href="<%= request.getContextPath() %>/admin/categoryDelete?id=<%= category.getId() %>">
削除
</a>
</td>

</tr>

<% } %>

</table>
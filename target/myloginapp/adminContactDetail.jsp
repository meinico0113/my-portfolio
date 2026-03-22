<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Contact" %>

<%
    Contact contact = (Contact) request.getAttribute("contact");
%>

<h2>お問い合わせ詳細</h2>

<p><strong>カテゴリー：</strong> <%= contact.getCategory() %></p>
<p><strong>本文：</strong><br>
<%= contact.getContent().replaceAll("\r\n|\n", "<br>") %>
</p>

<form action="<%= request.getContextPath() %>/admin/updateStatus" method="post">
<input type="hidden" name="id" value="<%= contact.getId() %>">

<p><strong>ステータス：</strong>
<select name="status">

<option value="未対応"
<%= "未対応".equals(contact.getStatus()) ? "selected" : "" %>>
未対応
</option>

<option value="対応中"
<%= "対応中".equals(contact.getStatus()) ? "selected" : "" %>>
対応中
</option>

<option value="対応済み"
<%= "対応済み".equals(contact.getStatus()) ? "selected" : "" %>>
対応済み
</option>
</select>
</p>

<input type="submit" value="更新">
</form>
<p><strong>作成日時：</strong> <%= contact.getCreatedAt() %></p>

<p>
<a href="<%= request.getContextPath() %>/admin/contactList">
    一覧へ戻る
</a>
</p>
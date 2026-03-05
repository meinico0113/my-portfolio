<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Contact" %>

<%
    Contact contact = (Contact) request.getAttribute("contact");
%>

<h2>お問い合わせ詳細</h2>

<p><strong>カテゴリー：</strong> <%= contact.getCategory() %></p>
<p><strong>本文：</strong><br><%= contact.getContent() %></p>
<p><strong>ステータス：</strong> <%= contact.getStatus() %></p>
<p><strong>作成日時：</strong> <%= contact.getCreatedAt() %></p>

<p>
<a href="<%= request.getContextPath() %>/admin/contactList">
    一覧へ戻る
</a>
</p>
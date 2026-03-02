<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Contact" %>

<html>
<head>
    <title>お問い合わせ一覧</title>
</head>
<body>

<h2>お問い合わせ一覧</h2>

<table border="1">
<tr>
    <th>カテゴリー</th>
    <th>本文（10文字）</th>
    <th>ステータス</th>
    <th>詳細</th>
</tr>

<%
    List<Contact> list = (List<Contact>) request.getAttribute("contactList");
    if (list != null) {
        for (Contact contact : list) {
%>
<tr>
    <td><%= contact.getCategory() %></td>
    <td>
        <%= contact.getContent().length() > 10
            ? contact.getContent().substring(0, 10)
            : contact.getContent() %>
    </td>
    <td><%= contact.getStatus() %></td>
    <td>
        <a href="AdminContactDetailServlet?id=<%= contact.getId() %>">
            詳細
        </a>
    </td>
</tr>
<%
        }
    }
%>

</table>

</body>
</html>
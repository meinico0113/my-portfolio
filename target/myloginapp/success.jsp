<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Category" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン成功</title>
</head>
<body>
    <h1>ログイン成功！</h1>
    <p>ようこそ、管理画面へ。</p>

    <p>
        <a href="<%= request.getContextPath() %>/admin/accountList">
        アカウント管理
        </a>
    </p>

    <h2>いいねランキング</h2>

<%
List<String> rankingList =
    (List<String>) request.getAttribute("rankingList");

if (rankingList != null) {
    int rank = 1;
    for (String post : rankingList) {
%>
        <p><%= rank %>位：<%= post %></p>
<%
        rank++;
    }
}
%>

<p>
<a href="<%= request.getContextPath() %>/admin/contactList">
    問い合わせ一覧
</a>
</p>

    <a href="index.jsp">ログアウト（戻る）</a>
</body>
</html>
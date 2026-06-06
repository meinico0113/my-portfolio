<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- これが必要 --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>一般ユーザー画面</title>
<style>
    .user-card { border: 1px solid #ddd; margin: 10px; padding: 10px; border-radius: 8px; width: 300px; }
    .like-btn { background-color: #ff4d4d; color: white; border: none; padding: 5px 10px; cursor: pointer; border-radius: 4px; }
</style>
</head>
<body>
    <h1>ようこそ、一般画面へ</h1>

    <hr>
    <div class="like-info">
    <span>あなたの現在の「いいね」数：</span>
    <span class="likes-count">${account.likes}</span>
    </div>
    <a class="menu-link" href="UserEditServlet">プロフィール</a><br>
    <a href="${pageContext.request.contextPath}/settings.jsp">設定（メアド・パスワード変更）</a><br>
    <a href="index.jsp">ログアウト</a>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%-- これが必要 --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>一般ユーザー画面</title>
</head>
<body>
    <h1>一般画面</h1>

    <hr>
    <div class="like-info">
    <span>あなたの現在の「いいね」数：</span>
    <span class="likes-count">${account.likes}</span>
    </div>
    <a href="${pageContext.request.contextPath}/UserEditServlet">プロフィールの編集</a><br>
    <a href="${pageContext.request.contextPath}/settings.jsp">設定（メアド・パスワード）</a><br>
    <a href="${pageContext.request.contextPath}/LogoutServlet">ログアウト</a>
</body>
</html>
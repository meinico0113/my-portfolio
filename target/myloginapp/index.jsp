<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>myportfolio</title>
    <style>
        body { font-family: sans-serif; text-align: center; margin-top: 50px; }
        form { display: inline-block; text-align: left; border: 1px solid #ccc; padding: 20px; border-radius: 8px; }
        .error-msg { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <h1>ログイン画面</h1>

    <%-- エラーメッセージ表示エリア --%>
    <c:if test="${not empty error}">
        <p class="error-msg">${error}</p>
    </c:if>

    <form action="LoginServlet" method="post">
        名前：<br>
        <input type="text" name="name" value="${fn:escapeXml(account.name)}">
        <br><br>
        
        パスワード：<br>
        <input type="password" name="password">
        <br><br>
        
        <button type="submit">ログイン</button>
    </form>
</body>
</html>
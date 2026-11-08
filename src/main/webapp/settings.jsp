<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>設定</title>
    <style>
        .error-msg { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <h1>設定</h1>
    <hr>
    <%-- エラーメッセージ表示エリア --%>
    <c:if test="${not empty error}">
        <p class="error-msg">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/SettingsServlet" method="post">

        メールアドレス：<br>
        <input type="text" name="email" value="${fn:escapeXml(account.email)}">
        <br><br>

        新しいパスワード：<br>
        <input type="password" name="password">
        <br><br>

        <input type="submit" value="変更を保存する">

    </form>

    <br>
    <a href="${pageContext.request.contextPath}/UserServlet">一般画面へ戻る</a>
</body>
</html>
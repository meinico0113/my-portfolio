<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>カテゴリ追加</title>
    <style>
        .error-msg { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <h2>カテゴリ追加</h2>
    <hr>

    <%-- エラーメッセージ表示エリア --%>
    <c:if test="${not empty error}">
        <p class="error-msg">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/admin/categoryNew" method="post">
    カテゴリ名
    <input type="text" name="name" value="${fn:escapeXml(name)}">
    <input type="submit" value="登録">
    </form>

    <p>
        <a href="${pageContext.request.contextPath}/admin/categoryList">カテゴリ一覧に戻る</a>
    </p>
</body>
</html>
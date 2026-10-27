<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カテゴリ追加</title>
<style>
    .error-msg { color: red; font-weight: bold; }
    .success-msg { color: green; font-weight: bold; }
</style>
</head>
<body>

<h2>カテゴリ追加</h2>
<hr>

<%-- エラーメッセージ表示エリア --%>
<c:if test="${not empty error}">
    <p class="error-msg">${error}</p>
</c:if>

<%-- 成功メッセージ（リダイレクト時） --%>
<c:if test="${param.success == '1'}">
    <p class="success-msg">カテゴリを追加しました！</p>
</c:if>

<form action="${pageContext.request.contextPath}/admin/categoryNew" method="post">

    カテゴリ名  
    <input type="text" name="name" value="${name}">

    <input type="submit" value="登録">

</form>

    <p>
    <a href="${pageContext.request.contextPath}/admin/categoryList">
        カテゴリ一覧に戻る
    </a>
</p>
</body>
</html>
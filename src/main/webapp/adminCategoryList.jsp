<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>アカウント編集</title>
    <style>
        .error-msg { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <h1>カテゴリ一覧</h1>
    <hr>

    <%-- エラーメッセージ表示エリア --%>
    <c:if test="${not empty error}">
        <p class="error-msg">${error}</p>
    </c:if>

<table border="1" style="border-collapse: collapse; width: 100%;">
<thead>
    <tr style="background-color: #eee;">
        <th>名前</th>
        <th>編集</th>
        <th>削除</th>
    </tr>
</thead>

<c:forEach var="category" items="${categoryList}">
    <tr>
        <td>${fn:escapeXml(category.name)}</td>

        <td>
            <a href="${pageContext.request.contextPath}/admin/categoryEdit?id=${category.id}">
            編集
            </a>
        </td>

        <td>
            <a href="${pageContext.request.contextPath}/admin/categoryDelete?id=${category.id}">
            削除
            </a>
        </td>
    </tr>
</c:forEach>
</table>

<p>
<a href="${pageContext.request.contextPath}/admin/categoryNew">カテゴリ追加</a>
</p>
<p>
<a href="${pageContext.request.contextPath}/admin/contactList">問い合わせ一覧に戻る</a>
</p>
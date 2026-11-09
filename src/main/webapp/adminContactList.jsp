<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>問い合わせ一覧</title>
    <style>
        .error-msg { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <h1>問い合わせ一覧</h1>
    <hr>

    <%-- エラーメッセージ表示エリア --%>
    <c:if test="${not empty error}">
        <p class="error-msg">${error}</p>
    </c:if>

    <table border="1" style="border-collapse: collapse; width: 100%;">
    <thead>
        <tr style="background-color: #eee;">
            <th>カテゴリー</th>
            <th>本文（10文字）</th>
            <th>ステータス</th>
            <th>詳細</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="contact" items="${contactList}">
            <tr>
                <td>${fn:escapeXml(contact.category)}</td>
                <td>
                    <%-- 文字数が10文字より多い場合は10文字で切って「...」を貼る --%>
                    <c:choose>
                        <c:when test="${fn:length(contact.content) > 10}">
                            ${fn:escapeXml(fn:substring(contact.content, 0, 10))}...
                        </c:when>
                        <c:otherwise>
                            ${fn:escapeXml(contact.content)}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${fn:escapeXml(contact.status)}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/contactDetail?id=${contact.id}">詳細</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
    </table>
    <p>
        <a href="${pageContext.request.contextPath}/admin/categoryList">カテゴリー一覧</a>
    </p>
    <p>
        <a href="${pageContext.request.contextPath}/AdminServlet">管理者画面に戻る</a>
    </p>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者画面</title>
</head>
<body>
    <h1>管理者画面</h1>
    <hr>
    <h2>いいねランキング</h2>
    
    <%-- ランキングデータが空（0件）の場合の判定 --%>
    <c:if test="${empty rankingList}">
        <p>現在ランキングデータはありません。</p>
    </c:if>

    <%-- ランキングデータがある場合のループ処理（varStatusで順位を自動カウント） --%>
    <c:forEach var="record" items="${rankingList}" varStatus="status">
        <p>${status.count}位：${record}</p>
    </c:forEach>

    <p>
        <a href="${pageContext.request.contextPath}/admin/accountList">アカウント一覧</a>
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/admin/contactList">問い合わせ一覧</a>
    </p>

    <p>
    <a href="${pageContext.request.contextPath}/LogoutServlet">ログアウト</a>
    </p>
</body>
</html>
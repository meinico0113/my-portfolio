<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント一覧</title>
</head>
<body>
    <h2>アカウント一覧</h2>
    <hr>

    <a href="${pageContext.request.contextPath}/admin/accountNew">アカウント追加</a>
    <br>
    <a href="${pageContext.request.contextPath}/admin/accountDeletedList">アカウント削除一覧</a>
    <br>
    <br>

    <%-- 表の項目名 --%>
    <table border="1">
    <tr>
        <th>名前</th>
        <th>メールアドレス</th>
        <th>ステータス</th>
        <th>編集</th>
        <th>削除</th>
        <th>切替</th>
    </tr>

    <%-- 表の中身 --%>
    <c:forEach var="account" items="${accountList}">
        <tr>
            <td><c:out value="${account.name}" /></td>
            <td><c:out value="${account.email}" /></td>

            <td>
                <%-- ステータスの条件分岐 --%>
                <c:choose>
                    <c:when test="${account.status == 0}">アクセス許可</c:when>
                    <c:otherwise>アクセス禁止</c:otherwise>
                </c:choose>
            </td>

            <td>
                <a href="${pageContext.request.contextPath}/admin/accountEdit?id=${account.id}">編集</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/accountDelete?id=${account.id}">削除</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/admin/accountStatusToggle?id=${account.id}">切替</a>
            </td>
        </tr>
    </c:forEach>
    </table>

    <%-- ページネーション部分 --%>
    <div style="margin-top:20px;">
        <c:forEach begin="1" end="${totalPages}" var="i">
            <c:choose>
                <c:when test="${i == currentPage}">
                    <strong>${i}</strong>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/admin/accountList?page=${i}">${i}</a>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </div>

    <br>
    <a href="${pageContext.request.contextPath}/AdminServlet">管理者画面に戻る</a>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>アカウント削除済み一覧</title>
</head>
<body>

<h2>アカウント削除済み一覧</h2>
<hr>

<%-- エラーメッセージ表示エリア --%>
<c:if test="${not empty error}">
    <p class="error-msg">${error}</p>
</c:if>

<table border="1" style="border-collapse: collapse; width: 100%;">
<thead>
    <tr style="background-color: #eee;">
        <th>メールアドレス</th>
        <th>名前</th>
        <th>ステータス</th>
        <th>完全削除</th>
        <th>復活</th>
    </tr>
</thead>

    <c:choose>
        <c:when test="${not empty deletedList}">
            <c:forEach var="account" items="${deletedList}">
                <tr>
                    <td>${account.email}</td>
                    <td>${account.name}</td>
                    <td>
                        <%-- 削除済みであることを明示 --%>
                        <span style="color: red;">削除済み</span>
                    </td>
                    <td>
                        <%-- 完全削除は危険な操作なので、JavaScriptで確認ダイアログを出す --%>
                        <a href="${pageContext.request.contextPath}/admin/accountHardDelete?id=${account.id}" 
                           onclick="return confirm('このアカウントを完全削除します。よろしいですか？');" 
                           style="color: red;">
                            完全削除
                        </a>
                    </td>
                    <td>
                        <a href="${pageContext.request.contextPath}/admin/accountRestore?id=${account.id}">
                            復活
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="5" style="text-align: center;">削除済みのデータはありません。</td>
            </tr>
        </c:otherwise>
    </c:choose>
</table>

<div style="margin-top: 10px;">
    <a href="${pageContext.request.contextPath}/admin/accountList">
        アカウント一覧に戻る
    </a>
</div>

</body>
</html>
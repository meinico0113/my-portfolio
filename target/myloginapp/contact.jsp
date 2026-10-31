<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>お問い合わせ</title>
    <style>
        label { display: block; margin-bottom: 5px; font-weight: bold; }

        select, textarea {
            border-radius: 4px;
        }

        .error-msg { color: red; font-weight: bold; }
    </style>
</head>
    <body>
        <h1>お問い合わせ</h1>
        <hr>

        <%-- エラーメッセージ表示エリア --%>
        <c:if test="${not empty error}">
            <p class="error-msg">${error}</p>
        </c:if>

        <form action="ContactServlet" method="post">
            <div>
                <label>カテゴリー</label>
                <select name="category">
                    <%-- DBから取得したリストをループ表示 --%>
                    <c:forEach var="cat" items="${categoryList}">
                        <option value="${fn:escapeXml(cat.name)}">${fn:escapeXml(cat.name)}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <label>内容</label>
                <textarea name="content" rows="5" required placeholder="お問い合わせ内容を入力してください"></textarea>
            </div>

            <button type="submit">送信する</button>
        </form>

        <p>
            <a href="${pageContext.request.contextPath}/UserListServlet">公開画面へ戻る</a>
        </p>
    </body>
</html>
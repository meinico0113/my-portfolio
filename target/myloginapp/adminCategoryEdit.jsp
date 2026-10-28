<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>カテゴリ編集</title>
    <style>
        .error-msg { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <h2>カテゴリ編集</h2>
    <hr>

    <%-- エラーメッセージ表示エリア --%>
    <c:if test="${not empty error}">
        <p class="error-msg">${error}</p>
    </c:if>

    <form action="${pageContext.request.contextPath}/admin/categoryUpdate" method="post" onsubmit="return validateForm();">
        <input type="hidden" name="id" value="${category.id}">
    
        カテゴリ名 
        <input type="text" id="categoryName" name="name" value="${fn:escapeXml(category.name)}">
    
        <input type="submit" value="更新">
    </form>
    <p>
        <a href="${pageContext.request.contextPath}/admin/categoryList">カテゴリ一覧に戻る</a>
    </p>    

    <script>
    function validateForm() {
        const input = document.getElementById('categoryName');
        if (!input) return true;

        const val = input.value.trim();

        if (val === "") {
            alert("カテゴリ名を入力してください。");
            return false;
        }
        if (val.length > 255) {
            alert("カテゴリ名は255文字以内で入力してください。");
            return false;
        }
        return true;
    }
    </script>
</body>
</html>
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

<%-- サーバー側のエラーメッセージ表示 --%>
<c:if test="${not empty errorMessage}">
    <p class="error-msg">${errorMessage}</p>
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

<script>
// JavaScriptによる送信前の文字数チェック
function validateForm() {
    const input = document.getElementById('categoryName');
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

    <p>
    <a href="${pageContext.request.contextPath}/admin/categoryList">
        カテゴリ一覧に戻る
    </a>
</p>
</body>
</html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>設定</title>
</head>
<body>

<h2>設定</h2>
<hr>

<%-- エラーメッセージ・完了メッセージ表示領域 --%>
<p id="messageDisplay" 
   data-message="<c:out value='${message}' />"
   style="color: red; font-weight: bold;"></p>

<%-- 設定変更フォーム --%>
<form action="${pageContext.request.contextPath}/SettingsServlet" method="post">

    メールアドレス：<br>
    <c:choose>
        <c:when test="${not empty email}">
            <input type="text" name="email" value="<c:out value='${email}' />">
        </c:when>
        <c:otherwise>
            <input type="text" name="email" value="<c:out value='${sessionScope.account.email}' />">
        </c:otherwise>
    </c:choose>
    <br><br>

    新しいパスワード：<br>
    <input type="password" name="password" value="<c:out value='${password}' />">
    <br><br>

    <input type="submit" value="変更を保存する">

</form>

<br>
<a href="${pageContext.request.contextPath}/UserServlet">一般画面へ戻る</a>

<script>
// 画面が読み込まれた時に自動で動く処理
window.onload = function() {
    // 隠し属性（data-message）からメッセージを取得
    const msgElement = document.getElementById("messageDisplay");
    const serverMessage = msgElement.getAttribute("data-message");
    
    // メッセージがあれば安全にテキストとして表示
    if (serverMessage && serverMessage !== "") {
        msgElement.innerText = serverMessage;
    }
};
</script>

</body>
</html>
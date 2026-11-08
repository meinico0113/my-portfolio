<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>プロフィール編集</title>
    <style>
        .error-msg { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <h1>プロフィール編集</h1>
    <hr>

    <%-- エラーメッセージ表示エリア --%>
    <c:if test="${not empty error}">
        <p class="error-msg">${error}</p>
    </c:if>

    <!-- enctype="multipart/form-data"> 画像を送信するために必要 -->
    <form action="${pageContext.request.contextPath}/UserUpdateServlet" method="post" enctype="multipart/form-data">
        
        <!-- ID保持用 -->
        <input type="hidden" name="id" value="${account.id}">

        <p>
            プロフィール画像<input type="file" name="image">
            <br>

            現在の画像<br>
            <img src="${pageContext.request.contextPath}/uploads/${not empty account.imagePath ? account.imagePath : 'default.png'}" width="100" alt="現在の画像">
        </p>

        <p>
            名前<br>
            <input type="text" name="name" value="${fn:escapeXml(account.name)}" required>
        </p>

        <p>
            ふりがな<br>
            <input type="text" name="kana" value="${fn:escapeXml(account.kana)}" required>
        </p>

        <p>
            性別<br>
            <label><input type="radio" name="gender" value="male" ${account.gender == 'male' ? 'checked' : ''}> 男性</label>
            <label><input type="radio" name="gender" value="female" ${account.gender == 'female' ? 'checked' : ''}> 女性</label>
        </p>

        <p>
            年齢<br>
            <input type="number" name="age" value="${fn:escapeXml(account.age)}" required>
        </p>

        <p>
            自己紹介<br>
            <textarea name="profile">${fn:escapeXml(account.profile)}</textarea>
        </p>

        <p>
            <input type="submit" value="更新">
        </p>
    </form>

    <p>
        <a href="${pageContext.request.contextPath}/UserServlet">一般画面に戻る</a>
    </p>

</body>
</html>
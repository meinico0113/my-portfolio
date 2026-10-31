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
    .preview-img { max-width: 150px; max-height: 150px; display: block; margin: 5px 0; }
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
    <input type="hidden" name="id" value="${userProfile.id}">

    <p>
        プロフィール画像<br>
        <c:if test="${not empty userProfile.imagePath}">
            <img src="${pageContext.request.contextPath}/uploads/${userProfile.imagePath}" class="preview-img" alt="現在の画像">
        </c:if>
        <input type="file" name="profileImage" accept="image/*">
    </p>

    <p>
        名前<br>
        <input type="text" name="name" value="${fn:escapeXml(userProfile.name)}" required>
    </p>

    <p>
        ふりがな<br>
        <input type="text" name="kana" value="${fn:escapeXml(userProfile.kana)}" required>
    </p>

    <p>
        性別<br>
        <label><input type="radio" name="gender" value="male" ${userProfile.gender == 'male' ? 'checked' : ''}> 男性</label>
        <label><input type="radio" name="gender" value="female" ${userProfile.gender == 'female' ? 'checked' : ''}> 女性</label>
    </p>

    <p>
        年齢<br>
        <input type="number" name="age" value="${fn:escapeXml(userProfile.age)}" required>
    </p>

    <p>
        自己紹介<br>
        <textarea name="introduction" rows="5" cols="40">${fn:escapeXml(userProfile.profile)}</textarea>
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
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>プロフィール編集</title>
<style>
    .form-group { margin-bottom: 15px; }
    .form-group label { display: block; font-weight: bold; margin-bottom: 5px; }
    .preview-img { max-width: 150px; max-height: 150px; display: block; margin-bottom: 10px; }
    .btn-submit { background-color: #4CAF50; color: white; padding: 10px 15px; border: none; cursor: pointer; border-radius: 4px; }
</style>
</head>
<body>

    <h1>プロフィール編集</h1>
    
    <!-- 画像アップロードがあるため multipart/form-data を指定 -->
    <form action="ProfileUpdateServlet" method="post" enctype="multipart/form-data">
        
        <!-- 1. プロフィール画像 -->
        <div class="form-group">
            <label>プロフィール画像</label>
            <!-- 現在の画像がある場合は表示 -->
            <c:if test="${not empty userProfile.imagePath}">
                <img src="${userProfile.imagePath}" class="preview-img" alt="現在のプロフィール画像">
            </c:if>
            <input type="file" name="profileImage" accept="image/*">
        </div>

        <!-- 2. 名前 -->
        <div class="form-group">
            <label for="name">名前</label>
            <input type="text" id="name" name="name" value="${c:out(userProfile.name)}" required>
        </div>

        <!-- 3. フリガナ -->
        <div class="form-group">
            <label for="furigana">フリガナ</label>
            <input type="text" id="furigana" name="furigana" value="${c:out(userProfile.furigana)}" required>
        </div>

        <!-- 4. 性別 -->
        <div class="form-group">
            <label>性別</label>
            <label><input type="radio" name="gender" value="male" ${userProfile.gender == 'male' ? 'checked' : ''}> 男性</label>
            <label><input type="radio" name="gender" value="female" ${userProfile.gender == 'female' ? 'checked' : ''}> 女性</label>
            <label><input type="radio" name="gender" value="other" ${userProfile.gender == 'other' ? 'checked' : ''}> その他</label>
        </div>

        <!-- 5. 年齢 -->
        <div class="form-group">
            <label for="age">年齢</label>
            <input type="number" id="age" name="age" value="${userProfile.age}" min="0" max="150">
        </div>

        <!-- 6. 自己紹介 -->
        <div class="form-group">
            <label for="introduction">自己紹介</label>
            <textarea id="introduction" name="introduction" rows="5" cols="40">${c:out(userProfile.profile)}</textarea>
        </div>

        <button type="submit" class="btn-submit">変更を保存する</button>
    </form>

    <p><a href="user.jsp">一般画面に戻る</a></p>

</body>
</html>
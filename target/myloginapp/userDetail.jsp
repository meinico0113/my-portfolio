<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>${user.kana}さんの詳細</title>
<style>
    .detail-card { border: 1px solid #ddd; padding: 20px; border-radius: 10px; max-width: 500px; margin: 20px auto; font-family: sans-serif; }
    .profile-img { width: 100%; max-width: 300px; height: auto; border-radius: 8px; margin-bottom: 15px; display: block; margin-left: auto; margin-right: auto; }
    .like-btn { background-color: #ff4d4d; color: white; border: none; padding: 12px; cursor: pointer; border-radius: 5px; width: 100%; font-size: 16px; font-weight: bold; }
</style>
</head>
<body>
    <div class="detail-card">
        <h1>プロフィール詳細</h1>
        
        <%-- 画像の表示（フォルダ名が images の場合） --%>
        <c:choose>
            <c:when test="${not empty user.imagePath}">
                <img src="images/${user.imagePath}" class="profile-img">
            </c:when>
            <c:otherwise>
                <div style="background: #eee; height: 200px; text-align: center; line-height: 200px;">No Image</div>
            </c:otherwise>
        </c:choose>

        <p><strong>名前：</strong> ${user.name}</p>
        <p><strong>フリガナ：</strong> ${user.kana}</p>
        <p><strong>性別：</strong> ${user.gender} / <strong>年齢：</strong> ${user.age}歳</p>
        <hr>
        <p><strong>自己紹介：</strong><br>${user.profile}</p>
        <p><strong>❤ 現在のいいね数：</strong> ${user.likes}</p>

        <form action="LikeServlet" method="post">
            <input type="hidden" name="targetId" value="${user.id}">
            <button type="submit" class="like-btn">いいね！を送る</button>
        </form>
        
        <div style="margin-top: 20px; text-align: center;">
            <a href="UserListServlet">← ランキングに戻る</a>
        </div>
    </div>
</body>
</html>
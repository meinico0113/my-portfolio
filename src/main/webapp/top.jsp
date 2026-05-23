<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公開画面</title>
<style>
    .user-card { border: 1px solid #ddd; margin: 10px; padding: 10px; border-radius: 8px; width: 300px; }
    
    /* ボタンの共通スタイル */
    .btn { 
        display: inline-block;
        padding: 5px 10px; 
        cursor: pointer; 
        border-radius: 4px; 
        border: none;
        text-decoration: none; /* リンクの下線を消す */
        font-size: 13.333px; /* ボタンのデフォルトフォントサイズに合わせる */
        font-family: Arial;
        color: white;
    }
    
    /* いいねボタン（赤） */
    .like-btn { background-color: #ff4d4d; }
    
    /* 詳細ボタン（グレー） */
    .detail-btn { background-color: #888888; }

    /* 横並びにするためのコンテナ */
    .button-group { display: flex; gap: 10px; margin-top: 10px; }
</style>
</head>
<body>
    <h1>ようこそ、公開画面へ</h1>

    <div style="margin-top: 10px;">
        <a href="<%= request.getContextPath() %>/index.jsp">ログイン</a>
        <a href="ContactServlet">お問い合わせ</a> </div>
    </div>

    <h2>ユーザーランキング</h2>

    <c:forEach var="acc" items="${userList}">
        <div class="user-card">
            <strong>ニックネーム: ${acc.kana}</strong><br> 
            <span>性別: ${acc.gender} / 年齢: ${acc.age}歳</span><br>
            <p>自己紹介: ${acc.profile}</p>
            
            <%-- 非同期更新のために id を付与 --%>
            <p>❤ 現在のいいね数: <strong id="like-count-${acc.id}">${acc.likes}</strong></p>

            <div class="button-group">
                <%-- 詳細ボタン --%>
                <a href="UserDetailServlet?id=${acc.id}" class="btn detail-btn">詳細を見る</a>

                <%-- いいねボタン --%>
                <form action="LikeServlet" method="post" style="margin: 0;">
                    <input type="hidden" name="targetId" value="${acc.id}">
                    <button type="submit" class="btn like-btn">いいね！</button>
                </form>
            </div>
        </div>
    </c:forEach>
</body>
</html>
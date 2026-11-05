<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>アカウント詳細</title>
    <style>
        /* いいねボタンのスタイル */
        .like-btn { 
            display: inline-block;
            padding: 5px 10px; 
            cursor: pointer; /* ボタンに合わせた際、カーソルが指のマークに変わる */
            border-radius: 4px; 
            border: none; /* 要素の周りにある枠線をすべてなくす */
            color: white;
            background-color: #ff4d4d;
        }
    </style>
</head>
<body>
    <h1>アカウント詳細</h1>
    <hr>
    <c:if test="${not empty user}">
        名前：${fn:escapeXml(user.name)}
        <br><br>

        ふりがな：${fn:escapeXml(user.kana)}
        <br><br>

        性別： 
        <c:choose>
            <c:when test="${user.gender == 'male'}">男性</c:when>
            <c:when test="${user.gender == 'female'}">女性</c:when>
        <c:otherwise><c:out value="${fn:escapeXml(user.gender)}" /></c:otherwise>
        </c:choose> 
        <br><br>

        年齢：
        ${user.age != null && user.age != 0 ? user.age : ''}歳
        <br><br>

        自己紹介：
        <br>
        <span>${fn:replace(fn:escapeXml(user.profile), '&#10;', '<br>')}</span>

        現在の画像：<br>
        <c:choose>
            <c:when test="${not empty user.imagePath}">
                <img src="${pageContext.request.contextPath}/uploads/${fn:escapeXml(user.imagePath)}" class="profile-img" width="100" alt="プロフィール画像">
            </c:when>
            <c:otherwise>
                <img src="${pageContext.request.contextPath}/uploads/default.png" class="profile-img" width="100" alt="デフォルト画像">
            </c:otherwise>
        </c:choose>
    </c:if>

    <c:if test="${empty user}">
        <p>ユーザー情報が見つかりませんでした。</p>
    </c:if>

    <!-- いいね数表示といいねボタン -->
        <p>現在のいいね数： <strong id="like-count"><c:out value="${user.likes}" /></strong></p>
        <button type="button" class="like-btn" id="like-btn" data-id="${user.id}">いいね！</button>
    
    <p>
        <a href="${pageContext.request.contextPath}/UserRankingServlet">公開画面に戻る</a>
    </p>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // 画面内から ID名がlike-btnである要素を探して取得する
        const likeBtn = document.getElementById('like-btn');
        if (likeBtn) {
            likeBtn.addEventListener('click', function() {
                const targetId = this.getAttribute('data-id');
                sendLike(targetId);
            });
        }
    });

    // 「いいね！」ボタンを押したときに画面をリロードせず、裏側でサーバー通信を行い、画面のいいね数を増やす処理
    function sendLike(targetId) {
        const formData = new URLSearchParams();
        formData.append('targetId', targetId);

        // UserRankingServletへPOST送信
        fetch('UserRankingServlet', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: formData.toString()
        })
        .then(response => {
            if (!response.ok) throw new Error('ネットワークエラーが発生しました');
            return response.text();
        })
        .then(data => {
            const likeCountEl = document.getElementById('like-count');
            if (likeCountEl) {
                if (data === "ok") {
                    let currentLikes = parseInt(likeCountEl.textContent, 10);
                    likeCountEl.textContent = currentLikes + 1;
                } else {
                    likeCountEl.textContent = data;
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('いいねの送信に失敗しました');
        });
    }
</script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>管理者画面</title>
</head>
<body>
    <h1>管理者画面</h1>
    <hr>
    <p>
        <a href="${pageContext.request.contextPath}/admin/accountList">
        アカウント一覧
        </a>
    </p>

    <p>
        <a href="${pageContext.request.contextPath}/admin/contactList">
        問い合わせ一覧
        </a>
    </p>

<h2>いいねランキング</h2>
    <div id="ranking-data-store" style="display: none;">
        <c:forEach var="post" items="${rankingList}">
            <span class="ranking-item" data-post="${post}"></span>
        </c:forEach>
    </div>

    <div id="ranking-container"></div>

<a href="${pageContext.request.contextPath}/LogoutServlet">ログアウト</a>

    <script>
        function renderRanking() {
            const container = document.getElementById('ranking-container');
            container.innerHTML = ''; // 初期化

            // 隠しタグからデータをすべて取得
            const elements = document.querySelectorAll('.ranking-item');

            // データがない（0件）場合の条件分岐
            if (elements.length === 0) {
                container.innerHTML = '<p>現在ランキングデータはありません。</p>';
                return;
            }

            // データがある（1件以上）場合のループ処理
            elements.forEach((el, index) => {
                const post = el.getAttribute('data-post');
                
                const rank = index + 1;

                const html = '<p>' + rank + '位：' + post + '</p>';
                container.insertAdjacentHTML('beforeend', html);
            });
        }

        // ページが読み込まれたら実行
        window.addEventListener('DOMContentLoaded', renderRanking);
    </script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>公開画面</title>
<style>
    /* ユーザー1人分の情報の枠線 */
    .user-card {
        border: 1px solid #ddd;
        margin: 10px; /* boxの外側の余白 */
        padding: 10px; /* boxの内側の余白 */
        border-radius: 8px; /* boxの丸み */
        width: 300px;
    }
    
    /* ボタンの共通スタイル */
    .btn { 
        display: inline-block;
        padding: 5px 10px; 
        cursor: pointer; /* ボタンに合わせた際、カーソルが指のマークに変わる */
        border-radius: 4px; 
        border: none;
        text-decoration: none; /* リンクの下線を消す */
        font-size: 13.333px; /* ボタンのデフォルトフォントサイズに合わせる */
        font-family: Arial;
        color: white;
    }
    
    /* いいねボタン */
    .like-btn { background-color: #ff4d4d; }
    
    /* 詳細ボタン */
    .detail-btn { background-color: #888888; }

    /* 横並びに配置 */
    .button-group { display: flex; gap: 10px; margin-top: 10px; }
</style>
</head>
<body>
    <h1>公開画面</h1>

    <div style="margin-top: 10px;">
        <a href="${pageContext.request.contextPath}/index.jsp">ログイン</a>
        <a href="${pageContext.request.contextPath}/ContactServlet">お問い合わせ</a>
    </div>

    <h2>いいねランキング</h2>
    <!-- 画面には表示しない隠しデータ置き場 -->
    <div id="user-data-store" style="display: none;">
        <c:forEach var="acc" items="${userList}">
            <span class="user-raw-data" 
                  data-id="${acc.id}"
                  data-kana="<c:out value='${acc.kana}'/>"
                  data-gender="<c:out value='${acc.gender}'/>"
                  data-age="<c:out value='${acc.age}'/>"
                  data-profile="<c:out value='${acc.profile}'/>"
                  data-likes="${acc.likes}"></span>
        </c:forEach>
    </div>

    <div id="user-list-container"></div>
        
    <script>
    // JS変数としてコンテキストパスを保持
    // 開発環境と本番環境でアプリ名や階層が変わっても、コードを書き換える必要がなくなる
    const contextPath = "${pageContext.request.contextPath}";

    // セキュリティ上入れるべきもの、ブラウザがそのままユーザーが入力したJSやHTMLを実行してしまう
    // 「g」= Global(全体)という意味で文字列に含まれる該当記号
    function escapeHtml(str) {
        if (str === null || str === undefined) return '';
        return String(str)
            .replace(/&/g, '&amp;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;')
            .replace(/"/g, '&quot;')
            .replace(/'/g, '&#39;');
    }

    function renderUserList() {
        const container = document.getElementById('user-list-container');
        // 一度中身を空っぽにする
        container.innerHTML = ''; 

        // データ要素を取得（ユーザー1人分の箱ごと順番を入れ替える必要があるため、user-raw-dataからとってくる）
        const dataElements = Array.from(document.querySelectorAll('.user-raw-data'));
        
        // いいね数（data-likes）の多い順にソート
        dataElements.sort((a, b) => {
            const likesA = parseInt(a.dataset.likes, 10) || 0;
            const likesB = parseInt(b.dataset.likes, 10) || 0;
            return likesB - likesA; // 多い順
        });

        // ソート済みの各データ要素からユーザー情報を抽出し、不要なデータをスキップする処理
        dataElements.forEach(el => {
            const id = el.dataset.id;
            const kana = el.dataset.kana;
            let gender = el.dataset.gender; // constの場合際代入できないため、letを使用
            const age = el.dataset.age;
            const profile = el.dataset.profile;
            const likes = el.dataset.likes;

            if (!id) return;

            if (gender === 'male') {
                gender = '男性';
            } else if (gender === 'female') {
                gender = '女性';
            }
            
            const cardHtml = 
                '<div class="user-card">' +
                    '<strong>ニックネーム: ' + escapeHtml(kana) + '</strong><br>' +
                    '<span>性別: ' + escapeHtml(gender) + ' / 年齢: ' + escapeHtml(age) + '歳</span><br>' +
                    '<p>自己紹介: ' + escapeHtml(profile) + '</p>' +
                    '<p>❤ 現在のいいね数: <strong id="like-count-' + id + '">' + escapeHtml(likes) + '</strong></p>' +
                    '<div class="button-group">' +
                        '<button type="button" class="btn detail-btn" onclick="location.href=\'' + contextPath + '/UserDetailServlet?id=' + id + '\'">詳細を見る</button>' +
                        '<button type="button" class="btn like-btn" data-id="' + id + '">いいね！</button>' +
                    '</div>' +
            '</div>';

            container.insertAdjacentHTML('beforeend', cardHtml);
        });

        // すべての「いいね！」ボタンに、クリック時の処理を登録する処理
        container.querySelectorAll('.like-btn').forEach(btn => {
            btn.addEventListener('click', function() {
                sendLike(this.getAttribute('data-id'));
            });
        });
    }

    // サーバーへデータを非同期通信し、画面の「いいね数」と並び順をリアルタイムに更新する処理
    function sendLike(targetId) {
        const formData = new URLSearchParams();
        formData.append('targetId', targetId);

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
            if (data.trim() === "ok") {
                // 文字列比較で属性検索（確実な要素取得のため）
                const rawDataElements = document.querySelectorAll('.user-raw-data');
                let targetEl = null;

                rawDataElements.forEach(el => {
                    if (String(el.dataset.id) === String(targetId)) {
                        targetEl = el;
                    }
                });
                
                if (targetEl) {
                    // 数値を+1してセット
                    let currentLikes = parseInt(targetEl.dataset.likes, 10) || 0;
                    targetEl.dataset.likes = currentLikes + 1;
                }
                
                // 再並び替え＆再描画
                renderUserList();

            } else {
                const likeCountEl = document.getElementById('like-count-' + targetId);
                if (likeCountEl) {
                    likeCountEl.textContent = data;
                }
            }
        })
        .catch(error => {
            console.error('Error:', error);
            alert('いいねの送信に失敗しました');
        });
    }

    window.addEventListener('DOMContentLoaded', renderUserList);
</script>
</body>
</html>
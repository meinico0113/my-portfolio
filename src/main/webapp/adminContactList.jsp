<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>問い合わせ一覧</title>
    <style>
        .error-msg { color: red; font-weight: bold; }
    </style>
</head>
<body>
    <h1>問い合わせ一覧</h1>
    <hr>

<%-- エラーメッセージ表示エリア --%>
<c:if test="${not empty error}">
    <p class="error-msg">${error}</p>
</c:if>

<table border="1" style="border-collapse: collapse; width: 100%;">
<thead>
    <tr style="background-color: #eee;">
        <th>カテゴリー</th>
        <th>本文（10文字）</th>
        <th>ステータス</th>
        <th>詳細</th>
    </tr>
</thead>

<tbody id="contact-list-container"></tbody>
</table>

<div id="contact-data-store" style="display: none;">
    <c:forEach var="contact" items="${contactList}">
        <span class="contact-item" 
              data-id="${contact.id}"
              data-category="${fn:escapeXml(contact.category)}"
              data-content="${fn:escapeXml(contact.content)}"
              data-status="${fn:escapeXml(contact.status)}"></span>
    </c:forEach>
</div>

    <p>
        <a href="${pageContext.request.contextPath}/admin/categoryList">カテゴリー一覧</a>
    </p>
    <p>
        <a href="${pageContext.request.contextPath}/AdminServlet">管理者画面に戻る</a>
    </p>

    <script>
        function renderContactList() {
            const tbody = document.getElementById('contact-list-container');
            tbody.innerHTML = ''; // 初期化

            // contextPathを取得するための工夫（aタグのベースとしてJS側で保持）
            const contextPath = '${pageContext.request.contextPath}';

            // 隠しタグからデータをすべて取得
            const elements = document.querySelectorAll('.contact-item');

            // データを1件ずつループして処理
            elements.forEach(el => {
                const id = el.getAttribute('data-id');
                const category = el.getAttribute('data-category');
                const content = el.getAttribute('data-content');
                const status = el.getAttribute('data-status');

                // 10文字で切り詰める処理
                let displayContent = content;
                if (content && content.length > 10) {
                    displayContent = content.substring(0, 10) + '...';
                }

                const rowHtml = 
                    '<tr>' +
                        '<td>' + category + '</td>' +
                        '<td>' + displayContent + '</td>' +
                        '<td>' + status + '</td>' +
                        '<td>' +
                            '<a href="' + contextPath + '/admin/contactDetail?id=' + id + '">詳細</a>' +
                        '</td>' +
                    '</tr>';

                tbody.insertAdjacentHTML('beforeend', rowHtml);
            });
        }

        // ページが読み込まれたらrenderContactList関数を実行
        window.addEventListener('DOMContentLoaded', renderContactList);
    </script>
</body>
</html>
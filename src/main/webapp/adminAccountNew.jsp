<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>アカウント追加</title>
    <style>
        .error-msg { color: red; font-weight: bold; }
    </style>
</head>
<body>

<h1>アカウント追加</h1>

<%-- エラーメッセージ表示エリア --%>
<c:if test="${not empty error}">
    <p class="error-msg">${error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/admin/accountCreate" 
      method="post" 
      enctype="multipart/form-data">

    <%-- 共通項目：ID --%>
    <input type="hidden" name="id" value="${account.id}">

    <%-- 一般、管理者切替ラジオボタン --%>
    <div style="margin-bottom: 15px;">
        種別：
        <label><input type="radio" name="role" value="user" ${account.role == 'admin' ? '' : 'checked'} onchange="switchFields()">一般</label>
        <label><input type="radio" name="role" value="admin" id="roleAdmin" ${account.role == 'admin' ? 'checked' : ''} onchange="switchFields()">管理者</label>
    </div>

    <hr>

    <%-- 共通項目グループ --%>
    <div style="margin-bottom: 15px;">
        名前
        <input type="text" name="name" value="${fn:escapeXml(account.name)}">
        <br><br>

        パスワード
        <input type="password" name="password">
        <br><br>

        メールアドレス
        <input type="text" name="email" value="${fn:escapeXml(account.email)}"
        <br><br>

        ステータス
        <label><input type="radio" name="status" value="0" ${account.status == 0 ? 'checked' : ''}>アクセス許可</label>
        <label><input type="radio" name="status" value="1" ${account.status == 1 ? 'checked' : ''}>アクセス禁止</label>
        <br><br>
    </div>

    <%-- 管理者用項目グループ（ラジオボタンで管理者が選ばれた場合は、共通項目以外に表示するものがないため空でOK） --%>
    <div id="adminFields" style="display:none;"></div>

    <%-- 一般ユーザー用項目グループ --%>
    <div id="userFields">
        ふりがな
        <input type="text" name="kana" value="${fn:escapeXml(account.kana)}">
        <br><br>

        性別
        <label><input type="radio" name="gender" value="male" ${account.gender == 'male' ? 'checked' : ''}>男性</label>
        <label><input type="radio" name="gender" value="female" ${account.gender == 'female' ? 'checked' : ''}>女性</label>
        <br><br>

        年齢
        <input type="number" name="age" value="${account.age != null && account.age != 0 ? account.age : ''}">
        <br><br>

        自己紹介
        <br>
        <textarea name="profile">${fn:escapeXml(account.profile)}</textarea>
        <br><br>

        プロフィール画像：
        <input type="file" name="image">
        <br><br>
    </div>

<button type="submit">登録</button>
<br><br>

<a href="${pageContext.request.contextPath}/admin/accountList">アカウント一覧に戻る</a>

<script>
function switchFields() {
    // ラジオボタンの選択状態を取得
    const isAdmin = document.getElementById('roleAdmin').checked;
    
    const adminFields = document.getElementById('adminFields');
    const userFields = document.getElementById('userFields');

    if (isAdmin) {
        // 管理者が選ばれたら、管理者用を表示、一般用を非表示
        adminFields.style.display = 'block';
        userFields.style.display = 'none';
    } else {
        // 一般が選ばれたら、一般用を表示、管理者用を非表示
        adminFields.style.display = 'none';
        userFields.style.display = 'block';
    }
}

// 画面が読み込まれた時に自動で動く処理
window.onload = function() {
    switchFields();
}
</script>
</body>
</html>
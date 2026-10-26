<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント編集</title>
</head>
<body>

<h2>アカウント編集</h2>

<%-- サーバーからのエラーメッセージ表示エリア --%>
<div id="errorDisplay" style="color: red; font-weight: bold; margin-bottom: 15px;">
    <c:if test="${not empty error}">${error}</c:if>
</div>

<form action="${pageContext.request.contextPath}/admin/accountUpdate" 
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
        名前：
        <input type="text" name="name" value="${account.name}">
        <br><br>

        メールアドレス：
        <input type="text" name="email" value="${account.email}">
        <br><br>
        ステータス：
        <label><input type="radio" name="status" value="0" ${account.status == 0 ? 'checked' : ''}>アクセス許可</label>
        <label><input type="radio" name="status" value="1" ${account.status == 1 ? 'checked' : ''}>アクセス禁止</label>
        <br>
    </div>

    <%-- 管理者用項目グループ（ラジオボタンで管理者が選ばれた場合は、共通項目以外に表示するものがないため空でOK） --%>
    <div id="adminFields" style="display:none;"></div>

    <%-- 一般ユーザー用項目グループ --%>
    <div id="userFields">
        ふりがな：
        <input type="text" name="kana" value="${account.kana}">
        <br><br>

        性別：
        <label><input type="radio" name="gender" value="male" ${account.gender == 'male' ? 'checked' : ''}>男性</label>
        <label><input type="radio" name="gender" value="female" ${account.gender == 'female' ? 'checked' : ''}>女性</label>
        <br><br>

        年齢：
        <input type="number" name="age" value="${account.age}">
        <br><br>

        自己紹介：
        <br>
        <textarea name="profile">${account.profile}</textarea>
        <br><br>

        プロフィール画像変更：<input type="file" name="image">
        <br>

        現在の画像<br>
        <img src="${pageContext.request.contextPath}/uploads/${not empty account.imagePath ? account.imagePath : 'default.png'}" width="100" alt="現在の画像">
        <br>
    </div>

    <button type="submit">更新保存</button>
    <br>
    <a href="${pageContext.request.contextPath}/admin/accountList">キャンセル</a>

</form>

<script>
function switchFields() {
    // ラジオボタンの選択状態を取得
    const isAdmin = document.getElementById('roleAdmin').checked;
    
    // 各入力エリアのDOMを取得
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

// 画面読み込み時に初期状態に合わせて表示を切り替える
window.onload = function() {
    switchFields();
}
</script>
</body>
</html>
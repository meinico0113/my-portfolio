<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" is ELIgnored="false" %>
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
        <input type="radio" name="role" value="user" id="roleUser" ${account.role == 'admin' ? '' : 'checked'} onchange="switchFields()">
        <label for="roleUser">一般</label>
        
        <input type="radio" name="role" value="admin" id="roleAdmin" ${account.role == 'admin' ? 'checked' : ''} onchange="switchFields()">
        <label for="roleAdmin">管理者</label>
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
        <select name="status">
            <option value="0" ${account.status == 0 ? 'selected' : ''}>アクセス許可</option>
            <option value="1" ${account.status == 1 ? 'selected' : ''}>アクセス禁止</option>
        </select>
        <br>
    </div>

    <%-- 管理者用項目グループ（ラジオボタンで管理者が選ばれた場合は、共通項目以外に表示するものがないため空でOK） --%>
    <div id="adminFields" style="display:none;"></div>

     <%-- 一般ユーザー用項目グループ --%>
    <div id="userFields">
        <%-- 
          一般ユーザー選択時でも、更新用DAOがnameやemail、statusのパラメータを
          要求する場合は、ここに現在の値をセットして送信できるようにしておく
        --%>

        ふりがな：
        <input type="text" name="kana" value="${account.kana}">
        <br><br>
        性別：
        <input type="radio" name="gender" value="male" ${account.gender == 'male' ? 'checked' : ''}>男性
        <input type="radio" name="gender" value="female" ${account.gender == 'female' ? 'checked' : ''}>女性
        <br><br>

        年齢：
        <input type="number" name="age" value="${account.age}">
        <br><br>

        自己紹介
        <textarea name="profile">${account.profile}</textarea>
        <br><br>

        <%-- 現在の画像がある場合に表示（任意） --%>
       <c:if test="${not empty account.imagePath}">
            <p>現在の画像：<br>
                <img src="${pageContext.request.contextPath}/uploads/${account.imagePath}" width="100" alt="現在の画像">
            </p>
        </c:if>
        プロフィール画像変更：<input type="file" name="image"><br>
    </div>

    <br>
    <button type="submit">更新保存</button>
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
};
</script>
</body>
</html>
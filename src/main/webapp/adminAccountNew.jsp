<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>アカウント追加</title>
</head>
<body>

<h2>アカウント追加</h2>

<%-- エラーメッセージ表示領域 --%>
<p id="errorDisplay" 
   data-error="<%= request.getAttribute("error") != null ? request.getAttribute("error") : "" %>" 
   style="color: red; font-weight: bold;"></p>

<%-- 画像などのファイルを含んだデータを、安全にサーバーへ送るための設定
acrion=送信先 method=送り方 enctype=種類（これがないと画像や写真などのファイルをサーバーに送れない）--%>
<form action="<%= request.getContextPath() %>/admin/accountCreate" method="post" enctype="multipart/form-data">

<%-- 【種別】
name =同じ名前をつけることでグループ化されるため片方を選ぶともう片方のチェックが自動的に外れる
value =Servletに送られる中身の値
checked =ついている方が最初の設定（デフォルト設定）
toggleForm =フォームを切り替える（管理者・一般で切り替わる）--%>
<p>
    <input type="radio" name="role" value="admin" onclick="toggleForm()" ${empty account.role || account.role == 'admin' ? 'checked' : ''}> 管理者  
    <input type="radio" name="role" value="user" onclick="toggleForm()" ${account.role == 'user' ? 'checked' : ''}> 一般
</p>

<%-- 【共通領域】 --%>
名前
<input type="text" name="name" value="${account.name}">
<br><br>

メールアドレス
<input type="text" name="email" value="${account.email}">
<br><br>

パスワード
<input type="password" name="password" value="${account.password}">
<br><br>

ステータス
<select name="status">
    <option value="0" ${account.status == '0' ? 'selected' : ''}>アクセス許可</option>
    <option value="1" ${account.status == '1' ? 'selected' : ''}>アクセス禁止</option>
</select>
<br><br>

<%-- ================== 管理者 ================== --%>
<div id="adminForm"></div>

<%-- ================== 一般 ================== --%>
<div id="userForm">
    プロフィール画像
    <input type="file" name="image">
    <br><br>

    ふりがな
    <input type="text" name="kana" value="${kana}">
    <br><br>

    性別
    <select name="gender">
        <option value="male" ${gender == 'male' ? 'selected' : ''}>男性</option>
        <option value="female" ${gender == 'female' ? 'selected' : ''}>女性</option>
    </select>
    <br><br>

    年齢
    <input type="number" name="age" value="${age}">
    <br><br>

    自己紹介
    <textarea name="profile">${profile}</textarea>
    <br><br>
</div>

<input type="submit" value="登録">

</form>

<br>
<a href="<%= request.getContextPath() %>/admin/accountList">アカウント一覧に戻る</a>

<script>
function toggleForm(){
    // ラジオボタンで「今どっちが選ばれているか」をチェック
    const role = document.querySelector('input[name="role"]:checked').value;

    if(role === "admin"){
        // 管理者が選ばれたら、管理者用を表示して一般用を隠す
        document.getElementById("adminForm").style.display = "block";
        document.getElementById("userForm").style.display = "none";
    } else {
         // 一般が選ばれたら、その逆にする
        document.getElementById("adminForm").style.display = "none";
        document.getElementById("userForm").style.display = "block";
    }
}

// 画面が読み込まれた時に自動で動く処理
window.onload = function() {
    // 最初に元のフォーム切り替え処理を走らせる
    toggleForm();
    // 部屋に隠しておいたエラーメッセージをJavaScriptで安全に取得
    const errorElement = document.getElementById("errorDisplay");
    const serverError = errorElement.getAttribute("data-error");
    
    // もしエラーがあれば、画面上の文字として表示する
    if (serverError && serverError !== "") {
        errorElement.innerText = serverError;
    }
};
</script>

</body>
</html>
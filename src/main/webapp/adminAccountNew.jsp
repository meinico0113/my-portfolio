<%@ page contentType="text/html; charset=UTF-8" %>

<h2>アカウント追加</h2>

<%-- 画像などのファイルを含んだデータを、安全にサーバーへ送るための設定
acrion=送信先 method=送り方 enctype=種類（これがないと画像や写真などのファイルをサーバーに送れない）--%>
<form action="<%= request.getContextPath() %>/admin/accountCreate" method="post" enctype="multipart/form-data">

<%-- 【種別】
name =同じ名前をつけることでグループ化されるため片方を選ぶともう片方のチェックが自動的に外れる
value =Servletに送られる中身の値
checked =ついている方が最初の設定（デフォルト設定）
toggleForm =フォームを切り替える（管理者・一般で切り替わる）--%>
<p>
<input type="radio" name="role" value="admin" onclick="toggleForm()"> 管理者  
<input type="radio" name="role" value="user" checked onclick="toggleForm()"> 一般
</p>

<%-- 【共通】 --%>
名前
<input type="text" name="name">
<br><br>

メール
<input type="text" name="email">
<br><br>

ステータス
<select name="status">
    <option value="0">アクセス許可</option>
    <option value="1">アクセス禁止</option>
</select>
<br><br>


<%-- ================== 管理者 ================= --%>
<div id="adminForm" style="display:none;">
    <p>管理者アカウント</p>
    ※追加項目なし
</div>

<%-- ================== 一般 ================== --%>
<div id="userForm">

    <p>一般ユーザーアカウント</p>

    プロフィール画像
    <input type="file" name="image">
    <br><br>

    ふりがな
    <input type="text" name="kana">
    <br><br>

    性別
    <select name="gender">
        <option value="male">男性</option>
        <option value="female">女性</option>
    </select>
    <br><br>

    年齢
    <input type="number" name="age">
    <br><br>

    自己紹介
    <textarea name="profile"></textarea>
    <br><br>

</div>

<input type="submit" value="登録">

</form>

<script>
function toggleForm(){
    // ラジオボタンで「今どっちが選ばれているか」をチェック
    const role = document.querySelector('input[name="role"]:checked').value;

    if(role === "admin"){
        // 管理者が選ばれたら、管理者用を表示して一般用を隠す
        document.getElementById("adminForm").style.display = "block";
        document.getElementById("userForm").style.display = "none";
    }else{
        // 一般が選ばれたら、その逆にする
        document.getElementById("adminForm").style.display = "none";
        document.getElementById("userForm").style.display = "block";
    }
}
</script>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Account" %>
<%
    Account account = (Account)request.getAttribute("account");
    if (account == null) {
        account = new Account(); // null安全対策
    }

    // ロールの判定
    String role = (account.getRole() != null) ? account.getRole() : "user";
    
    // 一般ユーザー項目の null 対策（画面に "null" と表示されないようにする）
    String name = (account.getName() != null) ? account.getName() : "";
    String email = (account.getEmail() != null) ? account.getEmail() : "";
    String kana = (account.getKana() != null) ? account.getKana() : "";
    String gender = (account.getGender() != null) ? account.getGender() : "";
    int age = account.getAge(); // int型
    String profile = (account.getProfile() != null) ? account.getProfile() : "";
    String imagePath = account.getImagePath();

    // エラーメッセージの取得
    String errorMessage = (String) request.getAttribute("error");
%>

<h2>アカウント編集</h2>

<%-- サーバーからのエラーメッセージ表示エリア --%>
<div id="errorDisplay" style="color: red; font-weight: bold; margin-bottom: 15px;">
    <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <%= errorMessage %>
    <% } %>
</div>

<form action="<%= request.getContextPath() %>/admin/accountUpdate" 
      method="post" 
      enctype="multipart/form-data">

    <%-- 共通項目：IDとロール --%>
    <input type="hidden" name="id" value="<%= account.getId() %>">

    <%-- 一般、管理者切替ラジオボタン --%>
    <div style="margin-bottom: 15px;">
        種別：
        <input type="radio" name="role" value="user" id="roleUser" <%= "user".equals(role) ? "checked" : "" %> onchange="switchFields()">
        <label for="roleUser">一般</label>
        
        <input type="radio" name="role" value="admin" id="roleAdmin" <%= "admin".equals(role) ? "checked" : "" %> onchange="switchFields()">
        <label for="roleAdmin">管理者</label>
    </div>

    <hr region="separator">

    <%-- 一般ユーザー用項目・管理者用項目グループ共通 --%>
    <div style="margin-bottom: 15px;">
        名前：
        <input type="text" name="name" value="${account.name}">
        <br><br>

        メールアドレス：
        <input type="text" name="email" value="${account.email}">
        <br><br>
        ステータス：
        <select name="status">
            <option value="0" <%= account.getStatus() == 0 ? "selected" : "" %>>アクセス許可</option>
            <option value="1" <%= account.getStatus() == 1 ? "selected" : "" %>>アクセス禁止</option>
        </select><br>
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
        <input type="text" name="kana" value="<%= kana %>">
        <br><br>
        性別：
        <input type="radio" name="gender" value="male" <%= "male".equals(gender) ? "checked" : "" %>>男性
        <input type="radio" name="gender" value="female" <%= "female".equals(gender) ? "checked" : "" %>>女性
        <br>

        年齢：
        <input type="number" name="age" value="<%= age %>">
        <br><br>

        自己紹介
        <textarea name="profile"><%= profile %></textarea>
        <br><br>

        <%-- 現在の画像がある場合に表示（任意） --%>
       <% if (imagePath != null && !imagePath.isEmpty()) { %>
            <p>現在の画像：<br>
                <img src="<%= request.getContextPath() %>/uploads/<%= imagePath %>" width="100" alt="現在の画像">
            </p>
        <% } %>
        プロフィール画像変更：<input type="file" name="image"><br>
    </div>

    <br>
    <button type="submit">更新保存</button>
    <a href="<%= request.getContextPath() %>/admin/accountList">キャンセル</a>

</form>

<%-- リアルタイム切り替え用JavaScript --%>
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
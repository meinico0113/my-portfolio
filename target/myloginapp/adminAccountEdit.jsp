<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Account" %>
<%
    Account account = (Account)request.getAttribute("account");
    // ロールの判定
    String role = (account != null && account.getRole() != null) ? account.getRole() : "user";
    
    // 一般ユーザー項目の null 対策（画面に "null" と表示されないようにする）
    String kana = (account.getKana() != null) ? account.getKana() : "";
    String gender = (account.getGender() != null) ? account.getGender() : "";
    String profile = (account.getProfile() != null) ? account.getProfile() : "";
%>

<h2>アカウント編集（<%= role.equals("admin") ? "管理者" : "一般ユーザー" %>）</h2>

<form action="<%= request.getContextPath() %>/admin/accountUpdate" 
      method="post" 
      enctype="multipart/form-data">

    <%-- 共通項目：IDとロール --%>
    <input type="hidden" name="id" value="<%= account.getId() %>">
    <input type="hidden" name="role" value="<%= role %>">

    名前：<input type="text" name="name" value="<%= account.getName() %>" required><br>
    メール：<input type="email" name="email" value="<%= account.getEmail() %>" required><br>
    
    ステータス：
    <select name="status">
        <option value="1" <%= account.getStatus() == 1 ? "selected" : "" %>>アクセス許可</option>
        <option value="0" <%= account.getStatus() == 0 ? "selected" : "" %>>アクセス禁止</option>
    </select><br>

    <%-- 一般ユーザーの場合のみ表示する項目 --%>
    <% if ("user".equals(role)) { %>
        <hr>
        <%-- value属性に既存の値をセット --%>
        ふりがな：<input type="text" name="kana" value="<%= kana %>"><br>
        
        性別：
        <input type="radio" name="gender" value="男性" <%= "男性".equals(gender) ? "checked" : "" %>>男性
        <input type="radio" name="gender" value="女性" <%= "女性".equals(gender) ? "checked" : "" %>>女性
        <input type="radio" name="gender" value="その他" <%= "その他".equals(gender) ? "checked" : "" %>>その他<br>

        年齢：<input type="number" name="age" min="0" max="150" value="<%= account.getAge() %>"><br>

        自己紹介：<br>
        <%-- textareaはタグの間に値を挟む --%>
        <textarea name="profile" rows="4" cols="40"><%= profile %></textarea><br>

        <%-- 現在の画像がある場合に表示（任意） --%>
        <% if(account.getImagePath() != null) { %>
            <p>現在の画像：<br><img src="<%= account.getImagePath() %>" width="100"></p>
        <% } %>
        プロフィール画像変更：<input type="file" name="image"><br>
    <% } %>

    <br>
    <button type="submit">更新保存</button>
    <a href="<%= request.getContextPath() %>/admin/accountList">キャンセル</a>

</form>
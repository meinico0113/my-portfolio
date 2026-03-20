<%@ page contentType="text/html; charset=UTF-8" %>

<h2>アカウント追加</h2>

<form action="<%= request.getContextPath() %>/admin/accountCreate" method="post">

名前
<input type="text" name="name">

<br><br>

メール
<input type="text" name="email">

<br><br>

パスワード
<input type="password" name="password">

<br><br>

<input type="submit" value="登録">

</form>
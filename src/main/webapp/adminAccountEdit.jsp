<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Account" %>

<%-- サーブレットから渡された「account」オブジェクトを取得する --%>
<% Account account = (Account)request.getAttribute("account"); %>

<h2>アカウント編集</h2>

<%-- 
  action: 送信先のサーブレットを指定（admin/accountUpdate）
  method: データの更新なので「post」を使用 
--%>
<form action="<%= request.getContextPath() %>/admin/accountUpdate" method="post">

<%-- 
  ユーザーには見えない「hidden」属性でIDを送信
  これがないと、DBの「誰のデータ」を更新すればいいか判別できなくなる 
--%>
<input type="hidden" name="id" value="<%= account.getId() %>">

<%-- 現在の登録内容をvalue属性に入れて初期表示させる --%>
名前：<input type="text" name="name" value="<%= account.getName() %>"><br>
メール：<input type="text" name="email" value="<%= account.getEmail() %>"><br>

<button type="submit">更新</button>

</form>
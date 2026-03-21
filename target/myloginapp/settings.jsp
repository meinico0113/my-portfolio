<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>設定</title>
</head>
<body>
    <h2>設定変更</h2>
    
    <%-- ここにエラーメッセージを表示する（バリデーション用） --%>
    <% if(request.getAttribute("message") != null) { %>
        <p style="color: red;"><%= request.getAttribute("message") %></p>
    <% } %>

    <form action="SettingsServlet" method="post">
        名前：<br>
        <input type="text" name="userName" value="${fn:escapeXml(currentName)}"><br><br>

        メールアドレス：<br>
        <input type="text" name="email"><br><br>

        新しいパスワード：<br>
        <input type="password" name="password"><br>
        <small>8~32文字の半角英数字と_-のみ</small><br><br>

        <input type="submit" value="変更を保存する">

    </form>
    
    <br>
    <a href="user.jsp">戻る</a>
</body>
</html>
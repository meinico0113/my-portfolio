<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ログイン</title>
</head>
<body>
    <h2>ログイン画面</h2>
    <p style="color: red;">${errorMessage}</p>

    <form action="LoginServlet" method="post">
        ユーザー名：<input type="text" name="username"><br>
        パスワード：<input type="password" name="password"><br>
        <button type="submit">ログイン</button>
    </form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>一般ユーザー画面</title>
</head>
<body>
    <h1>こんにちは、一般ユーザーさん！</h1>
    <p>ここは一般ユーザーだけが見られるページです。</p>

    <div style="margin: 20px 0; padding: 10px; border: 1px solid #ccc; display: inline-block;">
        <a href="settings.jsp" style="text-decoration: none; color: blue; font-weight: bold;">
            ⚙️ 設定（メアド・パスワード変更）へ
        </a>
    </div>

    <br>
    <a href="index.jsp">ログアウト</a>
</body>
</html>
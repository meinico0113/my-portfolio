<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>myportfolio</title>
    <style>
        /* 見た目をきれいにする */
        body { font-family: sans-serif; text-align: center; margin-top: 50px; }
        form { display: inline-block; text-align: left; border: 1px solid #ccc; padding: 20px; border-radius: 8px; }
        .error { color: red; }
    </style>
</head>
<body>
    <h2>ログイン画面</h2>

    <%-- エラーメッセージの表示 --%>
    <% 
        String error = request.getParameter("error");
        if("1".equals(error)) { 
    %>
        <p style="color: red;">ユーザー名かパスワードが違います！</p>
    <% 
        } else if("2".equals(error)) { 
    %>
        <p style="color: red;">ユーザー名が長すぎます（254文字以内で入力してください）</p>
    <% 
        } 
    %>

    <form action="LoginServlet" method="post">
        ユーザー名：<br>
        <input type="text" name="username" required><br><br>
        
        パスワード：<br>
        <input type="password" name="password" required><br><br>
        
        <input type="submit" value="ログイン">
    </form>
</body>
</html>
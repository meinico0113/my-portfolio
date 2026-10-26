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

    <div id="error-message-container"></div>

    <form action="LoginServlet" method="post">
        ユーザー名：<br>
        <input type="text" name="username" required><br><br>
        
        パスワード：<br>
        <input type="password" name="password" required><br><br>
        
        <input type="submit" value="ログイン">
    </form>

    <script>
        function renderLoginView() {
            const errorContainer = document.getElementById('error-message-container');
            errorContainer.innerHTML = ''; // 初期化

            // URLの「?error=1」などのパラメータをJavaScriptで直接取得する
            const urlParams = new URLSearchParams(window.location.search);
            const errorCode = urlParams.get('error');

            // エラーコードに応じた条件分岐
            let errorMessageHtml = '';
            if (errorCode === '1') {
                errorMessageHtml = '<p style="color: red;">ユーザー名かパスワードが違います。</p>';
            } else if (errorCode === '2') {
                errorMessageHtml = '<p style="color: red;">ユーザー名は255文字以内で入力してください。</p>';
            }　else if (errorCode === '3') {
            errorMessageHtml = '<p style="color: red;">パスワードは8〜32文字の半角英数字、ハイフン(-)、アンダースコア(_)で入力してください。</p>';
        }

            // 組み立てたエラーメッセージを画面に流し込む
            if (errorMessageHtml !== '') {
                errorContainer.innerHTML = errorMessageHtml;
            }
        }

        // ページが読み込まれたら実行
        window.addEventListener('DOMContentLoaded', renderLoginView);
    </script>
</body>
</html>
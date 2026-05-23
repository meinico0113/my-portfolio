<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>お問い合わせ</title>
<style>
    .contact-form { max-width: 400px; margin: 20px auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; }
    .field { margin-bottom: 15px; }
    label { display: block; margin-bottom: 5px; font-weight: bold; }
    select, textarea { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
    .send-btn { background-color: #007bff; color: white; border: none; padding: 10px 20px; cursor: pointer; border-radius: 4px; width: 100%; }
</style>
</head>
<body>
    <div class="contact-form">
        <h1>お問い合わせ</h1>
        <form action="ContactServlet" method="post">
            <div class="field">
                <label>カテゴリー</label>
                <select name="category">
                    <option value="ログインについて">ログインについて</option>
                    <option value="機能への要望">機能への要望</option>
                    <option value="不具合報告">不具合報告</option>
                    <option value="その他">その他</option>
                </select>
            </div>
            <div class="field">
                <label>内容</label>
                <textarea name="content" rows="5" required placeholder="お問い合わせ内容を入力してください"></textarea>
            </div>
            <button type="submit" class="send-btn">送信する</button>
        </form>
        <p style="text-align: center;"><a href="UserListServlet">戻る</a></p>
    </div>
</body>
</html>
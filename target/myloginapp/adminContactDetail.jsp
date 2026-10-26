<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>お問い合わせ詳細</title>
</head>
<body>

<h2>お問い合わせ詳細</h2>
<hr>

<p><strong>カテゴリー：</strong> ${fn:escapeXml(contact.category)}</p>

<p><strong>本文：</strong><br>
    <div style="white-space: pre-wrap;"><c:out value="${contact.content}" /></div>
</p>

<form action="${pageContext.request.contextPath}/admin/updateStatus" method="post">
    <input type="hidden" name="id" value="${contact.id}">

    <p><strong>ステータス：</strong>
        <select name="status">
            <option value="未対応" ${contact.status == '未対応' ? 'selected' : ''}>未対応</option>
            <option value="対応中" ${contact.status == '対応中' ? 'selected' : ''}>対応中</option>
            <option value="対応済み" ${contact.status == '対応済み' ? 'selected' : ''}>対応済み</option>
        </select>
    </p>

    <input type="submit" value="更新">
</form>

<p><strong>作成日時：</strong> ${fn:escapeXml(contact.createdAt)}</p>

<p>
    <a href="${pageContext.request.contextPath}/admin/contactList">
        一覧へ戻る
    </a>
</p>

</body>
</html>
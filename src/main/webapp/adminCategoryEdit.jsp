<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="model.Category" %>

<%
Category category = (Category) request.getAttribute("category");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カテゴリ編集</title>
</head>
<body>

<h2>カテゴリ編集</h2>

<form action="<%= request.getContextPath() %>/admin/categoryUpdate" method="post">

<input type="hidden" name="id" value="<%= category.getId() %>">

<input type="text" name="name" value="<%= category.getName() %>" maxlength="255">

<input type="submit" value="更新">

</form>
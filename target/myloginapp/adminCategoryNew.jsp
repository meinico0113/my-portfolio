<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>カテゴリ追加</title>
</head>
<body>

<h2>カテゴリ追加</h2>

<form action="<%= request.getContextPath() %>/admin/categoryCreate" method="post">

カテゴリ名  
<input type="text" name="name" maxlength="255">

<input type="submit" value="登録">

</form>

</body>
</html>
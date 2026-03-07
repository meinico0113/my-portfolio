<h2>カテゴリ追加</h2>

<form action="<%= request.getContextPath() %>/admin/categoryCreate" method="post">

カテゴリ名  
<input type="text" name="name" maxlength="255">

<input type="submit" value="登録">

</form>
<h2>カテゴリ編集</h2>

<form action="<%= request.getContextPath() %>/admin/categoryUpdate" method="post">

<input type="hidden" name="id" value="<%= category.getId() %>">

<input type="text" name="name" value="<%= category.getName() %>" maxlength="255">

<input type="submit" value="更新">

</form>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>
	<h2>科目情報登録</h2>

	<form action = "SubjectCreateExecute.action" method="post">


		<label>科目ID</label>
		<input type="text" name="cd"
			placeholder="科目IDを入力してください" maxlength="10"
			value="${subject.getCd()}" required />
		<div>${errors.get("cd")}</div>

		<label>科目名</label>
		<input type="text"
			name="name" placeholder="科目を入力してください" maxlength="10"
			value="${subject.getName()}" required />
		<div>${errors.get("name")}</div>



		<input type="submit" value="登録して終了">
	</form>

	<a href="SubjectList.action">戻る</a>

</body>
</html>
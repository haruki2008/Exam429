 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="../css/style.css" rel="stylesheet" type="text/css" media="all">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>
<body>
<%@ include file="../../common/header.jsp" %>
<%@ include file="../../common/navigation.jsp" %>
<div class = "menu">
	<div class = "strmenu">
		<h2>メニュー</h2>
	</div>
	<div class = "menucon">
		<div class = "studentlist">
			<p><a href="StudentList.action">学生管理</a></p>
		</div>
		<div class = "testregist">
			<p>成績管理</p>
			<p><a href="TestRegist.action">成績登録</a></p>
			<p><a href="TestList.action">成績参照</a></p>
		</div>
		<div class = "subject">
			<a href="#">科目管理</a>
		</div>
	</div>
</div>
<div class = "footer">
	<%@ include file="../../common/footer.jsp" %>
</div>
</body>
</html>
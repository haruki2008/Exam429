<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="css/style.css" rel="stylesheet" type="text/css" media="all">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生管理システム</title>
</head>
<body>
<%@ include file="../common/header.jsp" %>
<div class = logout>
	<h1>ログアウト</h1>
	<p>ログアウトしました</p>
	<a href="Login.action">ログイン</a>
</div>

<%@ include file="../common/footer.jsp" %>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

<h2>成績管理</h2>

		<form method="get">
		<label>入学年度</label>
		<select name="f1">
			<option value="0">--------</option>
			<c:forEach var="year" items="${ent_year_set}">
				<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
				<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
			</c:forEach>
		</select>

		<label>クラス</label>
		<select name="f2">
			<option value="0">--------</option>
			<c:forEach var="num" items="${class_num_set}">
				<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
				<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
			</c:forEach>
		</select>

		<label>科目</label>
		<select name="f3">
			<option value="0">--------</option>
			<c:forEach var="subject" items="${subject_set}">
				<%-- 現在のnumと選択されていたf3が一致していた場合selectedを追記 --%>
				<option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
			</c:forEach>
		</select>

		<label>回数</label>
		<select name="f4">
			<option value="0">--------</option>
			<c:forEach var="num" items="${no_set}">
				<%-- 現在のnumと選択されていたf4が一致していた場合selectedを追記 --%>
				<option value="${num}" <c:if test="${num==f4}">selected</c:if>>${num}</option>
			</c:forEach>
		</select>

		<button>検索</button>

		<div>${errors.get("f1")}</div>
	</form>

	<c:choose>
		<c:when test="${tests.size()>0}">
			<h2>科目：${subject.getName()}（${num}回）</h2>

			<table class="table table-hover">
				<tr>
					<th>入学年度</th>
					<th>クラス</th>
					<th>学生番号</th>
					<th>氏名</th>
					<th>点数</th>
				</tr>
				<c:forEach var="test" items="${tests}">
					<tr>
						<td>${test.getStudent().getEntYear()}</td>
						<td>${test.class_num}</td>
						<td>${test.student_no}</td>
						<td>${test.getStudent().getName()}</td>
						<tb><input name= "point_${test.student_no}" type="text">${test.point}</input>
						</tb>
					</tr>
				</c:forEach>
			</table>
			<input type="button">登録して終了</input>
		</c:when>
		<c:otherwise>
			<c:if test="${tests == null}">検索してください</c:if>
			<c:if test="${tests.size() == 0}"><div>成績情報が存在しませんでした</div></c:if>
		</c:otherwise>
	</c:choose>

</body>
</html>
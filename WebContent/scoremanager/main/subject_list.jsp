<%-- 科目一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>得点管理システム</title>
</head>

<link rel="stylesheet" href="./css/style.css">
<body>



<div class="sidebar">
<c:import url = "./common/navigation.jsp" />
</div>

<div class="sub_r">

	<h2 class="subject">科目管理</h2>

	<div class="sub_chart">
	<a href="SubjectCreate.action" class="new_sub">新規登録</a>

	<form method="get">
		<label>科目ID </label>
		<select name="f1">
			<option value="0">--------</option>
			<c:forEach var="subjectCd" items="${set_sub_list}">
				<%-- 現在のcdと選択されていたf1が一致していた場合selectedを追記 --%>
				<option value="${subjectCd}" <c:if test="${subjectCd==f1}">selected</c:if>>${subjectCd}</option>
			</c:forEach>
		</select>


		<button>絞込み</button>

		<div>${errors.get("f1")}</div>
	</form>

	<c:choose>
		<c:when test="${subjects.size()>0}">
			<div>検索結果：${subjects.size()}件</div>

			<table class="table table-hover">
				<tr>
					<th>科目ID</th>
					<th>科目名</th>
					<th></th>
					<th></th>
				</tr>

				<c:forEach var="subject" items="${subjects}">
					<tr>

						<td>${subject.subjectCd}</td>

						<td>${subject.name}</td>

						<td><a href="SubjectUpdate.action?cd=${subject.subjectCd}">変更</a></td>

						<td><a href="SubjectDelete.action?cd=${subject.subjectCd}">削除</a></td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<div>科目情報が存在しませんでした</div>
		</c:otherwise>
	</c:choose>
	<a href="menu.jsp">戻る</a>
	</div>

</div>

</body>
</html>
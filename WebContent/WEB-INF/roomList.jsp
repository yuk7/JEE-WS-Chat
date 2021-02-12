<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>roomList.jsp</title>
</head>
<body>
<h2>チャットルーム一覧</h2>
<ul>
<c:forEach var="r" items="${ roomList.map }">
<li><a href="room/${r.getKey()}">${r.getValue()}</a></li>
</c:forEach>
</ul>
</body>
</html>
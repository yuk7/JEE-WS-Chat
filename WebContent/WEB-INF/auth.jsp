<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>auth.jsp</title>
</head>
<body>
<h2>ログインしてください</h2>
<form action="auth" method="POST">
<p><input type="text" name="userID" placeholder="ID" /></p>
<p><input type="text" name="password" placeholder="password" /></p>
<input type="submit" value="submit" />
</form>
</body>
</html>
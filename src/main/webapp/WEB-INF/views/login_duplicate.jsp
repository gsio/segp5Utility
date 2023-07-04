<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page session="true" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<html>

<head>

	<title>Home</title>

	

</head>

 

<body>

<h1>
	로그인 중복 발생
</h1>
다른 위치에서 이 아이디로 접속하여 기존의 접속을 종료합니다.
<br>(ID당 가능한 최대  Session 수 : 3)
<br>
<a href="<c:url value="/login" />" > 로그인 페이지로</a>
 

</body>

</html>


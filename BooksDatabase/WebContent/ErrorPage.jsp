<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isErrorPage="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page</title>

<style>
h1 {
	margin: 20% 0 0 30%;
}
</style>
</head>
<body>
	<jsp:include page="Index.jsp"></jsp:include>
	<h1 style="color: Red;">
		<c:out value="${requestScope.error}">Something went wrong!Please try later!</c:out>
	</h1>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html >
<html>
<head>
<style>
body {
	text-align: center;
}
</style>
<link rel="stylesheet"
	href="<c:url value="resources/myStyle.css" />">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERROR PAGE</title>
</head>
<body background="<c:url value="resources/images/index11.jpg" />">
<a href="index.obj">
		<img src="resources/images/home.png" width="50" height="50" class="home"></a><center><h1 style="color: white;background-color:black" class="ems" >Employee Maintenance System</h1></center>

<br><br>
<font color="red" size="30%">
${msg}
</font>
<br><br>
</body>
</html>
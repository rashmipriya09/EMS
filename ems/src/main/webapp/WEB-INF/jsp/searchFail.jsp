<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isErrorPage="true"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ERROR PAGE</title>
</head>
<body>
<a href="userPage.obj">
		<img src="resources/images/home.png" width="50" height="50" class="home"></a><a href="index.obj"><img class="logout" src="resources/images/lgout.png" align="top" height="40" width="150"> </a><center><h1 style="color: white;background-color:black" class="ems" >
		Employee Maintenance System</h1></center>
		<br>
<h1 style="color:#0066CC">Employee Maintenance System</h1><br><br>

<font color="red" size="30%">
${msg}
</font>
<br><br>
<a href = "search.obj">HOME</a>
</body>
</html>
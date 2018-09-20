<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Employee Maintenance System</title>
<link rel="stylesheet"
	href="<c:url value="resources/myStyle.css" />">
</head>
<body background="<c:url value="resources/images/index11.jpg" />">
<a href="userPage.obj">
		<img src="resources/images/home.png" width="50" height="50" class="home"></a><a href="index.obj"><img class="logout" src="resources/images/lgout.png" align="top" height="40" width="150"> </a><center><h1 style="color: white;background-color:black" class="ems" >
		Employee Maintenance System</h1></center>
		<br>
	
	<center>
		<h1  style="color: white;background-color:black" class="ems">SEARCH EMPLOYEE</h1>
	</center>
	<form action="searchEmployee.obj" method="post" >
			<table align="center">
			<tr>
				<td style="color:white"><h2><b>CHOOSE OPTIONS :</b></h2></td>
				<td><select name="search">
						<option value="employeeId">Employee Id</option>
						<option value="firstName">FirstName</option>
						<option value="lastName">LastName</option>
						<option value="grade">Grade</option>
						<option value="maritalStatus">MaritalStatus</option>
				</select></td>
				
			</tr>
					
		</table>
		<br>
<input type="submit" value="search" /><br><br>
	</form>
	<center>
	<c:if test="${msg ne null}">
		<center><h3 style ="color:red ;background-color:white"  class="ems" >${msg}</h3></center>
	</c:if>
	<c:if test="${isFirst==true }">
	<s:form action="searchEmp.obj" modelAttribute="employee" method="post">
		<table class="trans" align="center">
		<c:if test="${name==id}">
		<tr><td><s:label path="employeeId">Enter employeeId</s:label></td>
			
			<td><s:input path="employeeId"  /></td>
			<td style="color:red"><s:errors path="employeeId"></s:errors></td>
			</tr>
		</c:if>
		
		<c:if test="${name==fname}">
		<tr><td>	<s:label path="firstName">Enter first name</s:label></td>
			
			<td><s:input path="firstName"  /></td></tr>
			<td style="color:red"><s:errors path="firstName"></s:errors></td>
		</c:if>

		<c:if test="${name==lname}">
		<tr><td>	<s:label path="lastName">Enter last name</s:label></td>
			
			<td><s:input path="lastName" /></td></tr>
			<td style="color:red"><s:errors path="lastName"></s:errors></td>
		</c:if>

		<c:if test="${name==grade}">
			<tr><td><s:label path="grade">Enter grade</s:label></td>
			
			<td><s:input path="grade" /></td></tr>
			<td style="color:red"><s:errors path="grade"></s:errors></td>
		</c:if>

		<c:if test="${name==maritalStatus}">
		<tr>	<td><s:label path="maritalStatus">Enter Marital Status</s:label></td>
			
			<td><s:input path="maritalStatus" /></td></tr>
			<td style="color:red"> <s:errors path="maritalStatus"></s:errors></td>
		</c:if>
		
		<tr><td></td><td><input type="submit" value="search"/></td></tr>
		</table>
	</s:form>
	
</c:if>
</center>
</body>
</html>




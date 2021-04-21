<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device=width, initial-scale=1.0">
<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">

<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
	crossorigin="anonymous">

<title>AuditManagement WebPortal</title>
</head>
<body>
<div class="container h2 pt-5">
	Audit Questions
</div>
<div class="container pt-5">
	<form:form action="processForm" modelAttribute="details">
	
		
		<form:input style="display: none" path="type" value="${type}"/>
		
		Project name: <form:input class="form-control" path="name" />

		<br>
		<br>
	
		Manager: <form:input class="form-control" path="manager" />

		<br>
		<br>
		
		Owner: <form:input class="form-control" path="owner" />

		<br>
		<br>
		
		Date: <form:input class="form-control" path="date" />

		<br>
		<br>
	
				
		<br>
		<br>

		Questions:
		<br><br>
		
		
		<c:forEach items="${ques2}" var="ques">
			${ques.getQuestion()}
			Yes<form:radiobutton path="q${ques.getId()}" value="yes" />
			No<form:radiobutton path="q${ques.getId()}" value="no" /><br><br>
		</c:forEach>
		
		<div>--------------------------------------</div>
		
		<!-- {questions.get(0)}
		Yes<form:radiobutton path="q1" value="yes" />
		No<form:radiobutton path="q1" value="no" />
		<br><br>
		${questions.get(1)}
		Yes<form:radiobutton path="q2" value="yes" />
		No<form:radiobutton path="q2" value="no" />
		<br><br>
		${questions.get(2)}
		Yes<form:radiobutton path="q3" value="yes" />
		No<form:radiobutton path="q3" value="no" />
		<br><br>
		${questions.get(3)}
		Yes<form:radiobutton path="q4" value="yes" />
		No<form:radiobutton path="q4" value="no" />
		<br><br>
		${questions.get(4)}
		Yes<form:radiobutton path="q5" value="yes" />
		No<form:radiobutton path="q5" value="no" /> -->

		<br>
		<br>

		<input type="submit" value="Submit" />

	</form:form>
</div>
</body>
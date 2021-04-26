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

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
			<div class="container pl-5">
				<div class="container d-flex flex-row">
					<a class="navbar-brand" href="#">AuditManagement</a>
				</div>
				<div class="container d-flex flex-row-reverse">
					<ul class="navbar-nav mr-auto">
						<li class="nav-item">
							<a class="nav-link" href="#">Home</a>
						</li>
					</ul>
				</div>
			</div>
		</nav>

	<div class="container mt-5 w-50">
		<div class="h2">
			Login
		</div>
		<div class="alert-danger">
			${invalid}
		</div>
		
		<form:form action="/loginValidate" modelAttribute="user">
			Username
			<form:input class="form-control" path="userName" />
			Password
			<form:input type="password" class="form-control" path="password" />
			
			<input class="btn btn-success mt-2" type="submit" value="Submit" />
		</form:form>
	</div>
</body>
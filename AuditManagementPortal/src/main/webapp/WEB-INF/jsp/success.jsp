<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
		<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
			<!DOCTYPE html>
			<html>

			<head>
				<meta charset="utf-8">
				<meta name="viewport" content="width=device=width, initial-scale=1.0">
				<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
				<link rel="stylesheet"
					href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">

				<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
					rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
					crossorigin="anonymous">

				<title>AuditManagement WebPortal</title>
			</head>

			<head>
				<meta charset="utf-8">
				<meta name="viewport" content="width=device=width, initial-scale=1.0">
				<script src="https://code.jquery.com/jquery-1.12.3.min.js"></script>
				<link rel="stylesheet"
					href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">

				<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
					rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
					crossorigin="anonymous">

				<title>AuditManagement WebPortal</title>
			</head>

			<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
				<div class="container pl-5">
					<div class="container d-flex flex-row">
						<a class="navbar-brand" href="#">AuditManagement</a>
					</div>
					<div class="container d-flex flex-row-reverse">
						<ul class="navbar-nav mr-auto">
							<li class="nav-item">
								<a class="nav-link" href="#">${sessionScope.user.getUserName()}</a>
							</li>
							<li class="nav-item">
								<a class="nav-link" href="logout">logout</a>
							</li>
						</ul>
					</div>
				</div>
			</nav>



			<body style="height: 100vh">
				<div class="container h-100">
					<div class="row h-50 align-items-center">
						<div class="col h-100 d-flex justify-content-center">
							<c:if test="${status.getStatus() == 'green'}">
								<div class="container w-50 h-50 alert-success h2 d-flex justify-content-center align-self-center">
									<p class="align-self-center">${status.getStatus()}</p>
								</div>
							</c:if>
							<c:if test="${status.getStatus() == 'red'}">
								<div class="container w-50 h-50 alert-danger h2 d-flex justify-content-center align-self-center">
									<p class="align-self-center">${status.getStatus()}</p>
								</div>
							</c:if>
						</div>
						
					</div>
					<!--  -->
					<div class="row h-25">
					
						<c:if test="${status.getStatus() == 'green'}">
							<div class="col h-100 d-flex justify-content-around">
								<p class="h3">Remedial duration :</p>
								<p class="text-success">${status.getRem_duration()}</p>
							</div>
						</c:if>
						<c:if test="${status.getStatus() == 'red'}">
							<div class="col h-100 d-flex justify-content-around">
								<p class="h3">Remedial duration :</p>
								<p class="text-danger h1">${status.getRem_duration()}</p>
							</div>
						</c:if>
					</div>
					<div class="row h-33 d-flex align-items-center justify-content-center">
						<div class="col d-flex h-100 justify-content-around">
							<form:form class="w-70" action="/redirect" method="post">
								<input class="btn flex-fill btn-success btn-lg btn-block mb-5" type="submit" value="Home" />
							</form:form>
						</div>
					</div>
				</div>
			</body>

			</html>
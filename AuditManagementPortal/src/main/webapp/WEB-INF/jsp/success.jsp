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

			<body style="height: 100vh">
				<div class="container">
					<div class="row h-33 align-items-center justify-content-center">
						<c:if test="${status.getStatus() == 'green'}">
							<div class="col alert-success">${status.getStatus()}</div>
						</c:if>
						<c:if test="${status.getStatus() == 'red'}">
							<div class="container alert-danger">${status.getStatus()}</div>
						</c:if>
					</div>
					<!--  -->
					<div class="row h-33 align-items-center justify-content-center">
						<div class="col">${status.getRem_duration()}</div>
					</div>
					<div class="row h-33 align-items-center justify-content-center">
						<div class="col">${status.getDetail().getType() }</div>
					</div>
					<div class="row h-33 align-items-center justify-content-center">
						<form:form action="/redirect" method="post">
							<input class="btn btn-info mb-5" type="submit" value="Home" />
						</form:form>
					</div>
				</div>
			</body>

			</html>
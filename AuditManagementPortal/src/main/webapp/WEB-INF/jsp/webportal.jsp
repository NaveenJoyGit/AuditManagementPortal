<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device=width, initial-scale=1.0">
	<link rel="stylesheet"
		href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
	
	<link
		href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css"
		rel="stylesheet"
		integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1"
		crossorigin="anonymous">
	
	<title>AuditManagement WebPortal</title>
</head>
<body style="height: 100vh">
	<section style="height: 100vh">
		<div class="container h-100 w-50">
			<div class="row h-100 align-items-center">
				<div class="col d-flex justify-content-center">
					<a class="btn btn-default" href="showForm/${Internal}">
						<button type="button" class="btn btn-info btn-lg align-self-center">Internal</button>
					</a>
				</div>
				<div class="col d-flex justify-content-center">
					<a class="btn btn-default" href="showForm/${SOX}">
						<button type="button" class="btn btn-secondary btn-lg">SOX</button>
					</a>
				</div>
			</div>
		</div>
	</section>
</body>
</html>
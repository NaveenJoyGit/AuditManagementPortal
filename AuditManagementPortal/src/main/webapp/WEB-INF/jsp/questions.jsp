<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
	<section class="container">
		<c:forEach items="${audit.getQuestions()}" var="ques">
			<p>${ques.question}</p>
			<div class="container">
				<label for="yes">Yes</label> <input type="radio" id="yes"> <label
					for="no">No</label> <input type="radio" id="no">
			</div>
		</c:forEach>

		<!-- 	<form:form method="POST" action="/ProjectExecutionStatus"
			modelAttribute="project">
			<form:label path="name">Name</form:label>
			<form:input path="name" />

			<form:label path="manager">Manager</form:label>
			<form:input path="manager" />

			<form:label path="owner">Name</form:label>
			<form:input path="owner" />
			<br>
			<input type="submit" value="Submit" />

		</form:form> -->

		<table>
			<tr>
				<td>Name</td>
				<td><input type="text" id="name"></td>
			</tr>
			<tr>
				<td>Manager</td>
				<td><input type="text" id="manager"></td>
			</tr>
			<tr>
				<td>Owner</td>
				<td><input type="text" id="owner"></td>
			</tr>

			<tr>
				<td colspan="2"><input type="button" id="submit" value="Submit" /></td>
			</tr>
		</table>

		<div id="displayDiv" style="display: none">
			<h3>JSON Data returned from Server after processing</h3>
			<div id="processedData"></div>
		</div>
	</section>

	<script>
	jQuery(document).ready(function($) {
 
		$("#submit").click(function(){
			var project = {};
			project["name"] = $("#name").val();
			project["manager"] = $("#manager").val();
			project["owner"] = $("#owner").val();
			
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/ProjectExecutionStatus",
				data : JSON.stringify(project),
				dataType : 'json',				
				success : function(data) {
					$('#processedData').html(JSON.stringify(data));
					$('#displayDiv').show();
				}
			});
		});
 
	});
</script>

</body>
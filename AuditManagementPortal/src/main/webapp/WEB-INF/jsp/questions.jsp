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
	<section id="que" class="container">
	 <div id="auditType">${audit.getAuditType()}</div> 
		<c:forEach items="${audit.getQuestions()}" var="ques">
			<p>${ques.question}</p>
			<div id="questions" class="container">
				<label for="yes">Yes</label> <input type="radio" id="yes" name="yn${ques.id}"
					value="yes"> <label for="no">No</label> <input type="radio"
					id="no${ques.id}" name="yn${ques.id}" value="no">
					
				<div>${ques.id}</div>
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

		Question1 <input type="radio" name="questions1" value="1" />Yes<br />
		<input type="radio" name="questions1" value="0" />No Question2 <input
			type="radio" name="questions2" value="1" />Yes<br /> <input
			type="radio" name="questions2" value="0" />No

		<button onclick="return getCount()">check count</button>

		<div>
			<p id="count"></p>
		</div>

	</section>
	
	<div id="status">
		</div>

	<script>
	
	
	jQuery(document).ready(function($) {
		const obj = {
  			one: 1,
			two: 2,
			three: 3,
			four: 4,
			five: 5,
			six: 6,
			seven: 7,
			eight: 8,
			nine: 9,
			ten: 10
		};	
		
		
		var count = 0;
		$.each(obj, function(key, value) {
  			$("#no" + value).click(function()
			{ 
				/*	if (!$("input[name='yn'+ value]").is(':checked')) {
					$("#no" + value).attr("checked", "checked");
					console.log("CHECKED");
					count++;
				} */
				console.log("ALREADY CHECKED");
				count++;
				console.log(count); 	
			});
		});
		
		
		/* $("#no4").click(function()
		{ 
			console.log("clicked");
    		$('#no').attr("checked", "checked");
		}); */
 
		$("#submit").click(function(){
	   	 	var finalCount = count;
	   	 	console.log(count);
	   	 	
	   	 	var auditType = $('#auditType').text();
	   	 	console.log(auditType);
	   	 	

			var project = {};
			project["name"] = $("#name").val();
			project["manager"] = $("#manager").val();
			project["owner"] = $("#owner").val();
			
			var audit = {}
			audit["type"] = auditType;
			audit["count"] = finalCount
			audit["date"] = "21/04/2021";
			audit["project"] = project;
			
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/severityservice",
				data : JSON.stringify(audit),
				dataType : 'json',				
				success : function(data) {
					console.log(data);
					let div = document.createElement('div');
					let status = document.getElementById("status");
					let que = document.getElementById("que");
					let p1 = document.createElement('p');
					p1.textContent = data.status;
					let p2 = document.createElement('p');
					p2.textContent = data.status;
					let p3 = document.createElement('p');
					p3.textContent = data.status;
					
					que.style.display = 'none';
					
					div.append(p1);
					status.append(div);
					
					
					/* $('#processedData').html(JSON.stringify(data));
					$('#displayDiv').show(); */
				}
			});
		});
 
	});
</script>

</body>
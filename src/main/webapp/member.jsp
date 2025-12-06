<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>

<head>
<meta charset="ISO-8859-1">
<title>Member Enrollment Form</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
</head>
<body>

	<div class="container mt-5">
		<c:if test="${msg ne null}">
			<div id='alertId' class='alert alert-success'>
				<span class='font-weight-bold'>Message : </span> ${msg}
			</div>
			<%
			request.getSession(false).removeAttribute("msg");
			%>
		</c:if>
		
		
		<div class="card">
			<div class="card-header h2 text-primary bg-secondary">Student Enrollment Form</div>
			<div class="card-body">
				<div class="container">
					<form id="memberForm" action="./savemember" method="post" 
					enctype="multipart/form-data" novalidate >
	

						<div class="row">
						
							<div class="col-3 mb-2">
								<label for="membercodeid" class="font-weight-bold">Member Code</label> <input
									type="text" name="membercodeid" id="membercodeid" class="form-control" 
									>
								<div class="invalid-feedback">Please enter the member code</div>
							</div>
							
							<div class="col-4 mb-3">
								<label for="nameId" class="font-weight-bold">Name</label> <input
									type="text" name="nameId" id="nameId" class="form-control" 
									>
								<div class="invalid-feedback">Please enter the name.</div>
							</div>

							<div class="col-4 mb-3">
								<label for="emailId" class="font-weight-bold">Email</label> <input
									type="text" name="emailId" id="emailId" class="form-control"
									>
								<div class="invalid-feedback">Please enter a valid email.</div>
							</div>
							
							<div class="col-3 mb-3">
								<label for="photoId" class="font-weight-bold">Upload Photo</label> <input
									type="file" name="photoId" id="photoId" class="form-control"
									>
								<div class="invalid-feedback">Please enter a valid email.</div>
							</div>
							
							
						</div>

						<div class="text-center mt-3">
							<input type="submit" class="btn btn-success"
								value='save'> <input
								type="reset" class="btn btn-warning" value="Reset">
						</div>
					</form>
				</div>
			</div>
		</div>
		
		<div class="h2 text-primary">Member List</div>
			<table class="table table-bordered table-striped mt-3">
				<thead>
					<tr>
						<th>Sl.#</th>
						<th>Id</th>
						<th>Code</th>
						<th>Name</th>
						<th>Email</th>
						<th>Photo</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${members}" var="m" varStatus="counter">
						<tr>
							<td>${counter.count}</td>
							<td>${m.id}</td>
							<td>${m.memberCode}</td>
							<td>${m.name}</td>
							<td>${m.email}</td>	
							<td><a href="./download?pname=${m.photo}" class="btn btn-success"> download </a></td>						
							
							<td><a href="./delete?mid=${m.id}"
								class="text-danger">Del</a> | 
								<a href="./update?mid=${m.id}" class="text-primary">Update</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			</div>
		
		
		
		
		
		
		
		
	</div>
	<script>
		 document.addEventListener("DOMContentLoaded", function(event){
			var al=document.querySelector("#alertId");
			if(al != null){
				setTimeout(() => {
					al.remove();
				}, 3000);
			}			
		});
	</script>

</body>
</html>
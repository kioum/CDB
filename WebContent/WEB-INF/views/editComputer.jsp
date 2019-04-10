<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>"
	media="screen">
<link rel="stylesheet" href="<c:url value="/css/font-awesome.css"/>"
	media="screen">
<link rel="stylesheet" href="<c:url value="/css/main.css"/>"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<c:url value="/Dashboard"/>">
				Application - Computer Database </a>
		</div>
	</header>
<body>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computer.id}</div>
					<h1>Edit Computer</h1>

					<form action="EditServlet" method="POST">
						<input type="hidden" value="${computer.id}" id="id" name="id" />
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<label for="computerName">Computer name</label> <input
									type="text" class="form-control" id="computerName"
									name="computerName" value="${computer.name}"
									placeholder="Computer name">
							</div>
							<div class="form-group">
								<label for="introduced">Introduced date</label> <input
									type="date" class="form-control" id="introduced"
									name="introduced" value="${computer.introduced}"
									placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued">Discontinued date</label> <input
									type="date" class="form-control" id="discontinued"
									name="discontinued" value="${computer.discontinued}"
									placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId">Company</label> <select
									class="form-control" id="companyId" name="companyId">
									<c:forEach var="comp" items="${companies}">
										<option value="${comp.id}"
											<c:if test="${computer.manufacturerId==comp.id}">
												selected
											</c:if>>${comp.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<c:if test="${not empty exception}">
							<p style="color:red">
								Exception :
								<c:out value="${exception}" />
							</p>
						</c:if>
						<div class="actions pull-right">
							<input type="submit" value="Edit" class="btn btn-primary">
							or <a href="Dashboard" class="btn btn-default">Cancel</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/validator.js"/>"></script>
</body>
</html>
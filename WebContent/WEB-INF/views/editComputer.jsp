<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
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
			<a class="navbar-brand" href="<c:url value="/Dashboard"/>"><spring:message
					code="title" /></a>
		</div>
	</header>
<body>
	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<div class="label label-default pull-right">id:
						${computer.id}</div>
					<h1>
						<spring:message code="editComputer" />
					</h1>

					<form:form modelAttribute="computer" name="editComputer"
						id="editComputer" action="editComputer" method="POST">
						<form:hidden path="id" value="${computer.id}" id="id" />
						<!-- TODO: Change this value with the computer id -->
						<fieldset>
							<div class="form-group">
								<form:label path="name" for="name">
									<spring:message code="computerName" />
								</form:label>
								<spring:message code="computerName" var="computerName" />
								<form:input type="text" class="form-control" id="name"
									path="name" placeholder="${computerName}" />
							</div>
							<div class="form-group">
								<form:label path="introduced" for="introduced">
									<spring:message code="introduced" />
								</form:label>
								<form:input type="date" class="form-control" id="introduced"
									path="introduced" value="${computer.introduced}"
									placeholder="Introduced date" />
							</div>
							<div class="form-group">
								<form:label path="discontinued" for="discontinued">
									<spring:message code="discontinued" />
								</form:label>
								<form:input type="date" class="form-control" id="discontinued"
									path="discontinued" value="${computer.discontinued}"
									placeholder="Discontinued date" />
							</div>
							<div class="form-group">
								<form:label path="manufacturerId" for="manufacturerId">
									<spring:message code="company" />
								</form:label>
								<form:select path="manufacturerId" class="form-control" id="manufacturerId" >
									<c:forEach var="comp" items="${companies}">
										<form:option value="${comp.id}">${comp.name}</form:option>
									</c:forEach>
								</form:select>
							</div>
						</fieldset>
						<c:if test="${not empty exception}">
							<p style="color: red">
								Exception :
								<c:out value="${exception}" />
							</p>
						</c:if>
						<div class="actions pull-right">
							<input type="submit" value="<spring:message
					code="edit" />"
								class="btn btn-primary">
							<spring:message code="or" />
							<a href="Dashboard" class="btn btn-default"><spring:message
									code="cancel" /></a>
						</div>
					</form:form>
				</div>
			</div>
		</div>
	</section>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/validator.js"/>"></script>
</body>
</html>
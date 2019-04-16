<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="<c:url value="/css/bootstrap.min.css"/>" rel="stylesheet"
	media="screen">
<link href="<c:url value="/css/font-awesome.css"/>" rel="stylesheet"
	media="screen">
<link href="<c:url value="/css/main.css"/>" rel="stylesheet"
	media="screen">
</head>
<body>
	<header class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<a class="navbar-brand" href="<c:url value="/Dashboard"/>"><spring:message
					code="title" /></a>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="addComputer" />
					</h1>
					<form action="CreateServlet" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="computerName"><spring:message
										code="computerName" /></label> <input type="text"
									class="form-control" id="computerName" name="computerName"
									placeholder="<spring:message
										code="computerName" />">
							</div>
							<div class="form-group">
								<label for="introduced"><spring:message
										code="introduced" /></label> <input type="date" class="form-control"
									id="introduced" name="introduced" placeholder="Introduced date">
							</div>
							<div class="form-group">
								<label for="discontinued"><spring:message
										code="discontinued" /></label> <input type="date"
									class="form-control" id="discontinued" name="discontinued"
									placeholder="Discontinued date">
							</div>
							<div class="form-group">
								<label for="companyId"><spring:message code="company" /></label>
								<select class="form-control" id="companyId" name="companyId">
									<c:forEach var="comp" items="${companies}">
										<option value="${comp.id}">${comp.name}</option>
									</c:forEach>
								</select>
							</div>
						</fieldset>
						<c:if test="${not empty exception}">
							<p style="color: red">
								Exception :
								<c:out value="${exception}" />
							</p>
						</c:if>
						<div class="actions pull-right">
							<input id="btnSubmit" type="submit"
								value="<spring:message
					code="addComputer.add" />"
								class="btn btn-primary">
							<spring:message code="or" />
							<a href="<c:url value="/Dashboard"/>" class="btn btn-default"><spring:message
									code="cancel" /></a>
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
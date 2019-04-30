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
			<div class="navbar-brand navbar-right">
				<button
					onclick="location.href='<c:url value="/login?lang=fr"/>'"
					type="button" class="btn btn-default">FR</button>
				<button
					onclick="location.href='<c:url value="/login?lang=en"/>'"
					type="button" class="btn btn-default">EN</button>
			</div>
		</div>
	</header>

	<section id="main">
		<div class="container">
			<div class="row">
				<div class="col-xs-8 col-xs-offset-2 box">
					<h1>
						<spring:message code="signin" />
					</h1>
					<form name="login" id="login" action="LoginProcess" method="POST">
						<fieldset>
							<div class="form-group">
								<label for="userName"> <spring:message code="userName" />
								</label>
								<spring:message code="userName" var="userName" />
								<input type="text" class="form-control" id="userName"
									required="required" />
							</div>
							<div class="form-group">
								<label for="password"> <spring:message code="password" />
								</label>
								<spring:message code="password" var="password" />
								<input type="password" class="form-control" id="password"
									required="required" />
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
					code="signin" />"
								class="btn btn-primary">
						</div>
					</form>
				</div>
			</div>
		</div>
	</section>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
</body>
</html>
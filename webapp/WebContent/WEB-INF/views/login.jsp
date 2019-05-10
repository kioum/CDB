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
			<div class="navbar-brand navbar-right">
				<button onclick="location.href='<c:url value="/login?lang=fr"/>'"
					type="button" class="btn btn-default">FR</button>
				<button onclick="location.href='<c:url value="/login?lang=en"/>'"
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
					<c:if test="${param.error}">
						<p class="alert alert-danger">
							<spring:message code="badcredential" />
						</p>
					</c:if>
					<c:if test="${param.logout}">
						<p class="alert alert-info">
							<spring:message code="logoutMessage" />
						</p>
					</c:if>
					<form action="loginProcess" method="POST">
						<input type="hidden" name="${_csrf.parameterName}"
							value="${_csrf.token}" />
						<fieldset>
							<div class="form-group">
								<label for="username"> <spring:message code="userName" />
								</label> <input type="text" class="form-control" id="username"
									name="username" required="required" />
							</div>
							<div class="form-group">
								<label for="password"> <spring:message code="password" />
								</label> <input type="password" class="form-control" id="password"
									name="password" required="required" />
							</div>
						</fieldset>
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
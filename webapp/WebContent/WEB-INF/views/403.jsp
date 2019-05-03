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
				<button
					onclick="location.href='<c:url value="/addComputer?lang=fr"/>'"
					type="button" class="btn btn-default">FR</button>
				<button
					onclick="location.href='<c:url value="/addComputer?lang=en"/>'"
					type="button" class="btn btn-default">EN</button>
				<span><a href="logoutProcess" id="logout"> <i
						class="glyphicon glyphicon-off"></i></a></span>
			</div>
		</div>
	</header>


	<section id="main">
		<div class="container">
			<div class="alert alert-danger">
				<spring:message code="error403" />
				<br/>
			</div>
		</div>
	</section>

	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/js/dashboard.js"/>"></script>

</body>
</html>
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

	<section id="main">
		<div class="container">
			<h1 id="homeTitle">
				<c:out value="${page.getList().size()}" />
				Computers found
			</h1>
			<c:if test="${not empty exception}">
				<p style="color: red">
					Exception :
					<c:out value="${exception}" />
				</p>
			</c:if>
			<div id="actions" class="form-horizontal">
				<div class="pull-left">
					<form id="searchForm" action="#" method="GET" class="form-inline">
						<input type="search" id="searchbox" name="search"
							class="form-control" placeholder="Search name" /> <input
							type="submit" id="searchsubmit" value="Filter by name"
							class="btn btn-primary" />
					</form>
				</div>
				<div class="pull-right">
					<a class="btn btn-success" id="addComputer"
						href="<c:url value="/CreateServlet"/>">Add Computer</a> <a
						class="btn btn-default" id="editComputer" href="#"
						onclick="$.fn.toggleEditMode();">Edit</a>
				</div>
			</div>
		</div>

		<form id="deleteForm" action="#" method="POST">
			<input type="hidden" name="selection" value="">


			<div class="container" style="margin-top: 10px;">
				<table class="table table-striped table-bordered">
					<thead>
						<tr>
							<!-- Variable declarations for passing labels as parameters -->
							<!-- Table header for Computer Name -->

							<th class="editMode" style="width: 60px; height: 22px;"><input
								type="checkbox" id="selectall" /> <span
								style="vertical-align: top;"> - <a href="#"
									id="deleteSelected" onclick="$.fn.deleteSelected();"> <i
										class="fa fa-trash-o fa-lg"></i>
								</a>
							</span></th>
							<th onclick="location.href='<c:url value="/Dashboard?numPage=${page.numPage}&maxElement=${page.maxElement}&search=${search}&sortBy=name&asc=${(sortBy == 'name' && asc != null) ? !Boolean.valueOf(asc):true}"/>'">Computer
								name</th>
							<th
								onclick="location.href='<c:url value="/Dashboard?numPage=${page.numPage}&maxElement=${page.maxElement}&search=${search}&sortBy=introduced&asc=${(sortBy == 'introduced' && asc != null) ? !Boolean.valueOf(asc):true}"/>'">Introduced
								date</th>
							<!-- Table header for Discontinued Date -->
							<th
								onclick="location.href='<c:url value="/Dashboard?numPage=${page.numPage}&maxElement=${page.maxElement}&search=${search}&sortBy=discontinued&asc=${(sortBy == 'discontinued' && asc != null) ? !Boolean.valueOf(asc):true}"/>'">Discontinued
								date</th>
							<!-- Table header for Company -->
							<th
								onclick="location.href='<c:url value="/Dashboard?numPage=${page.numPage}&maxElement=${page.maxElement}&search=${search}&sortBy=companyid&asc=${(sortBy == 'companyid' && asc != null) ? !Boolean.valueOf(asc):true}"/>'">Company</th>
						</tr>
					</thead>
					<!-- Browse attribute computers -->
					<tbody id="results">
						<c:forEach var="comp" items="${page.currentPage()}">
							<tr>
								<td class="editMode"><input type="checkbox" name="cb"
									class="cb" value="${comp.id}"></td>
								<td><a href="<c:url value="/EditServlet?id=${comp.id}"/>"
									onclick=""><c:out value="${comp.name}" /></a></td>
								<td><c:if test="${comp.introduced != ''}">
										<c:out value="${comp.introduced}" />
									</c:if></td>
								<td><c:if test="${comp.discontinued != ''}">
										<c:out value="${comp.discontinued}" />
									</c:if></td>
								<td><c:out value="${comp.manufacturerName}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</form>
	</section>

	<footer class="navbar-fixed-bottom">
		<div class="container text-center">
			<ul class="pagination">
				<li><a
					href="?numPage=${page.numPage-1}&maxElement=${page.maxElement}&search=${search}&sortBy=${sortBy}&asc=${asc}"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>
				</a></li>
				<c:forEach begin="${page.startPage()}" end="${page.endPage()}"
					varStatus="loop">
					<li><a
						href="?numPage=${loop.index}&maxElement=${page.maxElement}&search=${search}&sortBy=${sortBy}&asc=${asc}">${loop.index + 1}</a></li>
				</c:forEach>
				<li><a
					href="?numPage=${page.numPage+1}&maxElement=${page.maxElement}&search=${search}&sortBy=${sortBy}&asc=${asc}"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
			</ul>
			<div class="btn-group btn-group-sm pull-right" role="group">
				<button
					onclick="location.href='<c:url value="/Dashboard?numPage=${page.numPage}&maxElement=10&search=${search}&sortBy=${sortBy}&asc=${asc}"/>'"
					type="button" class="btn btn-default">10</button>
				<button
					onclick="location.href='<c:url value="/Dashboard?numPage=${page.numPage}&maxElement=50&search=${search}&sortBy=${sortBy}&asc=${asc}"/>'"
					type="button" class="btn btn-default">50</button>
				<button
					onclick="location.href='<c:url value="/Dashboard?numPage=${page.numPage}&maxElement=100&search=${search}&sortBy=${sortBy}&asc=${asc}"/>'"
					type="button" class="btn btn-default">100</button>
			</div>
		</div>
	</footer>
	<script src="<c:url value="/js/jquery.min.js"/>"></script>
	<script src="<c:url value="/js/bootstrap.min.js"/>"></script>
	<script src="<c:url value="/js/dashboard.js"/>"></script>

</body>
</html>
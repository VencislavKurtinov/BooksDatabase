<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script src='https://code.jquery.com/jquery-3.2.1.min.js'></script>
</head>
<body>
	<form action="./Home" method="post">
		<label>Ganre</label> <input type="text" name="ganre" /><label>Way
			to the book</label> <input type="text" name="path" /> <label>Name</label> <input
			type="text" name="name" /> <input type="submit" value="add book" />
	</form>
	<form  name="byGanre" action="./Home" method="get">
		<label>Ganre</label> <input type="text" name="ganre" /> <label>Min
			Pages</label> <input type="text" name="minPages" /> <label>Max Pages</label>
		<input type="text" name="maxPages" /> <input id='bookGanre' type="submit"
			value="search by" />
	</form>
	<h1>

		<input id='book' type="text" value="Enter book name:" />


	</h1>

	<table id='books'>
		<thead>
			<tr>
				<th>Name</th>
				<th>Page</th>
				<th>Date</th>
				<th>Genre</th>

			</tr>
		</thead>
		<tbody>

		</tbody>
	</table>

	<script src="js/myscript.js"></script>
</body>
</html>
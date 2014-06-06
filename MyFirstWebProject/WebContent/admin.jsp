<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Page d'administration</title>
</head>

<%@page import="com.llt.beans.User" %>
<body>
	<img src="image/logo-llt.png" align="left" height="200" />
	<br><br><br><br>
	<h1 style="FONT-SIZE: xx-large" align=center ><b>Portail d'administration</b></h1>
	<br><br>
	<br><br>
<center>
	<%
			// S'il s'est loggué, on affiche...
			User currentUser = (User) request.getSession().getAttribute("user");
			out.print(String.format("Bonjour ! Tu es connecté en tant que : %s", currentUser.getLogin()));
			out.print("<br />");
			out.print(String.format("Ton mot de passe est : %s", currentUser.getPassword()));

	%>
	<br><br>
	<br><br>
	<form action="RecuperationUsers" method="post">
		<input type="submit" value="Gestion des utilisateurs" />
	</form>
	<br>
	<br>
	<form action="RecuperationGroup" method="post">
		<input type="submit" value="Gestion des groupes" />
	</form>
	<%
				out.print("<p><a href=\"LogoutServlet\">Déconnexion</a></p>");
	%>
</center>
</body>
</html>
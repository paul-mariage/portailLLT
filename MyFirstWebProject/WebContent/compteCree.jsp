<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>page de contenu</title>
</head>

<%@page import="com.llt.beans.User" %>

<body>
	<img src="image/logo-llt.png" align="left" height="200" />
	<br><br><br><br>
	<h1 style="FONT-SIZE: xx-large" align=center ><b>Compté crée!</b></h1>
	<br><br>
	<br><br><center>
	<%
			User currentUser = (User) request.getSession().getAttribute("user");
			out.print("Votre compte a bien été créer.");
			out.print("<br />");
			out.print("Rappel des informations du compte : <br>");
			out.print(String.format("Votre login :  %s<br>", currentUser.getLogin()));
			out.print(String.format("Votre password :%s<br>", currentUser.getPassword()));
			out.print("Un email a été envoyé à l'administrateur pour qu'il valide votre compte");
			out.print("<p><a href=\"LogoutServlet\">Retour Accueil</a></p>");

	%>
	</center>
</body>
</html>
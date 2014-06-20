<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Outil de reporting Leroux et Lotz - Page Utilisateur</title>
</head>

<%@page import="com.llt.beans.User" %>
<body>
	<img src="image/logo-llt.png" align="left" height="200" />
	<br><br><br><br>
	<h1 style="FONT-SIZE: xx-large" align=center ><b>Portail utilisateur</b></h1>
	<br><br>
	<br><br>
<center>
	<%		
	// si l'utilisateur tape l'adresse de la page content.jsp sans s'être logué auparavant, on affiche...
	if(request.getSession().getAttribute("user") == null){
		out.print("Vous n'êtes pas connecté. Veuillez vous authentifier.");
		%><br><br><button onClick="history.back()">Retour</button><%
		
	} else {
			// S'il s'est loggué, on affiche...
			User currentUser = (User) request.getSession().getAttribute("user");
			out.print(String.format("Bonjour %s %s! Vous êtes connecté en tant que : %s",currentUser.getNom(),currentUser.getPrenom(), currentUser.getLogin()));
			out.print("<br />");
			out.print(String.format("Ton mot de passe est : %s", currentUser.getPassword()));out.print("<br />");
			out.print(String.format("Vous appartenez au groupe : %s", currentUser.getGroupe()));

	%>
	<br><br>
	<br><br>
	<p><a href="<%= request.getContextPath( ) + "/frameset?__report=testRapport.rptdesign" %>"><button>Rapport Général <%=currentUser.getGroupe()%></button></a>
	<br><br>
	<p><a href="<%= request.getContextPath( ) + "/frameset?__report=testRapportParam.rptdesign" %>"><button>Rapport Paramétrable </button></a>
	<form action="sendInfoUser" method="post">
		<input type="hidden" name="login" value="<%=currentUser.getLogin()%>">
		<input type="hidden" name="password" value="<%=currentUser.getPassword()%>">
		<input type="hidden" name="nom" value="<%=currentUser.getNom()%>">
		<input type="hidden" name="prenom" value="<%=currentUser.getPrenom()%>">
		<input type="hidden" name="email" value="<%=currentUser.getEmail()%>">
		<input type="hidden" name="groupe" value="<%=currentUser.getGroupe()%>">
		<input type="submit" value="Modifier mes infos" />
	</form><br><br>
	<form action="LogoutServlet" method="get">
		<input type="submit" value="Déconnexion" />
	</form>
</center>
<%}%>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Outil de reporting Leroux & Lotz - Création d'un groupe</title>
</head>
<%@page import="com.llt.beans.User" %>
<body>
	<img src="image/logo-llt.png" align="left" height="200" />
	<br><br><br><br>
	<h1 style="FONT-SIZE: xx-large" align=center ><b>Création d'un nouveau groupe</b></h1>
	<br><br>
	<br><br>
	<center>
	<%
	// si l'utilisateur tape l'adresse de la page content.jsp sans s'être logué auparavant, on affiche...
	if(request.getSession().getAttribute("user") == null || ((User) request.getSession().getAttribute("user")).getGroupe().compareTo("admin")!=0 ){
		out.print("Vous n'êtes pas connecté en tant qu'administrateur.");
		%><br><br><button onClick="history.back()">Retour</button><%
		
	} else { %>
	<h3>Formulaire</h3>
	
	<div>
		<form action="createGroupAdmin" method="post">
			Nom du groupe d'utilisateurs : <input type="text" name="nomGroup" /><br><br>
			<input type="submit" value="Créer le groupe" />
		</form>
		<button onClick="history.back()">Retour</button>
		
	</div>
	<%} %>
	</center>
</body>
</html>
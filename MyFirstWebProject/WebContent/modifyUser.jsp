<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Outil de reporting Leroux & Lotz- Création d'un utilisateur</title>
</head>
<%@page import="com.llt.beans.User" %>
<body>
	<img src="image/logo-llt.png" align="left" height="200" />
	<br><br><br><br>
	<h1 style="FONT-SIZE: xx-large" align=center ><b>Création d'un nouvel utilisateur</b></h1>
	<br><br>
	<br><br>
	<center><h3>Formulaire</h3></center>
	
	<% User currentUser = (User) request.getSession().getAttribute("user");%>
	
	<div><center>
		<form action="updateUser" method="post">
			<input type="hidden" name="currentlogin" value="<%=currentUser.getLogin()%>">
			<input type="hidden" name=groupe value="<%=currentUser.getGroupe()%>">
			<input type="hidden" name="allowed" value="<%=currentUser.isAllowed()%>">
			Login : <input type="text" name="login" value="<%=currentUser.getLogin() %>"/><br>
			Nom : <input type="text" name="nom" value=""/><br>
			Prénom : <input type="text" name="prenom" value="" /><br>
			Password : <input type="text" name="password" value="<%=currentUser.getPassword() %>"><br><br>
			<input type="submit" value="Modifier mes infos" />
		</form><br>
		<button onClick="history.back()">Retour</button>
		</center>
	</div>
</body>
</html>
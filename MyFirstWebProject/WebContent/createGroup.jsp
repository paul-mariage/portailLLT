<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Accueil Visiteur</title>
</head>
<body>
	<img src="image/logo-llt.png" align="left" height="200" />
	<br><br><br><br>
	<h1 style="FONT-SIZE: xx-large" align=center ><b>Création d'un nouvel utilisateur</b></h1>
	<br><br>
	<br><br>
	<center><h3>Formulaire</h3></center>
	
	<div><center>
		<form action="createGroupAdmin" method="post">
			Nom du groupe d'utilisateurs : <input type="text" name="nomGroup" /><br><br>
			<input type="submit" value="connexion" />
		</form></center>
	</div>
</body>
</html>
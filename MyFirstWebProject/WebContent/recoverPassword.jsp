<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Outil de reporting Leroux et Lotz - Récupération du mot de passe</title>
</head>
<body>
	<img src="image/logo-llt.png" align="left" height="200" />
	<br><br><br><br>
	<h1 style="FONT-SIZE: xx-large" align=center ><b>Récupération du mot de passe</b></h1>
	<br><br>
	<br><br>
	<center><h3>Formulaire</h3></center>
	
	<div><center>
		<form action="recoverInformation" method="post">
			Login : <input type="text" name="login" /><br>
			Email : <input type="text" name="email" /><br>
			<input type="hidden" name="champ" value="password" /><br>
		
			<input type="submit" value="Renvoyer mon mot de passe" />
		</form><br>
		<button onClick="history.back()">Retour</button>
		</center>
	</div>
</body>
</html>
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
	<h1 style="FONT-SIZE: xx-large" align=center ><b>Portail de reporting Leroux et Lotz</b></h1>
	<br><br>
	<br><br>
	<center><h3>Formulaire de connexion</h3>
	
	<div>
		<form action="LoginServlet" method="post">
			Login : <input type="text" name="login" />
			Password : <input type="password" name="password" />
			<input type="submit" value="Connexion" />
		</form><br />
		<form action="redirection" method="post">
			<input type="submit" name="link" value="Créer un compte" />
		</form><br />
	</div></center>
</body>
</html>
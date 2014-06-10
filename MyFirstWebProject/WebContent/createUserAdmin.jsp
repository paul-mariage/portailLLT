<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Outil de reporting Leroux & Lotz - Création d'un utilisateur</title>
</head>

<%@page import="com.llt.beans.User" %>
<%@page import="com.llt.beans.Group" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>
<body>
	<img src="image/logo-llt.png" align="left" height="200" />
	<br><br><br><br>
	<h1 style="FONT-SIZE: xx-large" align=center ><b>Création d'un nouvel utilisateur</b></h1>
	<br><br>
	<br><br>
	<center><h3>Formulaire</h3></center>
	
	<div><center>
		<form action="createUserAdmin" method="post">
			Login : <input type="text" name="login" /><br>
			Password : <input type="password" name="password" /><br>
			Groupe : <SELECT name="group" size="1">
					<% 
					
					List<Group> listeGroups = (ArrayList<Group>) request.getAttribute("listeGroup");
					        Iterator<Group> it = listeGroups.iterator();
					        
					while(it.hasNext()){ 
            			Group currentGroup = it.next();
           			 %>
					<OPTION value="<%=currentGroup.getNomGroup()%>"><%=currentGroup.getNomGroup()%></OPTION>
						<%} %>
						</SELECT><br>
			Activé :  <INPUT TYPE="CHECKBOX" NAME="allowed" VALUE="true" CHECKED><BR>
            
			<input type="submit" value="Créer le compte" />
		</form>
		<button onClick="history.back()">Retour</button> 
		</center>
		
	</div>
</body>
</html>
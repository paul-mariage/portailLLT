<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Outil de reporting Leroux & Lotz - Liste des groupes</title>
<script language="JavaScript">
	function onClicBouton() {
		request.setParameter("nomGroup", "invité");
	}
</script>
</head>
<%@page import="com.llt.beans.Group"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>

<body>
	<img src="image/logo-llt.png" align="left" height="200" />
	<br>
	<br>
	<br>
	<br>
	<h1 style="FONT-SIZE: xx-large" align=center>
		<b>Liste de tous les groupes du portail</b>
	</h1>
	<br>
	<br>
	<br>
	<br>
	<%
		List<Group> listeGroups = (ArrayList<Group>) request.getAttribute("listeGroup");
		        Iterator<Group> it = listeGroups.iterator();
	%>
	<center>
		<TABLE BORDER="1">
			<TR>
				<TH>Nom du groupe</TH>
				<TH>Utilisateurs</TH>
				<TH>Supprimer Groupe</TH>
			</TR>
			<%
				while(it.hasNext()){ 
				            Group currentGroup = it.next();
			%>
			<TR>
				<TD><%=currentGroup.getNomGroup()%></td>
				<TD><FORM action="RecuperationUsersGroup" method="post">
						<input type="hidden" name="nomGroup"
							value=<%=currentGroup.getNomGroup()%>> <INPUT
							TYPE="submit" VALUE="Show users">
					</FORM></TD>
				<TD><FORM action="deleteGroup" method="post">
						<input type="hidden" name="nomGroup"
							value=<%=currentGroup.getNomGroup()%>>
						<center>
							<INPUT TYPE="submit" VALUE="Delete">
						</center>
					</FORM></TD>
			</TR>
			<%
				}
			%>
		</TABLE>
		<br> <a href="createGroup.jsp"><button>Créer un groupe</button></a>
		<a href="admin.jsp"><button>Retour</button></a>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Outil de reporting Leroux & Lotz - Liste des utilisateurs</title>
</head>
<%@page import="com.llt.beans.User" %>
<%@page import="com.llt.beans.Group" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Iterator" %>

<body>
	<img src="image/logo-llt.png" align="left" height="200" />
	<br><br><br><br>
	<h1 style="FONT-SIZE: xx-large" align=center ><b>Liste de tous les utilisateurs du portail</b></h1>
	<br><br>
	<br><br>
<%
        List<User> listeUser = (ArrayList<User>) request.getAttribute("listeUser");
        Iterator<User> it = listeUser.iterator();
        List<Group> listeGroup = (ArrayList<Group>) request.getAttribute("listeGroup");
        
        
        %>
<center><TABLE BORDER="1">
            <TR>
                <TH>Login</TH>
                <TH>Password</TH>
                <TH>Nom du groupe</TH>
                <TH>Activ�</TH>
                <TH>Supprimer</TH>
            </TR>
            <% while(it.hasNext()){ 
            User currentUser = it.next();
            %>
            <TR>
                <TD> <%= currentUser.getLogin() %></td>
                <TD> <%= currentUser.getPassword() %></TD>
               <td><FORM action="changeGroupUser" method="post">
               		<input type="hidden" name="login" value="<%=currentUser.getLogin()%>">
					<center><SELECT name="group" onChange="this.form.submit()" size="1">
					<% 
					Iterator<Group> it2 = listeGroup.iterator();
					while(it2.hasNext()){ 
            			Group currentGroup = it2.next();
           			 %>
					<OPTION <% if (currentUser.getGroupe().equals(currentGroup.getNomGroup())){%>SELECTED <%} %>value="<%=currentGroup.getNomGroup()%>"><%=currentGroup.getNomGroup()%></OPTION>
						<%} %>
						</SELECT></center>
						</FORM>
					</td>
               	<% if (currentUser.isAllowed()){ %>
               			<TD> <FORM action="changeStateUser" method="post">
       						<input type="hidden" name="login" value="<%=currentUser.getLogin()%>">
       						<input type="hidden" name="allowed" value="0">
        					<center><INPUT TYPE="submit" VALUE="D�sactiver"></center>
    					</FORM></TD>
    			<%} else { %>
    					<TD> <FORM action="changeStateUser" method="post">
       						<input type="hidden" name="login" value="<%=currentUser.getLogin()%>">
       						<input type="hidden" name="groupe" value="<%=currentUser.getGroupe()%>">
       						<input type="hidden" name="allowed" value="1">
        					<center><INPUT TYPE="submit" VALUE="Activer"></center>
    					</FORM></TD>
            	<% } %>
                <TD> <FORM action="deleteUser" method="post">
       						<input type="hidden" name="login" value="<%=currentUser.getLogin()%>">
        					<center><INPUT TYPE="submit" VALUE="Delete"></center>
    					</FORM></TD>
            </TR>
            <% } %>
            </TABLE>
            <br>
            <form action="RecuperationGroup" method="post">
            		<input type="hidden" name="link" value="createUserAdmin.jsp">
					<input type="submit" value="Cr�er un compte" />
			</form><BR>
			<a href="admin.jsp"><button>Retour</button></a>
</body>
</html>
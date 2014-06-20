package com.llt.servlets;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.llt.beans.User;
import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class createUser
 */
public class createUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public createUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Tentative d'accès direct à la servlet createUser");
		getServletContext().getRequestDispatcher("/home.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("-----------------CreateUser------------------");
		System.out.println("Début doPost CreateUser");

		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		User myUser = new User(login,password,nom,prenom,email,"invite",false);

		if (!login.isEmpty() && !password.isEmpty() && !nom.isEmpty() && !prenom.isEmpty() && !email.isEmpty()) {
		/* Connexion à la base de données */
		String url = "jdbc:mysql://localhost:8082/gestionPortail";
		String utilisateur = "root";
		String motDePasse = "root";
		Connection connexion = null;
		Statement stmt = null;
		ResultSet getUsers = null;
		ResultSet getAdmins = null;
		boolean existe = false;

		try {

			Class<?> driver_class = Class.forName("com.mysql.jdbc.Driver");
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			// Réalisation de la connexion
			connexion = DriverManager.getConnection(url, utilisateur,
					motDePasse);

			// Création du statement
			stmt = connexion.createStatement();
			
			//Récupération des utilisateurs
			getUsers = stmt.executeQuery("SELECT * FROM user ORDER BY nomGroup;");


			//Boucle de parcours getUsers

			while (getUsers.next()) {

				if (getUsers.getString("login").compareTo(login)==0)
				{
					existe = true;
				}

			}
			
			getAdmins = stmt.executeQuery("SELECT * FROM user WHERE nomGroup='admin';");
			while (getAdmins.next()) {

				
				envoyerMailSMTP(new User("","","","",getAdmins.getString("email"),"admin",true),myUser,false);
				

			}
			
			if(!existe)
			{
			System.out.println("Requete : INSERT INTO user VALUES ('" + login + "','"
					+ password + "','" + nom + "','" + prenom + "','" + email + "','invité','0'); ");
			// Récupération des utilisateurs
			stmt.executeUpdate("INSERT INTO user VALUES ('" + login + "','"
					+ password + "','" + nom + "','" + prenom + "','" + email + "','invité','0');");
			
			}

		} catch (SQLException e) {
			/* Gérer les éventuelles erreurs ici */
			System.out.println("Erreur SQLExeption 1: ");
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (stmt != null)
				try {
					System.out.println("Fermeture des statements");
					/* Fermeture de la connexion */
					stmt.close();
				} catch (SQLException ignore) {
					/*
					 * Si une erreur survient lors de la fermeture, il suffit de
					 * l'ignorer.
					 */
					System.out.println("Erreur SQLExeption 3");
				}
			if (connexion != null)
				try {
					/* Fermeture de la connexion */
					System.out.println("Fermeture de la connection");
					connexion.close();
				} catch (SQLException ignore) {
					/*
					 * Si une erreur survient lors de la fermeture, il suffit de
					 * l'ignorer.
					 */
					System.out.println("Erreur SQLExeption 4");
				}
		}
		
		if(!existe)
		{
		request.getSession().setAttribute("user", new User(login,password,nom,prenom,email,"invité",false));
		request.getRequestDispatcher("/compteCree.jsp").forward(request,
				response);
		}
		else request.getRequestDispatcher("/userExist.jsp").forward(request,
				response);
		} else {
			// Si l'utilisateur n'a pas rempli les deux champs du formulaire, il
			// est renvoyé sur home.jsp
			System.out.println("Donnée manquante");
			response.sendRedirect("missingValue.jsp");
		}
	}
	
public static boolean envoyerMailSMTP(User admin,User user,boolean debug) {
		boolean result = false;
		System.out.println(">>>envoyerMailSMTP");
		try {
		    Properties properties = new Properties(); 
		    properties.setProperty("mail.transport.protocol", "smtp"); 
		    properties.setProperty("mail.smtp.host", "localhost"); 
		    properties.setProperty("mail.smtp.user", "root"); 
		    properties.setProperty("mail.from", "Portail LLT"); 
		    Session session = Session.getInstance(properties); 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("Portail@llt.fr"));
			System.out.println(">>>>>>>Parcours des admins");
	
				InternetAddress[] internetAddresses = new InternetAddress[1];
					internetAddresses[0] = new InternetAddress(admin.getEmail());

				message.setRecipients(Message.RecipientType.TO,internetAddresses);
				message.setSubject("Nouveau compte crée!");
				message.setText("Bonjour !\n\n "+user.getPrenom()+" "+user.getNom()+" viens de créer son compte. Pensez à l'activer. \n\n\nPortail Leroux & Lotz");
				message.setHeader("X-Mailer", "Java");
				message.setSentDate(new Date());
				session.setDebug(debug);
				Transport.send(message);

			result = true;
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		result = true;
	
		return result;
		
	}
}	

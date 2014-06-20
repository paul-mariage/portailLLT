package com.llt.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.llt.beans.Group;
import com.llt.beans.User;
import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class changeStateUser
 */
public class changeStateUser extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public changeStateUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Tentative d'accès direct à la servlet changeStateUser");
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
				.println("-----------------changeStateUser------------------");
		System.out.println("Début doPost changeStateUser");

		//On récupère le login, le groupe et l'autorisation de l'utilisateur
		String login = request.getParameter("login");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String email = request.getParameter("email");
		String groupe = request.getParameter("groupe");
		String allowed = request.getParameter("allowed");
		
		System.out.println("L'user "+login+" du groupe "+groupe+": allowed ="+allowed);

		/* Connexion à la base de données */
		String url = "jdbc:mysql://localhost:8082/gestionPortail";
		String utilisateur = "root";
		String motDePasse = "root";
		Connection connexion = null;
		Statement stmt = null;
		ResultSet getUsers = null;
		List<User> listeUser = new ArrayList<User>();
		ResultSet getGroups = null;
		List<Group> listeGroup = new ArrayList<Group>();
		boolean autorise = (allowed.compareTo("true")==0);

		try {

			Class<?> driver_class = Class.forName("com.mysql.jdbc.Driver");
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			// Réalisation de la connexion
			connexion = DriverManager.getConnection(url, utilisateur,
					motDePasse);

			// Création du statement
			stmt = connexion.createStatement();

			System.out.println("Requete : UPDATE user SET allowed='" + allowed
					+ "' WHERE login='" + login + "';");
			// Mise a jour du groupe
			stmt.executeUpdate("UPDATE user SET allowed='" + allowed
					+ "' WHERE login='" + login + "';");
			
			//Envoi du mail disant que le compte est activé
			envoyerMailSMTP(new User(login, "", nom, prenom, email, "invite", autorise),false);

			
			System.out.println("Requete : SELECT * FROM user where nomGroup='"+groupe+"';");
			// récupération de la nouvelle liste d'utilisateur du groupe de l'utilisateur
			getUsers = stmt.executeQuery("SELECT * FROM user where nomGroup='"+groupe+"';");

			// Boucle de parcours getUsers

			while (getUsers.next()) {

				listeUser.add(new User(getUsers.getString("login"), getUsers
						.getString("password"),getUsers.getString("nom"),getUsers.getString("prenom"),getUsers.getString("email"), getUsers.getString("nomGroup"),
						getUsers.getBoolean("allowed")));

			}

			request.setAttribute("listeUser", listeUser);
			
			//Récupération des groupes
			getGroups = stmt.executeQuery("SELECT * FROM groups;");


			//Boucle de parcours getGroups

			while (getGroups.next()) {

				listeGroup.add(new Group(getGroups.getString("nomGroup"),
						getGroups.getString("link")));

			}

			request.setAttribute("listeGroup", listeGroup);

			//Affichage sur la console des groupes pour test
			Iterator<Group> it2 = listeGroup.iterator();
			while (it2.hasNext()) {
				System.out.println(it2.next().toString());
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

		request.getRequestDispatcher("/ShowUsers.jsp").forward(request,
				response);

	}
	
	public static boolean envoyerMailSMTP(User user,boolean debug) {
		boolean result = false;
		String val = "";
		System.out.println(">>>envoyerMailSMTP");
		if (user.isAllowed()){
			val= "activé";
		}
		else val = "désactivé";
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
					internetAddresses[0] = new InternetAddress(user.getEmail());

				message.setRecipients(Message.RecipientType.TO,internetAddresses);
				message.setSubject("Compte "+val);
				message.setText("Bonjour "+user.getPrenom()+" "+user.getNom()+"!\n\nVotre compte viens d'être"+val+".\n\n\nPortail Leroux & Lotz");
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

package com.llt.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
			envoyerMailSMTP(true);

			
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
	
	public static boolean envoyerMailSMTP(boolean debug) {
		boolean result = false;
		
	/*		Session session = null;
			try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");
			session = (Session) envCtx.lookup("mail/NomDeLaRessource");
			} catch (Exception ex) {
			System.out.println("erreur au lookup");
			System.out.println( ex.getMessage());
			}
			
		Message message = new MimeMessage(session);*/
		System.out.println("Compte activé, Envoi d'un mail ici");
		
		//Problème avec librairie
		
		/*message.setFrom(new InternetAddress("no-reply@portailLLT.fr"));
		InternetAddress[] internetAddresses = new InternetAddress[1];
		internetAddresses[0] = new InternetAddress("paul.mariage@live.fr");
		message.setRecipients(Message.RecipientType.TO,internetAddresses);
		message.setSubject("Test");
		message.setText("test mail");
		message.setHeader("X-Mailer", "Java");
		message.setSentDate(new Date());
		session.setDebug(debug);
		Transport.send(message);*/
		
		
		result = true;
	
		return result;
		
		

	}

}

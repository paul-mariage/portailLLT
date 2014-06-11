package com.llt.servlets;

import java.sql.Statement;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;




import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.llt.beans.User;
import com.mysql.jdbc.Driver;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LoginServlet() {
		// TODO Auto-generated constructor stub

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*
		 * si l'utilisateur tape l'adresse de la servlet directement dans la
		 * barre d'adresse, il est renvoyé sur la page d'accueil !
		 */
		System.out.println("Tentative d'accès direct à la servlet LoginServlet");
		getServletContext().getRequestDispatcher("/home.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----------------LoginServlet------------------");
		// on récupère les valeurs des deux champs du formulaire de la page de
		// login
		boolean autorisationDB = false;
		String groupeDB = "";
		String nom = "";
		String prenom = "";
		String email = "";
		String link="";
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		System.out.println("Sauvegarde du login et du password");

		// on vérifie qu'ils ont bien été remplis
		if (!login.isEmpty() && !password.isEmpty()) {
			// on créé un objet User pour conserver les informations de
			// l'utilisateur...
			System.out.println("Creation de l'user");
			User currentUser = new User();
			currentUser.setLogin(login);
			currentUser.setPassword(password);
			System.out.println("user crée");
			
			System.out.println("Login : "+currentUser.getLogin()+" password : '"+currentUser.getPassword()+"'");

			System.out.println("Recherche de l'utilisateur existant");
			// On parcourt les utilisateurs sur la base de donnée pour vérifier
			// qu'il existe			
			
			
			
			/* Connexion à la base de données */
			String url = "jdbc:mysql://localhost:8082/gestionPortail";
			String utilisateur = "root";
			String motDePasse = "root";
			Connection connexion = null;
			Statement stmt1 = null;
			Statement stmt2 = null;
			ResultSet getInfosUser = null;
			ResultSet getInfosGroups = null;
			boolean vide = false;
			boolean badPassword = false;

			try {
				
				Class driver_class = Class.forName("com.mysql.jdbc.Driver");
				Driver driver = (Driver) driver_class.newInstance();
			    DriverManager.registerDriver(driver); 
				
				connexion = DriverManager.getConnection(url, utilisateur,
						motDePasse);

				stmt1 = connexion.createStatement();
				stmt2 = connexion.createStatement();

				getInfosUser = stmt1
						.executeQuery("SELECT * FROM user WHERE login='"
								+ currentUser.getLogin()+"';");
				
				
				if (getInfosUser.next())
				{
				String loginDB = getInfosUser.getString("login");
				System.out.println("login : "+loginDB);
				String passwordDB = getInfosUser.getString("password");
				System.out.println("passwordDB : '"+passwordDB+"'");
				nom = getInfosUser.getString("nom");
				System.out.println("nom : '"+nom+"'");
				prenom = getInfosUser.getString("prenom");
				System.out.println("prenom : '"+passwordDB+"'");
				email = getInfosUser.getString("email");
				System.out.println("email : '"+passwordDB+"'");
				autorisationDB = getInfosUser.getBoolean("allowed");
				System.out.println("autorisation : "+autorisationDB);
				groupeDB = getInfosUser.getString("nomGroup");
				System.out.println("groupe : "+groupeDB);
				
				System.out.println("Test de mot de passe");				
				if (passwordDB.compareTo(password)!=0)
				{
					System.out.println("BAD PASSWORD");
					badPassword = true;
				}
				
				
				getInfosUser.close();				
				
				getInfosGroups = stmt2
						.executeQuery("SELECT * FROM groups WHERE nomGroup='"+groupeDB+"';");
				
				
				if (getInfosGroups.next())
				{
					 link = getInfosGroups.getString("link");
					 System.out.println("link : "+link);
				}

				}
				else {
					System.out.println("getInfosUser est vide");
					vide = true;
				}

			} catch (SQLException e) {
				/* Gérer les éventuelles erreurs ici */
				System.out.println("Erreur SQLExeption 1: ");
				e.printStackTrace();
				request.getRequestDispatcher("/errorDB").forward(request, response);
				
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
				if (getInfosUser != null)
					try {
						System.out.println("Fermeture getInfosUser");
						/* Fermeture de la connexion */
						getInfosUser.close();
					} catch (SQLException ignore) {
						/*
						 * Si une erreur survient lors de la fermeture, il
						 * suffit de l'ignorer.
						 */
						System.out.println("Erreur SQLExeption 2");
					}
				if (stmt1 != null || stmt2 != null)
					try {
						System.out.println("Fermeture des statements");
						/* Fermeture de la connexion */
						stmt1.close();
						stmt2.close();
					} catch (SQLException ignore) {
						/*
						 * Si une erreur survient lors de la fermeture, il
						 * suffit de l'ignorer.
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
						 * Si une erreur survient lors de la fermeture, il
						 * suffit de l'ignorer.
						 */
						System.out.println("Erreur SQLExeption 4");
					}
			}

			if (vide) request.getRequestDispatcher("userInexistant.jsp").forward(request, response);
			
			if (!vide & badPassword) 
				{
				System.out.println("        Redirection car mauvais mot de passe");
				request.getRequestDispatcher("badPassword.jsp").forward(request, response);
				}


			if (!vide & !badPassword & autorisationDB) {
				// Si il est activé,on le redirige vers son lien
				//On stocke l'utilisateur dans la session
				System.out.println("        Redirection user autorisé");
				currentUser.setGroupe(groupeDB);
				currentUser.setNom(nom);
				currentUser.setPrenom(prenom);
				currentUser.setEmail(email);
				request.getSession().setAttribute("user", currentUser);
				
				//On redirige vers la page
				System.out.println("On redirige vers : "+link);
				request.getRequestDispatcher("/"+link).forward(request, response);
				//request.getRequestDispatcher("content.jsp").forward(request, response);
			}
			if(!vide & !badPassword & !autorisationDB)  {
				System.out.println("        Redirection car utilisateur non autorisé");
				request.getRequestDispatcher("/nonAutorise.jsp").forward(request, response);
			}


		} else {
			// Si l'utilisateur n'a pas rempli les deux champs du formulaire, il
			// est renvoyé sur home.jsp
			System.out.println("Donnée manquante");
			response.sendRedirect("missingValue.jsp");
		}
	}

}

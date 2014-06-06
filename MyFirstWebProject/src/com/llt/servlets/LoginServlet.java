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
		 * barre d'adresse, il est renvoy� sur la page d'accueil !
		 */
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
		// on r�cup�re les valeurs des deux champs du formulaire de la page de
		// login
		boolean autorisationDB = false;
		String link="";
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		System.out.println("Sauvegarde du login et du password");

		// on v�rifie qu'ils ont bien �t� remplis
		if (!login.isEmpty() && !password.isEmpty()) {
			// on cr�� un objet User pour conserver les informations de
			// l'utilisateur...
			System.out.println("Creation de l'user");
			User currentUser = new User();
			currentUser.setLogin(login);
			currentUser.setPassword(password);
			System.out.println("user cr�e");
			
			System.out.println("Login : "+currentUser.getLogin()+" password : "+currentUser.getPassword());

			System.out.println("Recherche de l'utilisateur existant");
			// On parcourt les utilisateurs sur la base de donn�e pour v�rifier
			// qu'il existe			
			
			
			
			/* Connexion � la base de donn�es */
			String url = "jdbc:mysql://localhost:8082/gestionPortail";
			String utilisateur = "root";
			String motDePasse = "root";
			Connection connexion = null;
			Statement stmt1 = null;
			Statement stmt2 = null;
			ResultSet getInfosUser = null;

			try {
				
				Class driver_class = Class.forName("com.mysql.jdbc.Driver");
				Driver driver = (Driver) driver_class.newInstance();
			    DriverManager.registerDriver(driver); 
				
				connexion = DriverManager.getConnection(url, utilisateur,
						motDePasse);

				stmt1 = connexion.createStatement();

				getInfosUser = stmt1
						.executeQuery("SELECT * FROM user WHERE login='"
								+ currentUser.getLogin() + "' AND password='"
								+ currentUser.getPassword()+"';");
				
				getInfosUser.next();

				String loginDB = getInfosUser.getString("login");
				System.out.println("login : "+loginDB);
				String passwordDB = getInfosUser.getString("password");
				System.out.println("password : "+passwordDB);
				autorisationDB = getInfosUser.getBoolean("allowed");
				System.out.println("autorisation : "+autorisationDB);
				String groupeDB = getInfosUser.getString("nomGroup");
				System.out.println("groupe : "+groupeDB);
				
				getInfosUser.close();
				
				stmt2 = connexion.createStatement();
				
				getInfosUser = stmt2
						.executeQuery("SELECT * FROM groups WHERE nomGroup='"+groupeDB+"';");
				
				getInfosUser.next();
				
				 link = getInfosUser.getString("link");
				 System.out.println("link : "+link);
				 

			} catch (SQLException e) {
				/* G�rer les �ventuelles erreurs ici */
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

			

			System.out.println("redirection si utilisateur autoris� par l'admin");
			if (autorisationDB) {
				// Si il est activ�,on le redirige vers son lien
				//On stocke l'utilisateur dans la session
				request.getSession().setAttribute("user", currentUser);
				
				//On redirige vers la page
				System.out.println("On redirige vers : "+link);
				request.getRequestDispatcher("/"+link).forward(request, response);
				//request.getRequestDispatcher("content.jsp").forward(request, response);
			}
			else  {
				System.out.println("Utilisateur inexistant ou non autoris�");
				response.sendRedirect("/home.jsp");
			}


		} else {
			// Si l'utilisateur n'a pas rempli les deux champs du formulaire, il
			// est renvoy� sur home.jsp
			System.out.println("Saisie incorrecte");
			response.sendRedirect("home.jsp");
		}
	}

}

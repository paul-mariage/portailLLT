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
 * Servlet implementation class RecuperationUsersGroup
 */
public class RecuperationUsersGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RecuperationUsersGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out
				.println("-----------------RecuperationUsersGroups------------------");
		System.out.println("Début doPost RecuperationUsers");
		/* Connexion à la base de données */
		String nomGroup = request.getParameter("nomGroup");
		String url = "jdbc:mysql://localhost:8082/gestionPortail";
		String utilisateur = "root";
		String motDePasse = "root";
		Connection connexion = null;
		Statement stmt = null;
		ResultSet getUsers = null;
		List<User> listeUser = new ArrayList<User>();
		ResultSet getGroups = null;
		List<Group> listeGroup = new ArrayList<Group>();
		System.out.println("nomGroup = "+nomGroup);
		try {

			Class driver_class = Class.forName("com.mysql.jdbc.Driver");
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			// Réalisation de la connexion
			connexion = DriverManager.getConnection(url, utilisateur,
					motDePasse);

			// Création du statement
			stmt = connexion.createStatement();

			// Récupération des utilisateurs
			getUsers = stmt.executeQuery("SELECT * FROM user WHERE nomGroup='"+nomGroup+"';");

			// Boucle de parcours getUsers

			while (getUsers.next()) {

				listeUser.add(new User(getUsers.getString("login"), getUsers
						.getString("password"), getUsers.getString("nomGroup"),
						getUsers.getBoolean("allowed")));

			}

			request.setAttribute("listeUser", listeUser);
			
			//Récupération des groupes
			getGroups = stmt.executeQuery("SELECT * FROM groups;");


			//Boucle de parcours getUsers

			while (getGroups.next()) {

				listeGroup.add(new Group(getGroups.getString("nomGroup"),
						getGroups.getString("link")));

			}

			request.setAttribute("listeGroup", listeGroup);

			//Affichage sur la console des utilisateurs pour test
			Iterator<Group> it2 = listeGroup.iterator();
			while (it2.hasNext()) {
				System.out.println(it2.next().toString());
			}

			// Affichage sur la console des utilisateurs pour test
			Iterator<User> it = listeUser.iterator();
			while (it.hasNext()) {
				System.out.println(it.next().toString());
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
			if (getUsers != null)
				try {
					System.out.println("Fermeture getInfosUser");
					/* Fermeture de la connexion */
					getUsers.close();
				} catch (SQLException ignore) {
					/*
					 * Si une erreur survient lors de la fermeture, il suffit de
					 * l'ignorer.
					 */
					System.out.println("Erreur SQLExeption 2");
				}
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
		System.out.println("Redirection ves la page des utilisateur du groupe"+nomGroup);
		request.getRequestDispatcher("ShowUsers.jsp")
				.forward(request, response);
	}

}

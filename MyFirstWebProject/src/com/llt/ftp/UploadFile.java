package com.llt.ftp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.llt.beans.Releve;
import com.mysql.jdbc.Driver;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class UploadFile
 */
public class UploadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UploadFile() {
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
		System.out.println("-----------DoPost de UploadFile----------");

		String filename = "";
		String type = "";
		File f = null;

		MultipartRequest multi = new MultipartRequest(request, ".");
		Enumeration<String> files = multi.getFileNames();
		System.out.println("files has more element : "
				+ files.hasMoreElements());

		while (files.hasMoreElements()) {
			String lefichier = files.nextElement().split(".bin")[0];
			filename = multi.getFilesystemName(lefichier);
			filename = filename.split(".bin")[0];
			type = multi.getContentType(lefichier);
			f = multi.getFile(lefichier);

			System.out.println("nom du fichier = " + filename);
			System.out.println("type du fichier = " + type);
			System.out.println("Longeur du fichier = " + f.length());
			System.out.println(f.getAbsolutePath());
			ParseFile(f);
			f.delete();
			System.out.println("Fichier Supprimé!");
		}

		System.out.println(f.toString());

		System.out.println("Fin de l'upload");

	}

	public void ParseFile(File f) {
		System.out.println("Parsing du fichier!");
		String maDate = "";
		String maVariable = "";
		String maValue = "";
		String monJour = null;
		String monMois = null;
		String monAnnee = null;
		String monHeure = null;
		String maMinute = null;
		String maSeconde = null;
		String monSite= null;

		try {
			// Création du flux bufférisé sur un FileReader, immédiatement suivi
			// par un
			// try/finally, ce qui permet de ne fermer le flux QUE s'il le
			// reader
			// est correctement instancié (évite les NullPointerException)
			BufferedReader buff = new BufferedReader(new FileReader(f));

			try {
				String line;
				line = buff.readLine();
				// Lecture du fichier ligne par ligne. Cette boucle se termine
				// quand la méthode retourne la valeur null.
				while ((line = buff.readLine()) != null) {
					if (line.compareTo("")!=0)
					{
					StringTokenizer st = new StringTokenizer(line, ";");
					if (st.hasMoreTokens())
						monSite = (new String(st.nextToken())).replace("\"", "");
					if (st.hasMoreTokens())
						maDate = (new String(st.nextToken())).replace("\"", "");
					if (st.hasMoreTokens())
						maVariable = (new String(st.nextToken())).replace("\"", "");
					if (st.hasMoreTokens())
						maValue = new String(st.nextToken());
					//System.out.println("A "+monSite+" le "+maDate+" ,la variable "+maVariable+" avait la valeur "+maValue);


					StringTokenizer st2 = new StringTokenizer(maDate, "/");

					if (st2.hasMoreTokens())
						monJour = new String(st2.nextToken());

					if (st2.hasMoreTokens())
						monMois = new String(st2.nextToken());

					if (st2.hasMoreTokens())
						monAnnee = new String(st2.nextToken());

					if (st2.hasMoreTokens())
						monHeure = new String(st2.nextToken());

					if (st2.hasMoreTokens())
						maMinute = new String(st2.nextToken());

					if (st2.hasMoreTokens())
						maSeconde = new String(st2.nextToken());


					
					Releve monReleve = new Releve(monSite, monAnnee, monMois, monJour, monHeure, maMinute, maSeconde,maVariable,Float.parseFloat(maValue));
					TraiterReleve(monReleve);
					//System.out.println(monReleve.toString());
					
				}
				}
			} finally {
				// dans tous les cas, on ferme nos flux
				buff.close();
			}
		} catch (IOException ioe) {
			// erreur de fermeture des flux
			System.out.println("Erreur --" + ioe.toString());
		}

	}

	private void TraiterReleve(Releve monReleve) {
		
		/* Connexion à la base de données */
		String url = "jdbc:mysql://localhost:8082/"+monReleve.getSite();
		String utilisateur = "root";
		String motDePasse = "root";
		Connection connexion = null;
		Statement stmt = null;

		try {

			Class<?> driver_class = Class.forName("com.mysql.jdbc.Driver");
			Driver driver = (Driver) driver_class.newInstance();
			DriverManager.registerDriver(driver);

			// Réalisation de la connexion
			connexion = DriverManager.getConnection(url, utilisateur,
					motDePasse);

			// Création du statement
			stmt = connexion.createStatement();
			
			String date = monReleve.getAnnee()+"-"+monReleve.getMois()+"-"+monReleve.getJour()+" "+monReleve.getHeure()+":"+monReleve.getMinute()+":"+monReleve.getSeconde();
			
			System.out.println("Requete : INSERT INTO "+monReleve.getNomVariable()+" (DateReleve,Value) VALUES ('" + date + "','"
					+ monReleve.getValue()+"');");
			// Récupération des utilisateurs
			stmt.executeUpdate("INSERT INTO "+monReleve.getNomVariable()+" (DateReleve,Value) VALUES ('" + date + "','"
					+ monReleve.getValue()+"');");

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
					connexion.close();
				} catch (SQLException ignore) {
					/*
					 * Si une erreur survient lors de la fermeture, il suffit de
					 * l'ignorer.
					 */
					System.out.println("Erreur SQLExeption 4");
				}
		}
		
	}
}

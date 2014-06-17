package com.llt.ftp;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class Upload
 */
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Upload() {
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
		String test = request.getParameter("Test");

		System.out.println("test GET : " + test);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----------DoPost de Upload----------");
		String name = "";
		String value = "";
		String filename = "";
		String type = "";
		File f = null;
		
		try{
			
		// On sait que cela va être une requete multipart/form-data

		// On construit la multipartrequest
		// On ecrit à la racine en imposant une limite à 5Mo
		System.out.println("CREATION DE LA MULTIPARTREQUEST");
		MultipartRequest multi = new MultipartRequest(request, ".",
				5 * 1024 * 1024);
		System.out.println("MutlipartRequest crée");
		
		
		System.out.println("CREATION DE L'ENUM PARAMS");
		Enumeration params = multi.getParameterNames();
		while (params.hasMoreElements()) {
			name = (String) params.nextElement();
			value = multi.getParameter(name);
			System.out.println(name + " = " + value);
		}
		System.out.println("CREATION DE L'ENUM FILES");
		Enumeration<File> files = multi.getFileNames();
		System.out.println(files.hasMoreElements());
		while (files.hasMoreElements()) {
			filename = multi.getFilesystemName(name);
			type = multi.getContentType(name);
			f = multi.getFile(name);

			System.out.println("nom du fichier = " + name);
			System.out.println("type du fichier = " + type);
			System.out.println("Longeur du fichier = " + f.length());
		}
		}
		catch (Exception e){
			e.printStackTrace();
			e.getCause();
			
		}
		System.out.println("Fin de l'upload");

	}

}

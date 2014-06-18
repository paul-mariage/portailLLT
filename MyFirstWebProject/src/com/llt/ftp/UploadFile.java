package com.llt.ftp;

import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("-----------DoPost de UploadFile----------");
		
		String value = "";
		String filename = "";
		String type = "";
		File f = null;
			
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		MultipartRequest multi = new MultipartRequest(request,".");
		Enumeration<String> files = multi.getFileNames();
		System.out.println("files has more element : "+files.hasMoreElements());
		
		while (files.hasMoreElements()) {
			String lefichier=files.nextElement();
			filename = multi.getFilesystemName(lefichier);
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
	
	public void ParseFile(File f){
		System.out.println("Parsing du fichier!");
	}

}

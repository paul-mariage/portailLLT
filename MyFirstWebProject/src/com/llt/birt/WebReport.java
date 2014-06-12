package com.llt.birt;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderContext;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IHTMLRenderOption;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.birt.report.model.api.activity.SemanticException;

public class WebReport extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Constructor of the object.
	 */
	private static final String cheminApp = "C:\\Users\\paul.mariage\\git\\portailLLT\\MyFirstWebProject\\WebContent\\";
	// private static final String cheminApp = "WebContent";
	private IReportEngine birtReportEngine = null;
	protected static Logger logger = Logger.getLogger("org.eclipse.birt");

	public WebReport() {
		super();
	}

	/**
	 * Destruction of the servlet.
	 */
	public void destroy() {
		super.destroy();
		BirtEngine.destroyBirtEngine();
	}

	/**
	 * The doGet method of the servlet.
	 * 
	 * 
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		// get report name and launch the engine

		// resp.setContentType( "application/pdf" );
		// resp.setHeader ("Content-Disposition","inline; filename=test.pdf");
		String reportName = req.getParameter("ReportName");
		String FormatSortie = req.getParameter("FormatSortie");
		ServletContext sc = req.getSession().getServletContext();
		
		
		try {
			if ("HTML".equals(FormatSortie)) {
				resp.setContentType("text/html");
				htmlReport(cheminApp, sc, reportName,resp.getOutputStream());
			} else if ("PDF".equals(FormatSortie)) {
				 resp.setContentType( "application/pdf" ); 
				 resp.setHeader ("Content-Disposition","inline; filename=testpdf.pdf");  
				pdfReport(cheminApp, sc, reportName,resp.getOutputStream());
			} else if ("EXCEL".equals(FormatSortie)) {
				resp.setContentType( "application/vnd.ms-excel" ); 
				resp.setHeader ("Content-Disposition","inline; filename=testxls.xls");
				xlsReport(cheminApp, sc, reportName,resp.getOutputStream());
			} else if ("WORD".equals(FormatSortie)) {
				resp.setContentType( "application/msword" );
				resp.setHeader ("Content-Disposition","inline; filename=testdoc.doc");
				docReport(cheminApp, sc, reportName,resp.getOutputStream());
			} else if ("PP".equals(FormatSortie)) {
				resp.setContentType( "application/vnd.ms-powerpoint" ); 
				resp.setHeader ("Content-Disposition","inline; filename=testppt.ppt");
				pptReport(cheminApp, sc, reportName,resp.getOutputStream());
			} else if ("PS".equals(FormatSortie)) {
				resp.setContentType( "application/postscript" ); 
				resp.setHeader ("Content-Disposition","inline; filename=testps.ps");
				psReport(cheminApp, sc, reportName,resp.getOutputStream());
			}
		} catch (SemanticException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * The doPost method of the servlet.
	 * 
	 * 
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML>");
		out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
		out.println("  <BODY>");
		out.println(" POST Not Supported");
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}

	/**
	 * Initialization of the servlet.
	 * 
	 * 
	 * @throws ServletException
	 *             if an error occure
	 */
	public void init() throws ServletException {
		System.out.println("Initialisation de la configuration");
		BirtEngine.initBirtConfig();

	}

	/**
	 * Generates the html report.
	 * @param servletOutputStream 
	 * 
	 * @return forward to the jsp page
	 * @throws EngineException
	 *             when opening report design or reunning the report
	 * @throws SemanticException
	 *             when changing properties of DesignElementHandle
	 * @throws ServletException
	 */

	public void htmlReport(String cheminApp, ServletContext sc,
			String reportName, ServletOutputStream servletOutputStream) throws EngineException, SemanticException,
			ServletException {

		// set output options
		IHTMLRenderOption options = new HTMLRenderOption();
		options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_HTML);
		options.setEmbeddable(true);
		options.setBaseImageURL(cheminApp + "images");
		options.setImageDirectory(cheminApp + "images");
		options.setImageHandler(new HTMLServerImageHandler());
		options.setMasterPageContent(false);
		options.setOutputStream(servletOutputStream);

		System.out.println("Génération du rapport HTML");
		generateReport(options, sc, reportName);

	}

	/**
	 * Generates the PDF report.
	 * @param servletOutputStream 
	 * 
	 * @return PDF file with the report output
	 * @throws EngineException
	 *             when opening report design or reunning the report
	 * @throws SemanticException
	 *             when changing properties of DesignElementHandle
	 * @throws ServletException
	 */
	public void pdfReport(String cheminApp, ServletContext sc, String reportName, ServletOutputStream servletOutputStream)
			throws EngineException, SemanticException, ServletException {

		// set output options
		IPDFRenderOption options = new PDFRenderOption();
		options.setOutputFormat(PDFRenderOption.OUTPUT_FORMAT_PDF);
		options.setOutputStream(servletOutputStream);

		System.out.println("Génération du rapport PDF");
		generateReport(options, sc, reportName);
		
	}

	/**
	 * Generates the Excel report.
	 * @param servletOutputStream 
	 * 
	 * @return Excel file with the report output
	 * @throws EngineException
	 *             when opening report design or reunning the report
	 * @throws SemanticException
	 *             when changing properties of DesignElementHandle
	 * @throws ServletException
	 */
	public void xlsReport(String cheminApp, ServletContext sc, String reportName, ServletOutputStream servletOutputStream)
			throws EngineException, SemanticException, ServletException {

		// set output options
		IRenderOption options = new RenderOption();
		options.setOutputFormat("xls");
		options.setOutputStream(servletOutputStream);

		System.out.println("Génération du rapport Excel");
		generateReport(options, sc, reportName);

	}

	/**
	 * Generates the Word report.
	 * @param servletOutputStream 
	 * 
	 * @return Excel file with the report output
	 * @throws EngineException
	 *             when opening report design or reunning the report
	 * @throws SemanticException
	 *             when changing properties of DesignElementHandle
	 * @throws ServletException
	 */
	public void docReport(String cheminApp, ServletContext sc, String reportName, ServletOutputStream servletOutputStream)
			throws EngineException, SemanticException, ServletException {

		// set output options
		IRenderOption options = new RenderOption();
		options.setOutputFormat("doc");		
		options.setOutputStream(servletOutputStream);

		System.out.println("Génération du rapport WORD");
		generateReport(options, sc, reportName);

	}

	/**
	 * Generates the Powerpoint report.
	 * @param servletOutputStream 
	 * 
	 * @return Excel file with the report output
	 * @throws EngineException
	 *             when opening report design or reunning the report
	 * @throws SemanticException
	 *             when changing properties of DesignElementHandle
	 * @throws ServletException
	 */
	public void pptReport(String cheminApp, ServletContext sc, String reportName, ServletOutputStream servletOutputStream)
			throws EngineException, SemanticException, ServletException {

		// set output options
		IRenderOption options = new RenderOption();
		options.setOutputFormat("ppt");
		options.setOutputStream(servletOutputStream);

		System.out.println("Génération du rapport PPT");
		generateReport(options, sc, reportName);
	}

	/**
	 * Generates the Postscript report.
	 * @param servletOutputStream 
	 * 
	 * @return Excel file with the report output
	 * @throws EngineException
	 *             when opening report design or reunning the report
	 * @throws SemanticException
	 *             when changing properties of DesignElementHandle
	 * @throws ServletException
	 */
	public void psReport(String cheminApp, ServletContext sc, String reportName, ServletOutputStream servletOutputStream)
			throws EngineException, SemanticException, ServletException {

		// set output options
		IRenderOption options = new RenderOption();
		options.setOutputFormat("postscript");
		options.setOutputStream(servletOutputStream);

		System.out.println("Génération du rapport PS");
		generateReport(options, sc, reportName);
	}

	/**
	 * Generates the report output.
	 * 
	 * @return the report output
	 * @throws EngineException
	 *             when opening report design or reunning the report
	 * @throws SemanticException
	 *             when changing properties of DesignElementHandle
	 * @throws ServletException
	 */
	private void generateReport(IRenderOption options, ServletContext sc,
			String reportName) throws EngineException, SemanticException,
			ServletException {

		this.birtReportEngine = BirtEngine.getBirtEngine(sc);

		IReportRunnable design;
		try {
			// Open report design
			// Attention, backslash pour windows seulement, utiliser / lors du
			// lancement
			System.out.println("Génération du design");
			design = birtReportEngine.openReportDesign(cheminApp + "Reports\\"
					+ reportName);
			System.out.println("Design : "+design.getReportName());
			// create task to run and render report
			System.out.println("Génération de la task");
			IRunAndRenderTask task = birtReportEngine
					.createRunAndRenderTask(design);

			task.setRenderOption(options);
			System.out.println("Les options de la task : "+task.getRenderOption().getOptions().toString());

			// run report
			task.run();
			task.close();
			System.out.println("Rapport généré avec succès");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}

	}

}
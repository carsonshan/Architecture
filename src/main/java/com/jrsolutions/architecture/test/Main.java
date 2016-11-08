package com.jrsolutions.architecture.test;

import java.io.File;
import java.io.IOException;

import es.indrasl.web.test.server.ObjectBroker;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	private File currentDir;
	private String urlPath;
	private String urlName;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		upload(request, response);
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		urlPath=request.getContextPath();
		urlName=request.getServletPath();

		String createObject=request.getParameter("createObject");
		if(createObject!=null){
			createServerObject(request);		
		}
		String executeObject=request.getParameter("executeObject");
		if(executeObject!=null){
			executeServerObject(request);		
		}
		String deleteObject=request.getParameter("deleteObject");
		if(deleteObject!=null){
			deleteServerObject(request);		
		}
	}
	

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		upload(req, resp);
	}
	
	
	private void createServerObject(HttpServletRequest request){
		HttpSession session=request.getSession(true);
		ObjectBroker ob= (ObjectBroker)session.getAttribute("OB");
		if(ob==null){
			ob=new ObjectBroker();
			session.setAttribute("OB", ob);
		}
		String className=request.getParameter("ClassName");
		String argumentos=request.getParameter("argsTypes");
		String values=request.getParameter("argsValues");
		String[] typesName=argumentos.split(":");
		int objId=ob.createObject(className, typesName, values);
		// retorna objId
	}
	private void executeServerObject(HttpServletRequest request){
		HttpSession session=request.getSession(true);
		ObjectBroker ob= (ObjectBroker)session.getAttribute("OB");
		if(ob==null){
			ob=new ObjectBroker();
			session.setAttribute("OB", ob);
		}
		String objId=request.getParameter("ObjId");
		String methodName=request.getParameter("method");
		String argumentos=request.getParameter("argsTypes");
		String valuesText=request.getParameter("argsValues");
		String[] values=valuesText.split(":"); // habría que convertir al tipo adecuado
		String[] typesName=argumentos.split(":");
		Object ret=ob.execute(Integer.parseInt(objId), methodName, typesName, values);
		// retorna objId
	}
	private void deleteServerObject(HttpServletRequest request){
		HttpSession session=request.getSession(true);
		ObjectBroker ob= (ObjectBroker)session.getAttribute("OB");
		if(ob==null){
			ob=new ObjectBroker();
			session.setAttribute("OB", ob);
		}
		String objId=request.getParameter("ObjId");
	
		ob.deleteObject(Integer.parseInt(objId));
		// retorna objId
	}

}

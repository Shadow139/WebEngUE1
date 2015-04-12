package at.ac.tuwien.big.we15.lab2.api.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we15.lab2.api.Avatar;
import at.ac.tuwien.big.we15.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we15.lab2.api.impl.JSONQuestionDataProvider;
import at.ac.tuwien.big.we15.lab2.api.impl.ServletJeopardyFactory;

//@WebServlet("/home")
@WebServlet(name = "BigJeopardyServlet", urlPatterns = {"/BigJeopardyServlet"})
public class BigJeopardyServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	//response.getWriter().write("Herro");
    	
    	getSubmit(request,response);
    }
    
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	getSubmit(request,response);
    }
    
    private void getSubmit(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	
    	String sumbitParam = request.getParameter("submit");
    	System.out.println(sumbitParam);

    	
    	if(sumbitParam == null){
    		redirectToHome(response);
    		return;
    	}
 	   	    	
    	switch(sumbitParam){
    	case "waehlen": 
        	response.sendRedirect("question.xhtml");
    		break;
    	case "antworten": 
        	response.sendRedirect("jeopardy.xhtml");
    		break;
    	case "Neues Spiel": 
        	startQuiz(request,response);
    		break;
    	default: 
    		redirectToHome(response);
    		return;
    	}
    }
    
    private void redirectToHome(HttpServletResponse response) throws IOException{
    	response.sendRedirect("login.xhtml");
    }
    
    private void startQuiz(HttpServletRequest request,
            HttpServletResponse response){
    	
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/question.xhtml");

        
    	
    }
    
    private void showCategory(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession(false);
        if(session == null){
        	showHomePage(request,response);
        	return;
        }
        ServletContext context = getServletContext();

        
    }
    
    private void showHomePage(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/login.xhtml");
        dispatcher.forward(request, response);
    }
    
    private void initServletData(){
        ServletContext context = getServletContext();
        
        ServletJeopardyFactory servletFactory = new ServletJeopardyFactory(context);
        QuestionDataProvider provider = servletFactory.createQuestionDataProvider();

    }
    
    private void debugHttp(HttpServletRequest request,
            HttpServletResponse response) throws IOException{
    	System.out.println("test");
    	response.getWriter().write("response: ");
    	response.getWriter().write(response.toString());
    	
    	response.getWriter().write("request: ");
    	response.getWriter().write(request.toString());


    }
}

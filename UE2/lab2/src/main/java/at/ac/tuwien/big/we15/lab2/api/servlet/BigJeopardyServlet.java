package at.ac.tuwien.big.we15.lab2.api.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we15.lab2.api.Avatar;
import at.ac.tuwien.big.we15.lab2.api.Category;
import at.ac.tuwien.big.we15.lab2.api.Game;
import at.ac.tuwien.big.we15.lab2.api.Player;
import at.ac.tuwien.big.we15.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we15.lab2.api.impl.JSONQuestionDataProvider;
import at.ac.tuwien.big.we15.lab2.api.impl.ServletJeopardyFactory;
import at.ac.tuwien.big.we15.lab2.api.impl.SimpleGame;
import at.ac.tuwien.big.we15.lab2.api.impl.SimplePlayer;

//@WebServlet("/home")
@WebServlet(name = "BigJeopardyServlet", urlPatterns = {"/BigJeopardyServlet"})
public class BigJeopardyServlet extends HttpServlet {

	ServletJeopardyFactory servletFactory;
	QuestionDataProvider provider;
	List<Category> categoryList;
	Game game;

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
    	case "Anmelden": 
    		startQuiz(request);
        	response.sendRedirect("jeopardy.jsp");
    		break;
    	case "waehlen": 
    		getSelectedQuestion();
        	response.sendRedirect("question.jsp");
    		break;
    	case "antworten": 
        	response.sendRedirect("jeopardy.jsp");
    		break;
    	case "Neues Spiel": 
        	startQuiz(request);
    		break;
    	default: 
    		redirectToHome(response);
    		return;
    	}
    }
    
    private void getSelectedQuestion() {
		// TODO Auto-generated method stub
		
	}

	private void redirectToHome(HttpServletResponse response) throws IOException{
    	response.sendRedirect("login.jsp");
    }
    
    private void startQuiz(HttpServletRequest request){
    	
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/jeopardy.jsp");
        
        servletFactory = new ServletJeopardyFactory(context);
        provider = servletFactory.createQuestionDataProvider();
        
        categoryList = provider.getCategoryData();
        
        
        Player player1 = new SimplePlayer("Black Widow");
        Player player2 = new SimplePlayer("Deadpool");
        
        game = new SimpleGame(player1, player2);
        
        game.setCategoryList(categoryList);
        
        
        request.getSession().setAttribute("categoryList", categoryList);

        request.getSession().setAttribute("game", game);


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
        RequestDispatcher dispatcher = context.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }


}
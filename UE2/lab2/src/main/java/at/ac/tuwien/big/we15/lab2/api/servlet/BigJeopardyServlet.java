package at.ac.tuwien.big.we15.lab2.api.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import at.ac.tuwien.big.we15.lab2.api.Answer;
import at.ac.tuwien.big.we15.lab2.api.Avatar;
import at.ac.tuwien.big.we15.lab2.api.Category;
import at.ac.tuwien.big.we15.lab2.api.Game;
import at.ac.tuwien.big.we15.lab2.api.Player;
import at.ac.tuwien.big.we15.lab2.api.Question;
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
	int currentQuestionId;

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
    		getSelectedQuestion(request);
        	response.sendRedirect("question.jsp");
    		break;
    	case "antworten": 
    		checkAnswer(request);
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
    
    private void checkAnswer(HttpServletRequest request) {
    	int qid = Integer.parseInt(request.getParameter("selectedQuestionId"));
    	Question question = getQuestionById(qid);
    	
		//boolean correct = true;
		boolean containsAnswer = false;
		
    	String[] answers = request.getParameterValues("answers");
    	List<Answer> correctAnswers = question.getCorrectAnswers();
    	
		for(int i = 0; i < answers.length ;i++){
			containsAnswer = isCorrect(answers[i],correctAnswers);
			
			if(containsAnswer == false){
				break;
			}
		}


		/*for(int i = 0; i < answers.length ;i++){
			containsAnswer = false;
			System.out.println(answers[i]);
	    	for(Answer ans: question.getCorrectAnswers()){
				if(!answers[i].contains(ans.getText() + "")){
					correct = false;
					break;
				}
				if(answers[i].equals(ans.getId()+"")){
					containsAnswer = true;
				}
			}
	    	if(!containsAnswer){
	    		correct = false;
	    		break;
	    	}
		}*/
    	
		System.out.println("Is answer correct? "+containsAnswer);
	}

	private boolean isCorrect(String answer, List<Answer> correctAnswers) {

	System.out.println("----------------------");
	System.out.println(answer);
	System.out.println("correct Answers ->");

		
	for(Answer a: correctAnswers){
		System.out.println(a.getText());

		System.out.println("is it equal?  :  " + a.equals(answer));
		if(a.getText().equals(answer)){
				return true;
		}	
	}
	System.out.println("----------------------");
		return false;
	}

	private void getSelectedQuestion(HttpServletRequest request) {
		// TODO Auto-generated method stub
    	int questionNumber = Integer.parseInt(request.getParameter("question_selection"));
    	request.getSession().setAttribute("selectedQuestion", getQuestionById(questionNumber));
	}
	
	private Question getQuestionById(int questionNumber){
		/*Question question = null;
		int i = 0;
    	for(Category c: categoryList){
    		for(Question q: c.getQuestions()){
    			i++;
    			if(i == questionNumber){
    				currentQuestionId = q.getId();
    				question = q;
    				break;
    			}
    		}
    	}
		return question;*/
		
    	for(Category c: categoryList){
    		for(Question q: c.getQuestions()){
    			if(questionNumber == q.getId()){
    				return q;
    			}    			
    		}
    	}
    	
    	return null;
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

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

import at.ac.tuwien.big.we15.lab2.api.Answer;
import at.ac.tuwien.big.we15.lab2.api.Category;
import at.ac.tuwien.big.we15.lab2.api.Game;
import at.ac.tuwien.big.we15.lab2.api.Player;
import at.ac.tuwien.big.we15.lab2.api.Question;
import at.ac.tuwien.big.we15.lab2.api.QuestionDataProvider;
import at.ac.tuwien.big.we15.lab2.api.impl.RandomNumberGenerator;
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
    	System.out.println("submit:                      " + sumbitParam);

    	
    	if(sumbitParam == null){
    		redirectToHome(response);
    		return;
    	}
 	   	    	
    	switch(sumbitParam){
    	case "Anmelden": 
    		//game = (Game) request.getAttribute("game");
    		request.getSession().setAttribute("player1info", null);
			request.getSession().setAttribute("player2info", null);
			request.getSession().setAttribute("player2Choice", null);
    		startQuiz(request);
        	response.sendRedirect("jeopardy.jsp");
    		break;
    	case "waehlen": 
    		game = (Game) request.getSession().getAttribute("game");
    		getSelectedQuestion(request);
        	response.sendRedirect("question.jsp");
        	if (game.getPlayer1().getWinnings()<=game.getPlayer2().getWinnings()) {
    			doShit(request);
    		}
    		break;
    	case "antworten":  
    		game = (Game) request.getSession().getAttribute("game");
    		processAnswer(request,response);
    		String redirect = "jeopardy.jsp";
    		
    		if(game.getQuestionsAsked() == 10)
            	redirect = "winner.jsp";
    		
        	response.sendRedirect(redirect);
    		break;
    	case "Neues Spiel": 
    		
        	startQuiz(request);
        	response.sendRedirect("jeopardy.jsp");
    		break;
    	default: 
    		game = (Game) request.getSession().getAttribute("game");
    		redirectToHome(response);
        	response.sendRedirect("jeopardy.jsp");
			request.getSession().setAttribute("player1info", null);
			request.getSession().setAttribute("player2info", null);
			request.getSession().setAttribute("player2Choice", null);
    		return;
    	}
    }
    
    private void processAnswer(HttpServletRequest request,HttpServletResponse response) throws IOException {
    	int qid = Integer.parseInt(request.getParameter("selectedQuestionId"));
    	Question question = getQuestionById(qid);
    	
		if(checkAnswer(request,question)){
			game.getCurrentPlayer().increaseWinnings(question.getValue());
			request.getSession().setAttribute("player1info", question.getValue());
		}else{
			game.getCurrentPlayer().decreaseWinnings(question.getValue()/2);
			request.getSession().setAttribute("player1info", -1*question.getValue()/2);
		}
		
		game.increaseQuestionsAskedCount();
		System.out.println("DEACTIVATE PLS");
		System.out.println(question.getText());
		System.out.println(question.isActive());
		question.setActive(false);
		System.out.println("DEACTIVATE DID IT WORK");

		if(game.getPlayer1().getWinnings()>game.getPlayer2().getWinnings()){
	    	doShit(request);
		}
	}
    
    private void doShit(HttpServletRequest request) {
    	List<Question> questions = activeQuestions();
    	int q = RandomNumberGenerator.getRandIntBetween(0,questions.size());
    	questions.get(q).setActive(false);
    	int l = RandomNumberGenerator.getRandIntBetween(0,2);
    	int answer;
    	if (l == 0) {
    		answer = -questions.get(q).getValue()/2;
    	} else { 
    		answer = questions.get(q).getValue();
    	} 
    	game.getPlayer2().increaseWinnings(answer);
    	request.getSession().setAttribute("player2info", answer);
		request.getSession().setAttribute("player2Choice", questions.get(q).getCategory().getName());
    }
    
    private List<Question> activeQuestions() {
    	List<Category> categories = game.getCategoryList();
    	List<Question> questions = new ArrayList<Question>();
    	for (Category c : categories) {
    		for (Question q : c.getQuestions()) {
    			if (q.isActive()) {
    				questions.add(q);
    			}
    		}
    	}
    	return questions;
    }

	private boolean checkAnswer(HttpServletRequest request,Question question) {
    	//int qid = Integer.parseInt(request.getParameter("selectedQuestionId"));
    	//Question question = getQuestionById(qid);
    	
		//boolean correct = true;
		boolean containsAnswer = false;
		
    	String[] answers = request.getParameterValues("answers");
    	if(answers == null){
    		return false;
    	}
    	List<Answer> correctAnswers = question.getCorrectAnswers();
    	
		for(int i = 0; i < answers.length ;i++){
			containsAnswer = isCorrect(answers[i],correctAnswers);
			System.out.println("<<<<< " + containsAnswer);

			if(containsAnswer == false){
				return false;
			}
		}
    	
		System.out.println("Is answer correct? " + containsAnswer);
		
		return containsAnswer;
	}

	private boolean isCorrect(String answer, List<Answer> correctAnswers) {

	System.out.println("----------------------");
	System.out.println(answer);
	System.out.println("<-------------------->");

		
	for(Answer a: correctAnswers){
		System.out.println(a.getText() + " equal " + answer + "     :     " + a.getText().equals(answer));
		if(a.getText().equals(answer)){
			System.out.println("<---------------------->");
				return true;  // freagt nur eine instanz ab nicht alle muss gefixt werden bsp SSD frage 2  Was ist DOM?
		}	
	}
		return false;
	}

	private void getSelectedQuestion(HttpServletRequest request) {
		// TODO Auto-generated method stub
    	int questionNumber = Integer.parseInt(request.getParameter("question_selection"));
    	request.getSession().setAttribute("selectedQuestion", getQuestionById(questionNumber));
	}
	
	private Question getQuestionById(int questionNumber){

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
        
                
        Player player1 = new SimplePlayer();
        Player player2 = new SimplePlayer();
        
        game = new SimpleGame(player1, player2);
        
        game.setCategoryList(categoryList);
        
        request.getSession().setAttribute("categoryList", categoryList);

        request.getSession().setAttribute("game", game);

    }

    
    private void showHomePage(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException{
        ServletContext context = getServletContext();
        RequestDispatcher dispatcher = context.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
    }


}

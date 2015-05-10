package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import play.cache.Cache;
import at.ac.tuwien.big.we15.lab2.api.JeopardyGame;
import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.db.jpa.JPA;
import play.mvc.*;
import views.html.*;
import models.*;


public class Application extends Controller {
    @play.db.jpa.Transactional
    public static Result index() {
		return redirect(routes.Application.registration());

    }

    // ---------- Registration ----------
    
    public static Result registration(){
    	Form<User> userForm = Form.form(User.class);
    	return ok(registration.render(userForm));
    	
    }
    @play.db.jpa.Transactional
    public static Result submitRegistration(){
    	Form<User> userForm = Form.form(User.class).bindFromRequest();
    	User user = null;
    	try{
    		user = userForm.get();
    	}catch(Exception e){
    		return redirect(routes.Application.registration());
    	}
    	if(user != null){
    		JPA.em().persist(user);
    	}
		return redirect(routes.Application.submitLogin());
    }
    
    // ---------- Login ----------
    
    public static Result login() {
    	Form<User> userForm = Form.form(User.class);
    	return ok(authentication.render(userForm));
    }
    
    @play.db.jpa.Transactional
    public static Result submitLogin() {
    	Form<User> userForm = Form.form(User.class).bindFromRequest();
    	User user = userForm.get();
    	Query query =   JPA.em().createQuery("SELECT x FROM User x WHERE x.password = :pwd AND x.username = :usr ")
    	.setParameter("pwd",user.getPassword())
    	.setParameter("usr",user.getUsername());
    	List<User> users = query.getResultList();
    	if (users.size() == 1) {
    		GameController gc = new GameController();
    		user = users.get(0);
    		JeopardyGame jgame = gc.startGame(user.getUsername(), user.getAvatar());
    		Game game = new Game(jgame);
    		Cache.set("game", game);
    		//return ok(jeopardy.render(game));
    		return redirect(routes.Application.jeopardy());

    	}
    	return redirect(routes.Application.login());
    }
    
    // ---------- Jeopary ----------
    
    public static Result jeopardy() {
    	Form<Quiz> qidForm = Form.form(Quiz.class);
    	Game game = (Game) Cache.get("game");

    	return ok(jeopardy.render(game, qidForm));
    }
    
    public static Result submitJeopardy() {
    	Form<Quiz> qidForm = Form.form(Quiz.class);
    	Quiz quiz = qidForm.bindFromRequest().get();
    	
		DynamicForm form = Form.form().bindFromRequest();
		String playerAnswer = form.get("question_selection");
		int choiceId = Integer.valueOf(playerAnswer);

    	Game game = (Game) Cache.get("game");
    	
    	game.getGame().chooseHumanQuestion(choiceId);

		Cache.set("game", game);

    	return redirect(routes.Application.question());
    }
    //-------------------- Answer -------------------------
    
    public static Result question() {
    	Form<Answer> answerForm = Form.form(Answer.class);
    	Game game = (Game) Cache.get("game");

    	return ok(question.render(game,answerForm));
    }
    
    public static Result submitQuestion() {
    	Form<Answer> answerForm = Form.form(Answer.class);
    	Answer answer = answerForm.bindFromRequest().get();
    	
    	Game game = (Game) Cache.get("game");
    	
    	game.getGame().answerHumanQuestion(answer.getAnswers());
    	
		Cache.set("game", game);

    	return redirect(routes.Application.jeopardy());
    }
    //úngefähr 10 Bindestriche:-------------- Debugging -------------------
    @play.db.jpa.Transactional
    public static Result newUser(){
    	Form<Register> registerForm = Form.form(Register.class);
    	return ok(newUser.render(registerForm));
    }
    @play.db.jpa.Transactional
    public static Result createUser(){
    	Form<Register> registerForm = Form.form(Register.class).bindFromRequest();
    	Register register = registerForm.get();

		JPA.em().persist(register);
		return redirect(routes.Application.listUsers());
    }
    
    @play.db.jpa.Transactional
    public static Result listRegister(){
    	Query query =  JPA.em().createQuery("From Register");
    	List<Register> userList = query.getResultList();
    	return ok(debugUsers.render(userList));
    }

    @play.db.jpa.Transactional
    public static Result listUsers(){
    	Query query =  JPA.em().createQuery("From User");
    	List<User> userList = query.getResultList();
    	return ok(listUsers.render(userList));
    }


}

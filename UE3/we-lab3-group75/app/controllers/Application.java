package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import play.cache.Cache;
import at.ac.tuwien.big.we15.lab2.api.JeopardyGame;
import play.*;
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
    		return ok(jeopardy.render(game));
    	}
    	return redirect(routes.Application.login());
    }
    
    // ---------- Jeopary ----------
    
    public static Result jeoprardy() {
    	return null;
    }
    
    public static Result submitJeoprardy() {
    	Form<Quiz> gameForm = Form.form(Quiz.class).bindFromRequest();
    	Quiz quiz = gameForm.get();
    	
    	Game game = (Game) Cache.get("game");
    	
    	game.getGame().chooseHumanQuestion(5);
		Cache.set("game", game);

    	return ok(question.render(game));
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


    public static Result question(){
    	Game game = (Game) Cache.get("game");

    	return ok(question.render(game));
    	
    }
    
    public static Result submitJeopardy(){
    	return ok();
    }
}

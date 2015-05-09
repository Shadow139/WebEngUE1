package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

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
    	User user = userForm.get();
    	JPA.em().persist(user);
		return redirect(routes.Application.listUsers());
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
    	Query query =   JPA.em().createQuery("SELECT * FROM User WHERE ?1 = password && ?2 = username");
    	query.setParameter(1,user.getPassword());
    	query.setParameter(2,user.getUsername());
    	List<User> users = query.getResultList();
    	if (users.size() == 1) {
    		return redirect(routes.Application.jeoprardy());
    	}
    	return redirect(routes.Application.login());
    }
    
    // ---------- Jeopary ----------
    
    public static Result jeoprardy() {
    	return null;
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
    public static Result jeopardy(){
    	Form<User> userForm = Form.form(User.class);
    	return ok(jeopardy.render());
    	
    }
}

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
        return ok();
    }
    
    
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
    public static Result listUsers(){
    	Query query =  JPA.em().createQuery("From Register");
    	List<Register> userList = query.getResultList();
    	return ok(debugUsers.render(userList));
    }

    public static Result jeopardy(){
    	Form<User> userForm = Form.form(User.class);
    	return ok(jeopardy.render());
    	
    }
}

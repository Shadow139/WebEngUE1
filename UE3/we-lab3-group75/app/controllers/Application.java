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
        return ok(registration.render());
    }
    @play.db.jpa.Transactional
    public static Result register(){
    	Form<Register> registerForm = Form.form(Register.class);
    	return ok(newUser.render(registerForm));
    }
    @play.db.jpa.Transactional
    public static Result registerUser(){
    	Form<Register> registerForm = Form.form(Register.class).bindFromRequest();
    	Register register = registerForm.get();

		JPA.em().persist(register);
		return redirect(routes.Application.listUsers());
    }
    
    @play.db.jpa.Transactional
    public static Result listUsers(){
    	Query query =  JPA.em().createQuery("SELECT * From Register");
    	List<User> userList = query.getResultList();
    	return ok(debugUsers.render(userList));
    }

}

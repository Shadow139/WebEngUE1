package controllers;

import java.util.ArrayList;
import java.util.List;

import play.*;
import play.data.Form;
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

    	
		return redirect(routes.Application.listUsers());
    }
    @play.db.jpa.Transactional
    public static Result listUsers(){
    	List<Register> userList = new ArrayList<Register>();
    	return ok(debugUsers.render(userList));
    }

}

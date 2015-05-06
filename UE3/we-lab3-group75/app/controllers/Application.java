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

    public static Result register(){
    	Form<Register> registerForm = Form.form(Register.class);
    	return ok(newUser.render(registerForm));
    }
    
    public static Result registerUser(){
    	Form<Register> registerForm = Form.form(Register.class).bindFromRequest();
    	Register register = registerForm.get();

    	
		return redirect(routes.Application.listUsers());
    }
    
    public static Result listUsers(){
    	List<User> userList = new ArrayList<User>();
    	return ok(debugUsers.render(userList));
    }

}

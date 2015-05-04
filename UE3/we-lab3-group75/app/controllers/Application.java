package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
    @play.db.jpa.Transactional
    public static Result index() {
        return ok(views.html.index.render("Herro"));
    }

}

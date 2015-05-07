package models;

import java.util.Date;

import javax.persistence.Entity;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
@Entity
public class Register {	
	public String firstname;
	
	public String lastname;
	
	public Date birthdate;

	public String gender;
	
	//@Required
	@MinLength(4)
	@MaxLength(8)
	public String username;
	
	//@Required
	@MinLength(4)
	@MaxLength(8)
	public String password;
	
	public String validate() {  
	      if( birthdate != null && birthdate.after(new Date()) ){
	    	  return "error"; 
	      }

	      return null;
    }

}

package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
@Entity
public class Register {	
	@Id
	private int id;
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	

}

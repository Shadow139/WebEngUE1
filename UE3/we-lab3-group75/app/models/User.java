package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;

import play.db.jpa.JPA;

@Entity
public class User {
	
	@Id
	@Column(unique=true)
	private String name;
	
	@Column(length = 255)
	private String password; 
	
	@Column(length = 50)
	private String firstname;
	
	@Column(length = 50)
	private String lastname;
	
	@Column(length = 50)
	private Date birthdate;

	@Column(length = 30)
	private String gender;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name= name;
	}
	
	public String getId() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public static User findUserByName(String username) {
		EntityManager em = JPA.em();
		return em.find(User.class, username);
	}
}
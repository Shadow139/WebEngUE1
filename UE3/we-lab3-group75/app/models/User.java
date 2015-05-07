package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;

import play.db.jpa.JPA;

@Entity
public class User {

	@Id
	@Column(unique = true)
	private String username;

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getId() {
		return username;
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

	public static User findUserByUsername(String username) {
		EntityManager em = JPA.em();
		return em.find(User.class, username);
	}

	public static List<User> findAllUser() {
		Query query = JPA.em().createQuery("From User");
		List<User> userList = query.getResultList();
		return userList;
	}
}
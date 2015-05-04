package assets;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User{

	@Id
	@GeneratedValue
	private long id;
	
	private String username;
	
	private String password;
	
	private String firstname;
	
	private String lastname;
	
	private Gender gender;
	
	private String birthday;
	
	//public static Finder<Long,User> find = new Finder<Long, User>(Long.class, User.class);
}

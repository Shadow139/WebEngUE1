package models;

import play.data.validation.Constraints.Required;

public class LoginInformation {

	@Required
	public String username;
	
	@Required
	public String password;
	
	public String validate() {

        if ( !authenticate(username, password) ) {
            return "Invalid email or password";
        }
        
        // If passes, return null.
        return null;
    }

	private boolean authenticate(String username, String password) {
		User user = User.findUserByName(username);
		
		if( user == null){
			return false;
		}
		
		try {
			//check password here
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}

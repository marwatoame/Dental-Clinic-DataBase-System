package DoctorUser;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class doctoruser {
	
	 private final StringProperty username;
	 private final StringProperty password;
	 
	 public doctoruser (String username, String password) {
		 this.username = new SimpleStringProperty(username);
		 this.password = new SimpleStringProperty(password);
   }

   public String getUsername() {
       return username.get();
   }

   public String getPassword() {
       return password.get();
   }
   
   public void setUsername(String value) {
       username.set(value);;
   }

   public void setPassword(String value) {
       password.set(value);;
   }
   
   public StringProperty usernameProberty(){
	   return username;
   }
   
   public StringProperty passwordProberty(){
	   return password;
   }

}

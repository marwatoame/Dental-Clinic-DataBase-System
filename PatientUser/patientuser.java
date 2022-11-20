package PatientUser;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class patientuser {
	
	 private final StringProperty id;
	 private final StringProperty password;
	 
	 public patientuser () {
		 this.id = idProberty();
		 this.password = passwordProberty();
   }
	 
	 public patientuser (String id, String password) {
		 this.id = new SimpleStringProperty(id);
		 this.password = new SimpleStringProperty(password);
   }

   public String getID() {
       return id.get();
   }

   public String getPassword() {
       return password.get();
   }
   
   public void setID(String value) {
       id.set(value);
   }

   public void setPassword(String value) {
       password.set(value);
   }
   
   public StringProperty idProberty(){
	   return id;
   }
   
   public StringProperty passwordProberty(){
	   return password;
   }

}

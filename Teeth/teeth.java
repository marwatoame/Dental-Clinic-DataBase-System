package Teeth;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class teeth {
	
    String ID;
    String Name;
    String toothNumber;
	String condition;
	String treatment;
	String Cost;
	
	public teeth (String id,String name,String toothNumber, String condition, String treatment, String Cost) {
		 this.ID=id;
		 this.Name=name;
		 this.toothNumber = toothNumber;
		 this.condition =condition;
		 this.treatment = treatment;
		 this.Cost = Cost;

   }

		public String getID() {
	       return ID;
		}
		public String getName() {
	       return Name;
	   }
	   public String getToothNumber() {
	       return toothNumber;
	   }
	   
	   public String getCondition() {
			return condition;
		}
	   
	   public String getTreatment() {
	       return treatment;
	   }
	   
	   public String getCost() {
	       return Cost;
	   }
	   
	   public void setID(String value) {
		   ID=value;
	   }
	   
	   public void setName(String value) {
		   Name=value;
	   }
	   

	   public void setToothNumber(String value) {
		   toothNumber=value;
	   }
	   
	   public void setCondition(String value) {
		   condition=value;
	   }
	   
	   public void setTreatment(String value) {
		   treatment=value;
	   }
	   
	   
	   public void setCost(String value) {
	        Cost=value;
	   }
	   
	 
	
}


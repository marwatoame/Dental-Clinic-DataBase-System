package DentalLabs;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DentalLabs {

	private final StringProperty Name;
	private final StringProperty Contact_Num;
	private final StringProperty Address;
	private final StringProperty DateSent;
	private final StringProperty ExpectedDuration;
	private final StringProperty Cost;
	
	public DentalLabs (String Name, String Contact_Num, String Address, String DateSent, String ExpectedDuration, String Cost) {
		 this.Name = new SimpleStringProperty(Name);
		 this.Contact_Num = new SimpleStringProperty(Contact_Num);
		 this.Address = new SimpleStringProperty(Address);
		 this.DateSent = new SimpleStringProperty(DateSent);
		 this.ExpectedDuration = new SimpleStringProperty(ExpectedDuration);
		 this.Cost = new SimpleStringProperty(Cost);

   }

	   public String getName() {
	       return Name.get();
	   }
	   
	   public String getContact_Num() {
			return Contact_Num.get();
		}
	   
	   public String getAddress() {
	       return Address.get();
	   }
	   
	   public String getDateSent() {
	       return DateSent.get();
	   }
	   
	   public String getExpectedDuration() {
	       return ExpectedDuration.get();
	   }
	   
	   public String getCost() {
	       return Cost.get();
	   }

	   public void setName(String value) {
		   Name.set(value);;
	   }
	   
	   public void setContact_Num(String value) {
		   Contact_Num.set(value);
	   }
	   
	   public void setAddress(String value) {
		   Address.set(value);
	   }
	   
	   public void setDateSent(String value) {
	        DateSent.set(value);
	   }
	   
	   public void setExpectedDuration(String value) {
	        ExpectedDuration.set(value);
	   }
	   
	   public void setCost(String value) {
	        Cost.set(value);
	   }
	      
	   public StringProperty NameProberty(){
		   return Name;
	   }
	   
	   public StringProperty Contact_NumProberty(){
		   return Contact_Num;
	   }
	   
	   public StringProperty AddressProberty(){
		   return Address;
	   }
	   
	   public StringProperty DateSentProberty(){
		   return DateSent;
	   }
	   
	   public StringProperty ExpectedDurationProberty(){
		   return ExpectedDuration;
	   }
	   
	   public StringProperty CostProberty(){
		   return Cost;
	   }
	   
	   @Override
	   public String toString() {
	   	return "DentalLabs [Name=" + getName() + "Contact Number=" + getContact_Num() + "Address=" + getAddress() + "Date Sent=" + getDateSent() + "Expected Duration=" + getExpectedDuration() + "Cost =" + getCost() + "]";
	   }
}


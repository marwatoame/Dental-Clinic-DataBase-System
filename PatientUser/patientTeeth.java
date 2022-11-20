package PatientUser;

public class patientTeeth {

	String toothNumber;
	String condition;
	String treatment;
	String Cost;
	
	public patientTeeth (String toothNumber, String condition, String treatment, String Cost) {

		 this.toothNumber = toothNumber;
		 this.condition =condition;
		 this.treatment = treatment;
		 this.Cost = Cost;

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
	   

	   public void setToothNumber(String value) {
		   toothNumber=value;
	   }
	   
	   public void setCondition(String value) {
		   condition=value;
	   }
	   
	   public void settreatment(String value) {
		   treatment=value;
	   }
	   
	   
	   public void setCost(String value) {
	        Cost=value;
	   }
	   

	
}


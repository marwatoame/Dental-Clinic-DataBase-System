package PatientUser;

public class PatientAppointment {
	 String app_date;
	 String timing;
	 String doctor;
	 String specialist;
	 
	 public PatientAppointment (String app_date, String timing,String doctor, String specialist) {
		 this.app_date = app_date;
		 this.timing =timing;
		 this.doctor=doctor;
		 this.specialist=specialist;
  }


  public String getApp_date() {
      return app_date;
  }

  public String getTiming() {
      return timing;
  }
  public String getDoctor() {
      return doctor;
  }

  public String getSpecialist() {
      return specialist;
  }
  
  public void setApp_date(String value) {
	  this.app_date=value;
  }

  public void setTiming(String value) {
      this.timing=value;
  }
  
  public void setDoctor(String value) {
	  this.doctor=value;
  }

  public void setSpecialist(String value) {
      this.specialist=value;
  }

}

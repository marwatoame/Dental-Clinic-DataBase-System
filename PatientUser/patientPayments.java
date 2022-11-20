package PatientUser;

public class patientPayments {
	String value;
	 String currency;
	 String method;
	 String date;
	 
	 public patientPayments (String value, String currency,String method, String date) {
		 this.value = value;
		 this.currency =currency;
		 this.method=method;
		 this.date=date;
 }


 public String getValue() {
     return value;
 }

 public String getCurrency() {
     return currency;
 }
 public String getMethod() {
     return method;
 }

 public String getDate() {
     return date;
 }
 
 public void setValue(String value) {
	  this.value=value;
 }

 public void setCurrency(String value) {
     this.currency=value;
 }
 
 public void setMethod(String value) {
	  this.method=value;
 }

 public void setDate(String value) {
     this.date=value;
 }
	
}

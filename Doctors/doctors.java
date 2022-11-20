package Doctors;

public class doctors {
	 String idNumber;
	 String dname;
	 String speciality;
	 String salary;
	 String phoneNumber;

	 
   public doctors (String idnum, String dname, String speciality, String salary, String phoneNumber) {
		 this.dname = dname;
		 this.idNumber = idnum;
         this.speciality=speciality;
         this.salary= salary;
         this.phoneNumber= phoneNumber;
   }

   
   
   public String getIdNumber() {
	return idNumber;
   }  

   public String getDname() {
	return dname;
   }

   public String getSpeciality() {
	return speciality;
   }

   public String getSalary() {
	return salary;
   }

   public String getPhoneNumber() {
	return phoneNumber;
   }

   public void setSalary(String value) {
	   this.salary=value;
   }

   public void setDname(String value) {
	  this.dname=value;
   }

   public void setSpeciality(String value) {
	   this.speciality=value;
   }
   
   public void setIdNumber(String value) {
	   this.idNumber=value;
   }

   public void setPhoneNumber(String value) {
	   this.phoneNumber=value;
   }   
}

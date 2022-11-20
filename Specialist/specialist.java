package Specialist;


public class specialist {
	 String idnum;
	 String sname;
	 String phone;
	 String speciality;
	 String salary;


	 
  public specialist (String idnum, String sname,String phone, String speciality, String salary) {
		 this.idnum = idnum;
		 this.sname = sname;
		 this.phone=phone;
         this.speciality=speciality;
         this.salary= salary;
         
  }

  public String getIdnum() {
	return idnum;
  }

  public String getSname() {
	return sname;
  }
  
  public String getPhone() {
		return phone;
	  }

  public String getSpeciality() {
	return speciality;
  }

  public String getSalary() {
	return salary;
  }
  
  public void setIdnum(String value) {
	   this.idnum=value;
  }

  public void setSname(String value) {
	  this.sname=value;
  }
  
  public void setPhone(String value) {
	  this.phone=value;
  }

  public void setSpeciality(String value) {
	  this.speciality=value;
  }

  public void setSalary(String value) {
	  this.salary=value;
  }
  
  }
package Appointment;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment  {

	   String Pname;

	   String apptime;
	   String appdate;
	   String PID;
	   String DID;
	   String SID;
	   
	 
	

// Constructor
		public Appointment (  String PID, String Pname, String time,String date,String DID, String SID) {
			this.Pname=Pname;

		   this.appdate=date;
		   this.apptime=time;
		   this.PID=PID;
		   this.DID=DID;
		   this.SID=SID;

		 }




		public String getDID() {
			return DID;
		}




		public void setDID(String dID) {
			DID = dID;
		}




		public String getSID() {
			return SID;
		}




		public void setSID(String sID) {
			SID = sID;
		}




		public String getPID() {
			return PID;
		}




		public void setPID(String pID) {
			PID = pID;
		}




		public String getPname() {
			return Pname;
		}




		public void setPname(String pname) {
			Pname = pname;
		}







		public String getApptime() {
			return apptime;
		}




		public void setApptime(String apptime) {
			this.apptime = apptime;
		}




		public String getAppdate() {
			return appdate;
		}




		public void setAppdate(String appdate) {
			this.appdate = appdate;
		}




	
		
}
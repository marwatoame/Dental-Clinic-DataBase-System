package patient;

public class patients {
     
	String ID;
	 String  name;
     String contact;
	 String age;
     String  record;
     String password;
	

// Constructor
		public patients ( String ID,String name,String contact, String age, String record,String password) {
			 this.ID=ID;
		     this.name = name;
			 this.contact= contact;
			 this.age = age;
			 this.record=record;
			 this.password=password;
			

		 }


		public String getID() {
			return ID;
		}


		public void setID(String iD) {
			ID = iD;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public String getContact() {
			return contact;
		}


		public void setContact(String contact) {
			this.contact = contact;
		}


		public String getAge() {
			return age;
		}


		public void setAge(String age) {
			this.age = age;
		}

		public String getRecord() {
			return record;
		}


		public void setRecord(String record) {
			this.record = record;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}
		
		
		

			

		
	
}

	 
	
	
package payment;


public class payment {
String name;
String ID;
String value;
String currency;
String method;
String date;
String Nisvalue;


//Constructor
	public payment ( String ID, String name,String value, String currency,String Nisvalue,String date, String method) {
		this.name=name;
		this.ID=ID;
		this.method=method;
		this.currency=currency;
		this.value=value;
		this.date=date;
		this.Nisvalue=Nisvalue;

	 }



	public String getNisvalue() {
		return Nisvalue;
	}



	public void setNisvalue(String nisvalue) {
		Nisvalue = nisvalue;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getID() {
		return ID;
	}



	public void setID(String iD) {
		ID = iD;
	}



	public String getValue() {
		return value;
	}



	public void setValue(String value) {
		this.value = value;
	}



	public String getCurrency() {
		return currency;
	}



	public void setCurrency(String currency) {
		this.currency = currency;
	}



	public String getMethod() {
		return method;
	}



	public void setMethod(String method) {
		this.method = method;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}


	
}
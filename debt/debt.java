package debt;


public class debt {
String name;
String ID;
String value;
String currency;
String method;
String date;



//Constructor
	public debt ( String ID, String name,String value, String currency, String method,String date) {
		this.name=name;
		this.ID=ID;
		this.method=method;
		this.currency=currency;
		this.value=value;
		this.date=date;

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
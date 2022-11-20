package report;




public class report {
String[] Income = new String[12];
String[] Expenses = new String[12];



//Constructor
	public report ( String i[] , String e[] ) {
 
		System.arraycopy(Income, 0, i, 0, i.length);
		System.arraycopy(Expenses, 0, e, 0, e.length);

	 }



	public String[] getIncome() {
		return Income;
	}



	public void setIncome(String[] income) {
		Income = income;
	}



	public String[] getExpenses() {
		return Expenses;
	}



	public void setExpenses(String[] expenses) {
		Expenses = expenses;
	}





	
}
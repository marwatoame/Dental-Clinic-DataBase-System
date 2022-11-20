package report;

import javafx.application.Application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import payment.dbconnect;
import payment.payment;  

public class controller implements Initializable {
	String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
   
	@FXML
	Button go = new Button();
	@FXML
	Button go2 = new Button();
	@FXML
	TextField text = new TextField(); 
	@FXML
     CategoryAxis xAxis = new CategoryAxis();
    @FXML
     NumberAxis yAxis = new NumberAxis();
   @FXML
     BarChart<String,Number> bc =  new BarChart<String,Number>(xAxis,yAxis);
   @FXML
    XYChart.Series inc = new XYChart.Series();
   @FXML
   XYChart.Series exp = new XYChart.Series();
   @FXML
   Text report = new Text();
   @FXML
	Label income2 = new Label();
	@FXML
   Label expenses2= new Label();
	@FXML
	Label profit2 = new Label();

	String[] Income = {"0","0","0","0","0","0","0","0","0","0","0","0"};	
	String[] Expenses = {"0","0","0","0","0","0","0","0","0","0","0","0"};
	String in;
	String out;
	double prof;
	

	
 //Intialize observable list to hold database data
   private ObservableList<report> data;
   private dbconnect dc;  
   @Override
   public void initialize(URL url, ResourceBundle rb) {

   dc= new dbconnect();
	for(int i=0; i<12;i++) {
		if(Income[i]==null)
		Income[i]="0";
		if(Expenses[i]==null)
		Expenses[i]="0";
	
}
	

	 inc.getData().add(new XYChart.Data(months[0], Integer.parseInt(Income[0])));
	 inc.getData().add(new XYChart.Data(months[1], Integer.parseInt(Income[1])));
	 inc.getData().add(new XYChart.Data(months[2], Integer.parseInt(Income[2])));
	 inc.getData().add(new XYChart.Data(months[3], Integer.parseInt(Income[3])));
	 inc.getData().add(new XYChart.Data(months[4], Integer.parseInt(Income[4])));
	 inc.getData().add(new XYChart.Data(months[5], Integer.parseInt(Income[5])));
	 inc.getData().add(new XYChart.Data(months[6], Integer.parseInt(Income[6])));
	 inc.getData().add(new XYChart.Data(months[7], Integer.parseInt(Income[7])));
	 inc.getData().add(new XYChart.Data(months[8], Integer.parseInt(Income[8])));
	 inc.getData().add(new XYChart.Data(months[9], Integer.parseInt(Income[9])));
	 inc.getData().add(new XYChart.Data(months[10],Integer.parseInt(Income[10])));
	 inc.getData().add(new XYChart.Data(months[11],Integer.parseInt( Income[11])));

	 exp.getData().add(new XYChart.Data(months[0],Integer.parseInt( Expenses[0])));
	 exp.getData().add(new XYChart.Data(months[1], Integer.parseInt(Expenses[1])));
	 exp.getData().add(new XYChart.Data(months[2],Integer.parseInt( Expenses[2])));
	 exp.getData().add(new XYChart.Data(months[3], Integer.parseInt(Expenses[3])));
	 exp.getData().add(new XYChart.Data(months[4],Integer.parseInt( Expenses[4])));
	 exp.getData().add(new XYChart.Data(months[5],Integer.parseInt( Expenses[5])));
	 exp.getData().add(new XYChart.Data(months[6],Integer.parseInt( Expenses[6])));
	 exp.getData().add(new XYChart.Data(months[7],Integer.parseInt( Expenses[7])));
	 exp.getData().add(new XYChart.Data(months[8],Integer.parseInt( Expenses[8])));
	 exp.getData().add(new XYChart.Data(months[9],Integer.parseInt( Expenses[9])));
	 exp.getData().add(new XYChart.Data(months[10],Integer.parseInt( Expenses[10])));
	 exp.getData().add(new XYChart.Data(months[11],Integer.parseInt( Expenses[11])));
     bc.getData().addAll(inc,exp);
  inc.setName("Income");
  exp.setName("Expenses");
   }

   	 
 
@FXML
public void loadDataFromDataBase(String year) {
		
	for(int i=0; i<12;i++) {
		
		Income[i]="0";
		Expenses[i]="0";
	
}

	try {

Connection conn = dc.Connect();
data = FXCollections.observableArrayList();
ResultSet rs = conn.createStatement().executeQuery("select month(date_of_payment),sum(value_In_NIS) from patient_pays_payment  where year(date_of_payment)='"+year+"'group by month(date_of_payment) order by month(date_of_payment)asc;");
while ( rs.next()) {
Income[Integer.parseInt(rs.getString(1))-1]= rs.getString(2);
}

ResultSet rs2 = conn.createStatement().executeQuery("select `month(date_of_payment)` as month,sum(value_In_NIS) as total1 from(SELECT month(date_of_payment),value_In_NIS FROM debts_for_specialist  where year(date_of_payment)='" +year +"' UNION ALL SELECT month(date_of_payment),value_In_NIS FROM debts_for_services where  year(date_of_payment)='"+year+"' ) as total2 group by `month(date_of_payment)` order by `month(date_of_payment)` asc;");
while ( rs2.next()) {
Expenses[Integer.parseInt(rs2.getString(1))-1]= rs2.getString(2);

}

}
		catch(SQLException | ClassNotFoundException ex) {
	    System.err.println("Error"+ex);
	}
	
	for(int i=0; i<12;i++) {
		if(Income[i]==null)
		Income[i]="0";
		if(Expenses[i]==null)
		Expenses[i]="0";
	
}
	 inc.getData().add(new XYChart.Data(months[0], Integer.parseInt(Income[0])));
	 inc.getData().add(new XYChart.Data(months[1], Integer.parseInt(Income[1])));
	 inc.getData().add(new XYChart.Data(months[2], Integer.parseInt(Income[2])));
	 inc.getData().add(new XYChart.Data(months[3], Integer.parseInt(Income[3])));
	 inc.getData().add(new XYChart.Data(months[4], Integer.parseInt(Income[4])));
	 inc.getData().add(new XYChart.Data(months[5], Integer.parseInt(Income[5])));
	 inc.getData().add(new XYChart.Data(months[6], Integer.parseInt(Income[6])));
	 inc.getData().add(new XYChart.Data(months[7], Integer.parseInt(Income[7])));
	 inc.getData().add(new XYChart.Data(months[8], Integer.parseInt(Income[8])));
	 inc.getData().add(new XYChart.Data(months[9], Integer.parseInt(Income[9])));
	 inc.getData().add(new XYChart.Data(months[10],Integer.parseInt(Income[10])));
	 inc.getData().add(new XYChart.Data(months[11],Integer.parseInt( Income[11])));

	 exp.getData().add(new XYChart.Data(months[0],Integer.parseInt( Expenses[0])));
	 exp.getData().add(new XYChart.Data(months[1], Integer.parseInt(Expenses[1])));
	 exp.getData().add(new XYChart.Data(months[2],Integer.parseInt( Expenses[2])));
	 exp.getData().add(new XYChart.Data(months[3], Integer.parseInt(Expenses[3])));
	 exp.getData().add(new XYChart.Data(months[4],Integer.parseInt( Expenses[4])));
	 exp.getData().add(new XYChart.Data(months[5],Integer.parseInt( Expenses[5])));
	 exp.getData().add(new XYChart.Data(months[6],Integer.parseInt( Expenses[6])));
	 exp.getData().add(new XYChart.Data(months[7],Integer.parseInt( Expenses[7])));
	 exp.getData().add(new XYChart.Data(months[8],Integer.parseInt( Expenses[8])));
	 exp.getData().add(new XYChart.Data(months[9],Integer.parseInt( Expenses[9])));
	 exp.getData().add(new XYChart.Data(months[10],Integer.parseInt( Expenses[10])));
	 exp.getData().add(new XYChart.Data(months[11],Integer.parseInt( Expenses[11])));
		

   
 
}
@FXML
public void selectYear(){
String year= text.getText();

loadDataFromDataBase(year);	
		
}
@FXML
public void annual_report() {
	String year= text.getText();
	
	    try {
	    	Connection conn = dc.Connect();

	    	ResultSet rc= conn.createStatement().executeQuery("select Sum(value_In_NIS) from patient_pays_payment where year(date_of_payment)=" +year +";");
	    	ResultSet rc2 = conn.createStatement().executeQuery("select Sum(value_In_NIS) from patient_pays_payment where year(date_of_payment)=" +year +";");
	    	rc.first();
	    	rc2.first();
	    	in= rc.getString(1);
	    	out= rc2.getString(1);
	    	prof = Double.parseDouble(in)-Double.parseDouble(out);
	    	   
	    	
	    	
	    	}

	    	catch(SQLException | ClassNotFoundException ex) {
	    	System.err.println("Error"+ex);
	    	}
	    income2.setText("The annual Income is: "+in);
    	expenses2.setText("The annual expense is: " +out);
    	profit2.setText("The annual net profit is: " +prof );
	    

	       

}
@FXML
void newpage() {
	try {
	
	
		FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("report2.fxml"));
		AnchorPane root= (AnchorPane)fxmlloader.load();
		annual_report();
        Stage stage= new Stage();
        stage.setScene(new Scene(root));
        stage.show(); 

		
	}
	catch(Exception e) {
		System.err.println(e.getMessage());
	}


}
}



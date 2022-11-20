package payment;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;


import java.sql.Date; 
public class payController implements Initializable{

@FXML
private TableView<payment> tablepayment;
@FXML
private TableColumn<payment, String> columnID;
@FXML
private TableColumn<payment, String> columnName;
@FXML
private TableColumn<payment, String> columnValue;
@FXML
private TableColumn<payment, String> columnCurrency;
@FXML
private TableColumn<payment, String> columnNisValue;
@FXML
private TableColumn<payment, String> columnMethod;
@FXML
private TableColumn<payment, String> columnDate;
int s=0;
int s2=0;
@FXML
private Button add;
@FXML
private Button delete;
private Connection conn;
PreparedStatement pst = null;

@FXML
ComboBox addID = new ComboBox();
@FXML
ComboBox  addname = new ComboBox ();
@FXML
TextField addvalue = new TextField();
@FXML
ComboBox  addcurrency = new ComboBox ();
@FXML
ComboBox  addmethod = new ComboBox ();
@FXML
DatePicker adddate = new DatePicker();
@FXML
private ImageView imgLogo;



//Intialize observable list to hold database data
private ObservableList<payment> data;
private dbconnect dc;

@Override
public void initialize(URL url, ResourceBundle rb) {
Image logo = new Image("/Images/logo.png");
imgLogo.setImage(logo);
dc= new dbconnect();
columnID.setCellFactory(TextFieldTableCell.forTableColumn());
columnName.setCellFactory(TextFieldTableCell.forTableColumn());
columnValue.setCellFactory(TextFieldTableCell.forTableColumn());
columnCurrency.setCellFactory(TextFieldTableCell.forTableColumn());
columnMethod.setCellFactory(TextFieldTableCell.forTableColumn());
columnDate.setCellFactory(TextFieldTableCell.forTableColumn());

load_to_ID();
load_to_name();
load_to_currency();
load_to_method();

loadDataFromDataBase();

}

public void load_to_ID() {
	ObservableList<String> options = 
		    FXCollections.observableArrayList();
	try {
		Connection conn = dc.Connect();

		ResultSet rs = conn.createStatement().executeQuery("select patientID_num from patient");
		while ( rs.next()) {
		options.add(rs.getString(1));
		}
		}

		catch(SQLException | ClassNotFoundException ex) {
		System.err.println("Error"+ex);
		}
		
	addID.setItems(options);

	
}
@FXML
public void ID_selected(){
s2=1;
if(s==1) {
			return;
	}
		
else {

	String ID= addID.getValue().toString();

	ObservableList<String> options = 
		    FXCollections.observableArrayList();
	try {
		Connection conn = dc.Connect();

		ResultSet rs = conn.createStatement().executeQuery("select patientName from patient where patientID_num=" +ID);
		while ( rs.next()) {
		options.add(rs.getString(1));
		}
		}

		catch(SQLException | ClassNotFoundException ex) {
		System.err.println("Error"+ex);
		}
		
	addname.setItems(options);

}
}
@FXML
public void name_selected(){
	s=1;
	if(s2==1) {
		return;
	}
		
	else {
	
	String name= addname.getValue().toString();
	ObservableList<String> options = 
		    FXCollections.observableArrayList();
	try {
		Connection conn = dc.Connect();

		ResultSet rs = conn.createStatement().executeQuery("select patientID_num from patient where patientName='" +name +"';" );
		while ( rs.next()) {
		options.add(rs.getString(1));
		}
		}

		catch(SQLException | ClassNotFoundException ex) {
		System.err.println("Error"+ex);
		}
		
	addID.setItems(options);
	//addID.setOnAction(null);

	}
}

public void load_to_name() {

	ObservableList<String> options = 
		    FXCollections.observableArrayList();
	try {
		Connection conn = dc.Connect();

		ResultSet rs = conn.createStatement().executeQuery("select patientName from patient");
		while ( rs.next()) {
		options.add(rs.getString(1));
		}
		}

		catch(SQLException | ClassNotFoundException ex) {
		System.err.println("Error"+ex);
		}
		
	addname.setItems(options);

	
}

public void load_to_method() {
	ObservableList<String> options = 
		    FXCollections.observableArrayList(

		        "Cash",
		        "Checks"
		        
		    );
		
	addmethod.setItems(options);
	
}
public void load_to_currency() {
	ObservableList<String> options = 
		    FXCollections.observableArrayList(

		        "USD",
		        "NIS",
		        "JD"
		    );

		
	addcurrency.setItems(options);
	
}





@FXML
public void loadDataFromDataBase() {
try {
Connection conn = dc.Connect();
data = FXCollections.observableArrayList();
ResultSet rs = conn.createStatement().executeQuery("select * from patient_pays_payment");
ResultSet rs2;
while ( rs.next()) {
	 rs2=conn.createStatement().executeQuery("select patientName from patient where patientID_num="+rs.getString(1));
	 rs2.first();
	
	 double Nis=000;

	 if (rs.getString(4).equals("USD")) 
	 	Nis=3.4*(Double.parseDouble(rs.getString(2)));
	 else if (rs.getString(4).equals("JD"))
	 	Nis=4.9*(Double.parseDouble(rs.getString(2)));
	 else
	 	Nis=(Double.parseDouble(rs.getString(2)));
	 
	 
data.add(new payment(rs.getString(1),rs2.getString(1),rs.getString(2),
rs.getString(4),Double.toString(Nis),rs.getString(6),rs.getString(5)));

}

}

catch(SQLException | ClassNotFoundException ex) {
System.err.println("Error"+ex);
}
columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
columnNisValue.setCellValueFactory(new PropertyValueFactory<>("Nisvalue"));
columnCurrency.setCellValueFactory(new PropertyValueFactory<>("currency"));
columnMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));


tablepayment.setItems(null);
tablepayment.setItems(data);

}

@FXML
private void deleteRow1(ActionEvent e) {
// TODO Auto-generated method stub
ObservableList<payment> selectedRows = tablepayment.getSelectionModel().getSelectedItems();
ArrayList<payment> rows = new ArrayList<>(selectedRows);
rows.forEach(row -> {tablepayment.getItems().remove(row);

try {
conn = dc.Connect();
//ResultSet rs = conn.createStatement().executeQuery("delete from  payment where pName='" +row.getName() + "';");
ExecuteStatement("delete from patient_pays_payment  where patientID_num = '"+row.getID() +"'AND date_of_payment = '" +row.getDate()+"';");
conn.close();
System.out.println("Connection closed");

} catch (SQLException e1) {
e1.printStackTrace();
} catch (ClassNotFoundException e1) {
e1.printStackTrace();
}
});
}	


public void ExecuteStatement(String SQL) throws SQLException {

try {
Statement stmt = conn.createStatement();
stmt.executeUpdate(SQL);
stmt.close();


}
catch(SQLException s) {
s.printStackTrace();
System.out.println("SQL statement is not executed!");

}}


@FXML
private void insertData1(ActionEvent e) {
payment rc;

double Nis=0;
if (addcurrency.getValue().toString()=="USD") 
	Nis=3.4*(Double.parseDouble(addvalue.getText().toString()));

else if (addcurrency.getValue().toString()=="JD")
	Nis=4.9*(Double.parseDouble(addvalue.getText().toString()));
else if (addcurrency.getValue().toString()=="NIS")
	Nis=(Double.parseDouble(addvalue.getText().toString()));

rc =new payment(
addID.getValue().toString(),		
addname.getValue().toString(),
addvalue.getText(), 
addcurrency.getValue().toString(),
Double.toString(Nis),
adddate.getValue().toString(),
addmethod.getValue().toString());
data.add(rc);

try {
conn = dc.Connect();
ExecuteStatement("Insert into patient_pays_payment values('"+rc.getID()+"','"+rc.getValue()+"','"+rc.getNisvalue()+"','"+rc.getCurrency()+"','"+ rc.getMethod()+"','"+Date.valueOf(rc.getDate()) +"')");
conn.close();

} catch (SQLException e1) {
e1.printStackTrace();
} catch (ClassNotFoundException e1) {
e1.printStackTrace();
}

loadDataFromDataBase();

}


@FXML
public void updateValue() {

columnValue.setOnEditCommit(        
(CellEditEvent<payment, String> t) -> {
((payment) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setName(t.getNewValue());



try {
	
conn = dc.Connect();

ExecuteStatement("update patient_pays_payment set pvalue ='" +t.getNewValue() +"' where patientID_num = '"+t.getRowValue().getID() +"'AND date_of_payment = '"+t.getRowValue().getDate()+"';");

double Nis=000;

if (t.getRowValue().getCurrency().equals("USD")) 
	Nis=3.4*(Double.parseDouble(t.getNewValue()));
else if (t.getRowValue().getCurrency().equals("JD"))
	Nis=4.9*(Double.parseDouble(t.getNewValue()));
else
	Nis=(Double.parseDouble(t.getNewValue()));

ExecuteStatement("update patient_pays_payment set value_in_NIS =" +Double.toString(Nis) +" where patientID_num = '"+t.getRowValue().getID() +"'AND date_of_payment = '"+t.getRowValue().getDate()+"';");
conn.close();
System.out.println("Connection closed");


loadDataFromDataBase();
} catch (SQLException e) {
e.printStackTrace();
} catch (ClassNotFoundException e) {
e.printStackTrace();
}

});


}

@FXML
public void updatemethod() {

columnMethod.setOnEditCommit(        
(CellEditEvent<payment, String> t) -> {
((payment) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setName(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update patient_pays_payment set method ='" +t.getNewValue() +"' where patientID_num = '"+t.getRowValue().getID() +"'AND date_of_payment = '"+t.getRowValue().getDate()+"';");
conn.close();
System.out.println("Connection closed");

} catch (SQLException e) {
e.printStackTrace();
} catch (ClassNotFoundException e) {
e.printStackTrace();
}
});

}
@FXML
public void updateCurrency() {

columnCurrency.setOnEditCommit(        
(CellEditEvent<payment, String> t) -> {
((payment) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setName(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update patient_pays_payment set currency ='" +t.getNewValue() +"' where patientID_num = '"+t.getRowValue().getID() +"'AND date_of_payment = '"+t.getRowValue().getDate()+"';");
double Nis=000;

if (t.getNewValue().equals("USD")) 
	Nis=3.4*(Double.parseDouble(t.getRowValue().getValue()));
else if (t.getNewValue().equals("JD"))
	Nis=4.9*(Double.parseDouble(t.getRowValue().getValue()));
else
	Nis=(Double.parseDouble(t.getRowValue().getValue()));

ExecuteStatement("update patient_pays_payment set value_in_NIS =" +Double.toString(Nis) +" where patientID_num = '"+t.getRowValue().getID() +"'AND date_of_payment = '"+t.getRowValue().getDate()+"';");
conn.close();
loadDataFromDataBase();
System.out.println("Connection closed");

} catch (SQLException e) {
e.printStackTrace();
} catch (ClassNotFoundException e) {
e.printStackTrace();
}
});

}
@FXML
public void updatedate(){

columnDate.setOnEditCommit(        
(CellEditEvent<payment, String> t) -> {
((payment) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setName(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update patient_pays_payment set date_of_payment='" +t.getNewValue() +"' where patientID_num = '"+t.getRowValue().getID() +"'AND date_of_payment = '"+t.getRowValue().getDate()+"';");
conn.close();
System.out.println("Connection closed");

} catch (SQLException e) {
e.printStackTrace();
} catch (ClassNotFoundException e) {
e.printStackTrace();
}
});

}
}
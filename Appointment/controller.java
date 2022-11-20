package Appointment;

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

import com.jfoenix.controls.JFXTimePicker;

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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;


import java.sql.Date;
import java.text.SimpleDateFormat; 

public class controller implements Initializable{

@FXML
private TableView<Appointment> tableAppointment;
@FXML
private TableColumn<Appointment, String> columnPID;
@FXML
private TableColumn<Appointment, String> columnPname;
@FXML
private TableColumn<Appointment, String> columnDID;
@FXML
private TableColumn<Appointment, String> columnSID;

@FXML
private TableColumn<Appointment, String> columnDate;
@FXML
private TableColumn<Appointment, String> columnTime;
int s=0;
int s2=0;
@FXML
private Button add;
@FXML
private Button delete;
private Connection conn;
PreparedStatement pst = null;

@FXML
ComboBox addDID = new ComboBox();
@FXML
ComboBox addPID = new ComboBox();
@FXML
ComboBox addSID = new ComboBox();
@FXML
ComboBox  addPname = new ComboBox ();
@FXML
DatePicker adddate = new DatePicker();
@FXML
JFXTimePicker addTime = new JFXTimePicker();



//Intialize observable list to hold database data
private ObservableList<Appointment> data;
private dbconnect dc;

@Override
public void initialize(URL url, ResourceBundle rb) {
	
dc= new dbconnect();
columnDID.setCellFactory(TextFieldTableCell.forTableColumn());
columnPname.setCellFactory(TextFieldTableCell.forTableColumn());
columnSID.setCellFactory(TextFieldTableCell.forTableColumn());
columnPID.setCellFactory(TextFieldTableCell.forTableColumn());
columnTime.setCellFactory(TextFieldTableCell.forTableColumn());
columnDate.setCellFactory(TextFieldTableCell.forTableColumn());

load_to_DID();
load_to_SID();
load_to_PID();
load_to_Pname();
addTime.setIs24HourView(false);
loadDataFromDataBase();

}

public void load_to_DID() {
	ObservableList<String> options = 
		    FXCollections.observableArrayList();
	try {
		Connection conn = dc.Connect();

		ResultSet rs = conn.createStatement().executeQuery("select ID_number from Doctor");
		while ( rs.next()) {
		options.add(rs.getString(1));
		}
		}

		catch(SQLException | ClassNotFoundException ex) {
		System.err.println("Error"+ex);
		}
		
	addDID.setItems(options);

	
}
public void load_to_SID() {
	ObservableList<String> options = 
		    FXCollections.observableArrayList();
	try {
		Connection conn = dc.Connect();

		ResultSet rs = conn.createStatement().executeQuery("select ID_num from specialist");
		while ( rs.next()) {
		options.add(rs.getString(1));
		}
		}

		catch(SQLException | ClassNotFoundException ex) {
		System.err.println("Error"+ex);
		}
		
	addSID.setItems(options);

	
}
public void load_to_PID() {
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
		
	addPID.setItems(options);

	
}



public void load_to_Pname() {

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
		
	addPname.setItems(options);

	
}


@FXML
public void loadDataFromDataBase() {
	
try {
Connection conn = dc.Connect();
data = FXCollections.observableArrayList();
ResultSet rs = conn.createStatement().executeQuery("select * from doctor_has_appointment");
ResultSet rs2;
while ( rs.next()) {
	
	rs2 = conn.createStatement().executeQuery("select  patientName from patient where patientID_num =" +rs.getString(2));

	rs2.first();
	/*SimpleDateFormat sdf = new SimpleDateFormat(
		    "dd-MM-yyyy");
*/
data.add(new Appointment(rs.getString(2),rs2.getString(1),rs.getString(4),rs.getString(3),
rs.getString(1),rs.getString(5)));
}

}

catch(SQLException | ClassNotFoundException ex) {
System.err.println("Error"+ex);
}
columnDID.setCellValueFactory(new PropertyValueFactory<>("DID"));
columnPname.setCellValueFactory(new PropertyValueFactory<>("Pname"));
columnPID.setCellValueFactory(new PropertyValueFactory<>("PID"));
columnSID.setCellValueFactory(new PropertyValueFactory<>("SID"));
columnDate.setCellValueFactory(new PropertyValueFactory<>("appdate"));
columnTime.setCellValueFactory(new PropertyValueFactory<>("apptime"));

tableAppointment.setItems(null);
tableAppointment.setItems(data);

addDID.setValue(null);
addPname.setValue(null);
addSID.setValue(null);
addPID.setValue(null);
addTime.setValue(null);
adddate.setValue(null);

}

@FXML
private void deleteRow1(ActionEvent e) {
// TODO Auto-generated method stub
ObservableList<Appointment> selectedRows = tableAppointment.getSelectionModel().getSelectedItems();
ArrayList<Appointment> rows = new ArrayList<>(selectedRows);
rows.forEach(row -> {tableAppointment.getItems().remove(row);

try {
conn = dc.Connect();
//ResultSet rs = conn.createStatement().executeQuery("delete from  Appointment where pName='" +row.getName() + "';");
ExecuteStatement("delete from doctor_has_appointment where DID='" +row.getDID() +"'AND app_date=' " +row.getAppdate() +"' AND timing ='"+row.getApptime() +"';");
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
Appointment rc;
addTime.setIs24HourView(false);

rc =new Appointment(
addPID.getValue().toString(),		
addPname.getValue().toString(),
addTime.getValue().toString(),
adddate.getValue().toString(),
addDID.getValue().toString(),
addSID.getValue().toString());
data.add(rc);

try {
conn = dc.Connect();
ExecuteStatement("Insert into doctor_has_appointment values('"+rc.getDID()+"','"+rc.getPID()+"','" +Date.valueOf(rc.getAppdate()) +"','"+rc.getApptime() +"','"+ rc.getSID() +"')");
conn.close();

} catch (SQLException e1) {
e1.printStackTrace();
} catch (ClassNotFoundException e1) {
e1.printStackTrace();
}

loadDataFromDataBase();

}

@FXML
public void updateDID() {

columnDID.setOnEditCommit(        
(CellEditEvent<Appointment, String> t) -> {
((Appointment) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setDID(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update doctor_has_appointment set DID =" +t.getNewValue()+" where DID = "+t.getRowValue().getDID() +" AND app_date='" +Date.valueOf(t.getRowValue().getAppdate()) +"'AND timing='" +t.getRowValue().getApptime() +"';");
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
public void updateSID() {

columnSID.setOnEditCommit(        
(CellEditEvent<Appointment, String> t) -> {
((Appointment) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setSID(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update doctor_has_appointment set SID =" +t.getNewValue()+" where DID = "+t.getRowValue().getDID() +" AND app_date='" +Date.valueOf(t.getRowValue().getAppdate()) +"'AND timing='" +t.getRowValue().getApptime() +"';");
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
public void updateDate() {

columnDate.setOnEditCommit(        
(CellEditEvent<Appointment, String> t) -> {
((Appointment) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setAppdate(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update doctor_has_appointment set app_date ='" +Date.valueOf(t.getNewValue())+"' where DID = "+t.getRowValue().getDID() +" AND app_date='" +Date.valueOf(t.getRowValue().getAppdate()) +"'AND timing='" +t.getRowValue().getApptime() +"';");
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
public void updateTime() {

columnTime.setOnEditCommit(        
(CellEditEvent<Appointment, String> t) -> {
((Appointment) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setApptime(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update doctor_has_appointment set timing ='" +t.getNewValue()+"' where DID = "+t.getRowValue().getDID() +" AND app_date='" +Date.valueOf(t.getRowValue().getAppdate()) +"'AND timing='" +t.getRowValue().getApptime() +"';");
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
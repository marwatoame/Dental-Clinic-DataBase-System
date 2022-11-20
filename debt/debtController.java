package debt;


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


import java.sql.Date; 
public class debtController implements Initializable{

	@FXML
	AnchorPane apMain;
@FXML
private TableView<debt> tabledebt;
@FXML
private TableColumn<debt, String> columnID;
@FXML
private TableColumn<debt, String> columnName;
@FXML
private TableColumn<debt, String> columnValue;
@FXML
private TableColumn<debt, String> columnCurrency;
@FXML
private TableColumn<debt, String> columnMethod;
@FXML
private TableColumn<debt, String> columnDate;

@FXML
private TableView<debt> tabledebt2;

@FXML
private TableColumn<debt, String> columnName2;
@FXML
private TableColumn<debt, String> columnValue2;
@FXML
private TableColumn<debt, String> columnCurrency2;
@FXML
private TableColumn<debt, String> columnMethod2;
@FXML
private TableColumn<debt, String> columnDate2;


@FXML
private Button add;
@FXML
private Button add2;
private Connection conn;
PreparedStatement pst = null;

@FXML
TextField addID = new TextField();
@FXML
TextField addname = new TextField();
@FXML
TextField addvalue = new TextField();
@FXML
TextField addcurrency = new TextField();
@FXML
TextField addmethod = new TextField();
@FXML
DatePicker adddate = new DatePicker();

@FXML
TextField addname2 = new TextField();
@FXML
TextField addvalue2 = new TextField();
@FXML
TextField addcurrency2 = new TextField();
@FXML
TextField addmethod2 = new TextField();
@FXML
DatePicker adddate2 = new DatePicker();


//Intialize observable list to hold database data
private ObservableList<debt> data;
private ObservableList<debt> data2;
private dbconnect dc;

@Override
public void initialize(URL url, ResourceBundle rb) {
	
dc= new dbconnect();
columnID.setCellFactory(TextFieldTableCell.forTableColumn());
columnName.setCellFactory(TextFieldTableCell.forTableColumn());
columnValue.setCellFactory(TextFieldTableCell.forTableColumn());
columnCurrency.setCellFactory(TextFieldTableCell.forTableColumn());
columnMethod.setCellFactory(TextFieldTableCell.forTableColumn());
columnDate.setCellFactory(TextFieldTableCell.forTableColumn());

columnName2.setCellFactory(TextFieldTableCell.forTableColumn());
columnValue2.setCellFactory(TextFieldTableCell.forTableColumn());
columnCurrency2.setCellFactory(TextFieldTableCell.forTableColumn());
columnMethod2.setCellFactory(TextFieldTableCell.forTableColumn());
columnDate2.setCellFactory(TextFieldTableCell.forTableColumn());
loadDataFromDataBase();
}


@FXML
public void loadDataFromDataBase() {
try {
Connection conn = dc.Connect();
data = FXCollections.observableArrayList();
ResultSet rs = conn.createStatement().executeQuery("select * from debts_for_specialist");
while ( rs.next()) {
data.add(new debt(rs.getString(1),rs.getString(2),rs.getString(3),
rs.getString(4),rs.getString(5),rs.getString(6)));
}

data2 = FXCollections.observableArrayList();
ResultSet rs2 = conn.createStatement().executeQuery("select * from debts_for_services");
while ( rs2.next()) {
data2.add(new debt(null,rs2.getString(1),rs2.getString(2),rs2.getString(3),
rs2.getString(4),rs2.getString(5)));
}


}
catch(SQLException | ClassNotFoundException ex) {
System.err.println("Error"+ex);
}
columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
columnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
columnCurrency.setCellValueFactory(new PropertyValueFactory<>("currency"));
columnMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));

columnName2.setCellValueFactory(new PropertyValueFactory<>("name"));
columnValue2.setCellValueFactory(new PropertyValueFactory<>("value"));
columnCurrency2.setCellValueFactory(new PropertyValueFactory<>("currency"));
columnMethod2.setCellValueFactory(new PropertyValueFactory<>("method"));
columnDate2.setCellValueFactory(new PropertyValueFactory<>("date"));

//addname.setMaxWidth(columnName.getPrefWidth());


tabledebt.setItems(null);
tabledebt.setItems(data);


tabledebt2.setItems(null);
tabledebt2.setItems(data2);


}

@FXML
private void deleteRow1(ActionEvent e) {
// TODO Auto-generated method stub
ObservableList<debt> selectedRows = tabledebt.getSelectionModel().getSelectedItems();
ArrayList<debt> rows = new ArrayList<>(selectedRows);
rows.forEach(row -> {tabledebt.getItems().remove(row);

try {
conn = dc.Connect();
//ResultSet rs = conn.createStatement().executeQuery("delete from  debt where pName='" +row.getName() + "';");
ExecuteStatement("delete from debts_for_specialist where ID_num='" +row.getID() + "';");
conn.close();
System.out.println("Connection closed");

} catch (SQLException e1) {
e1.printStackTrace();
} catch (ClassNotFoundException e1) {
e1.printStackTrace();
}
});
}	

@FXML
private void deleteRow2(ActionEvent e) {
// TODO Auto-generated method stub
ObservableList<debt> selectedRows = tabledebt2.getSelectionModel().getSelectedItems();
ArrayList<debt> rows = new ArrayList<>(selectedRows);
rows.forEach(row -> {tabledebt2.getItems().remove(row);

try {
conn = dc.Connect();
//ResultSet rs = conn.createStatement().executeQuery("delete from  debt where pName='" +row.getName() + "';");
ExecuteStatement("delete from debts_for_services where service_name"+ "='" +row.getName() + "';");
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
debt rc;
rc =new debt(
addID.getText(),		
addname.getText(),
addvalue.getText(), 
addcurrency.getText(),
addmethod.getText(),
adddate.getValue().toString());
data.add(rc);

try {
conn = dc.Connect();
ExecuteStatement("Insert into debts_for_specialist values('"+rc.getID()+"','"+rc.getName()+"','"+rc.getValue()+"','"+rc.getCurrency()+"','"+ rc.getMethod()+"','"+Date.valueOf(rc.getDate()) +"')");
conn.close();

} catch (SQLException e1) {
e1.printStackTrace();
} catch (ClassNotFoundException e1) {
e1.printStackTrace();
}

loadDataFromDataBase();
addID.clear();
addname.clear();
addcurrency.clear();
addmethod.clear();
addvalue.clear();
}
@FXML
private void insertData2(ActionEvent e) {
debt rc;
rc =new debt(
null,		
addname2.getText(),
addvalue2.getText(), 
addcurrency2.getText(),
addmethod2.getText(),
adddate2.getValue().toString());

data2.add(rc);

try {
conn = dc.Connect();
ExecuteStatement("Insert into debts_for_services values('"+rc.getName()+"','"+rc.getValue()+"','"+rc.getCurrency()+"','"+ rc.getMethod()+"','"+Date.valueOf(rc.getDate()) +"')");
conn.close();

} catch (SQLException e1) {
e1.printStackTrace();
} catch (ClassNotFoundException e1) {
e1.printStackTrace();
}

loadDataFromDataBase();
addname2.clear();
addcurrency2.clear();
addmethod2.clear();
addvalue2.clear();
}

@FXML
public void updateValue() {

columnValue.setOnEditCommit(        
(CellEditEvent<debt, String> t) -> {
((debt) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setName(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update debts_for_specialist set pvalue ='" +t.getNewValue()+"' where ID_num = '"+t.getRowValue().getID() +"';");
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
public void updatemethod() {

columnMethod.setOnEditCommit(        
(CellEditEvent<debt, String> t) -> {
((debt) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setName(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update debts_for_specialist set method ='" +t.getNewValue()+"' where ID_num = '"+t.getRowValue().getID() +"';");
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
(CellEditEvent<debt, String> t) -> {
((debt) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setName(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update debts_for_specialist set currency ='" +t.getNewValue()+"' where ID_num = '"+t.getRowValue().getID() +"';");
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
public void updatedate() {

columnDate.setOnEditCommit(        
(CellEditEvent<debt, String> t) -> {
((debt) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setName(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update debts_for_specialist set date_of_payment='" +t.getNewValue()+"' where ID_num = '"+t.getRowValue().getID() +"';");
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
public void updateValue2() {

columnValue2.setOnEditCommit(        
(CellEditEvent<debt, String> t) -> {
((debt) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setName(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update debts_for_services set pvalue ='" +t.getNewValue()+"' where service_name = '"+t.getRowValue().getName() +"';");
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
public void updatemethod2() {

columnMethod2.setOnEditCommit(        
(CellEditEvent<debt, String> t) -> {
((debt) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setName(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update debts_for_services set method ='" +t.getNewValue()+"' where service_name = '"+t.getRowValue().getName() +"';");
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
public void updateCurrency2() {

columnCurrency2.setOnEditCommit(        
(CellEditEvent<debt, String> t) -> {
((debt) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setName(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update debts_for_services set currency ='" +t.getNewValue()+"' where service_name = '"+t.getRowValue().getName() +"';");
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
public void updatedate2() {

columnDate2.setOnEditCommit(        
(CellEditEvent<debt, String> t) -> {
((debt) t.getTableView().getItems().get(
t.getTablePosition().getRow())
).setName(t.getNewValue());

try {
conn = dc.Connect();
ExecuteStatement("update debts_for_services set date_of_payment='" +t.getNewValue()+"' where service_name = '"+t.getRowValue().getName() +"';");
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
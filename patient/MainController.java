package patient;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import javafx.util.converter.IntegerStringConverter;


public class MainController implements Initializable{


	@FXML
	private TableView<patients> tablepatients;
	@FXML
	private TableColumn<patients, String> columnID;
	@FXML
	private TableColumn<patients, String> columnName;
	@FXML
	private TableColumn<patients, String> columnContact;
	@FXML
	private TableColumn<patients, String> columnage;
	@FXML
	private TableColumn<patients, String> columnRecord;
	@FXML
	private TableColumn<patients, String> columnPassword;
	@FXML
	private Button btnLoad;
	@FXML
	private Button delete;
	@FXML
	private Button add;
	@FXML
	private Button Back;
	
	private Connection conn;
	 PreparedStatement pst = null;
	@FXML
	TextField addname = new TextField();
	@FXML
	 TextField addcontact = new TextField();
	@FXML
	 TextField addage = new TextField();
	@FXML
	 TextField addpassword = new TextField();
	@FXML
	 TextArea addrecord = new TextArea();
    @FXML
    private ImageView imgLogo;
	
	//Intialize observable list to hold database data
    private ObservableList<patients> data;
    private dbconnect dc;
	 
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Image logo = new Image("/Images/logo.png");
		imgLogo.setImage(logo);
		dc= new dbconnect();
		
		columnName.setCellFactory(TextFieldTableCell.forTableColumn());
		columnRecord.setCellFactory(TextFieldTableCell.forTableColumn());
		columnage.setCellFactory(TextFieldTableCell.forTableColumn());
		columnPassword.setCellFactory(TextFieldTableCell.forTableColumn());
		columnContact.setCellFactory(TextFieldTableCell.forTableColumn());
		loadDataFromDataBase();
	}
	

	
	@FXML
	public void loadDataFromDataBase() {
		try {
			Connection conn = dc.Connect();
			data = FXCollections.observableArrayList();
			ResultSet rs = conn.createStatement().executeQuery("Select * from patient");
			while ( rs.next()) {
				data.add(new patients(rs.getString(1),rs.getString(2),rs.getString(3),
						rs.getString(4),rs.getString(5),rs.getString(6)));
			}
			
			}
		catch(SQLException | ClassNotFoundException ex) {
			System.err.println("Error"+ex);
		}
		columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
		columnage.setCellValueFactory(new PropertyValueFactory<>("age"));
		columnRecord.setCellValueFactory(new PropertyValueFactory<>("record"));
		columnPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
		
		 //addname.setMaxWidth(columnName.getPrefWidth());
		
		
		tablepatients.setItems(null);
		tablepatients.setItems(data);
		
		
	}
	@FXML
	private void deleteRow(ActionEvent e) {
		// TODO Auto-generated method stub
		 ObservableList<patients> selectedRows = tablepatients.getSelectionModel().getSelectedItems();
     	ArrayList<patients> rows = new ArrayList<>(selectedRows);
     	rows.forEach(row -> {tablepatients.getItems().remove(row);
	
		try {
		conn = dc.Connect();
			//ResultSet rs = conn.createStatement().executeQuery("delete from  patients where pName='" +row.getName() + "';");
			ExecuteStatement("delete from patient where patientID_num='" +row.getID() + "';");
			
			conn.close();
			
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		});
	}	
	
	
	public void ExecuteStatement(String SQL) throws SQLException {

		try {
			Connection conn=dc.Connect();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();
		
			 
		}
		catch(SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");
			  
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
@FXML
	private void insertData(ActionEvent e) {
   patients rc;
     rc =new patients(null,
  			  addname.getText(),
         		addcontact.getText(), 
         		addage.getText(),
         		addrecord.getText(),addpassword.getText());

		 //data.add(rc);

	     	
	try {
		   conn = dc.Connect();
			ExecuteStatement("Insert into patient (patientName,contact,p_age,medical_record,p_password) values('"+rc.getName()+"','"+rc.getContact()+"','"+rc.getAge()+"','"+ rc.getRecord() +"','"+ rc.getPassword()+"')");
			conn.close();
			
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}

	loadDataFromDataBase();
	  addname.clear();
 		addcontact.clear();
 		addage.clear();
 		addrecord.clear();
 		addpassword.clear();
	}

@FXML
public void updateName() {
	
	   columnName.setOnEditCommit(        
       		(CellEditEvent<patients, String> t) -> {
                      ((patients) t.getTableView().getItems().get(
       	                        t.getTablePosition().getRow())
       	                        ).setName(t.getNewValue());
                      
	try {
		conn = dc.Connect();
		ExecuteStatement("update  patient set patientName ='" +t.getNewValue()+"' where patientID_num = '"+t.getRowValue().getID() +"';");
		conn.close();		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
     		});
	  
}

@FXML
public void updateage() {
	
	   columnage.setOnEditCommit(        
       		(CellEditEvent<patients, String> t) -> {
                      ((patients) t.getTableView().getItems().get(
       	                        t.getTablePosition().getRow())
       	                        ).setAge(t.getNewValue());
                      
	try {
		conn = dc.Connect();
		ExecuteStatement("update  patient set p_age ='" +t.getNewValue()+"' where patientID_num = '"+t.getRowValue().getID() +"';");
		conn.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
     		});
	  
}
@FXML
public void updateRecord() {
	
	   columnRecord.setOnEditCommit(        
       		(CellEditEvent<patients, String> t) -> {
                      ((patients) t.getTableView().getItems().get(
       	                        t.getTablePosition().getRow())
       	                        ).setRecord(t.getNewValue());
                      
	try {
		conn = dc.Connect();
		ExecuteStatement("update  patient set medical_record ='" +t.getNewValue()+"' where patientID_num = '"+t.getRowValue().getID() +"';");
		conn.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
     		});
	  
}
@FXML
public void updateContact() {
	
	   columnContact.setOnEditCommit(        
    		(CellEditEvent<patients, String> t) -> {
                   ((patients) t.getTableView().getItems().get(
    	                        t.getTablePosition().getRow())
    	                        ).setContact(t.getNewValue());
                   
	try {
		conn = dc.Connect();
		ExecuteStatement("update  patient set contact ='" +t.getNewValue()+"' where patientID_num = '"+t.getRowValue().getID() +"';");
		conn.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
  		});
	  
}
@FXML
public void updatePassword() {
	
	   columnPassword.setOnEditCommit(        
    		(CellEditEvent<patients, String> t) -> {
                   ((patients) t.getTableView().getItems().get(
    	                        t.getTablePosition().getRow())
    	                        ).setPassword(t.getNewValue());
                   
	try {
		conn = dc.Connect();
		ExecuteStatement("update  patient set p_password ='" +t.getNewValue()+"' where patientID_num = '"+t.getRowValue().getID() +"';");
		conn.close();
		
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
  		});
	  
}
}

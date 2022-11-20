package Specialist;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import Specialist.specialist;
import MainMenu.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SpecialistController implements Initializable{

	@FXML
    private Button btnRefresh;

    @FXML
    private Button btnClear;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnAdd;

    @FXML
    private TextField addSname;

    @FXML
    private TextField addID;

    @FXML
    private TextField addSpeciality;

    @FXML
    private TextField addSalary;
    
    @FXML
    private TextField addPhone;

    @FXML
    private TableView<specialist> table_specialist;

    @FXML
    private TableColumn<specialist, String> ColID;

    @FXML
    private TableColumn<specialist, String> ColName;

    @FXML
    private TableColumn<specialist, String> ColSpeciality;

    @FXML
    private TableColumn<specialist, String> ColSalary;
    
    @FXML
    private TableColumn<specialist, String> ColPhone;
    
    @FXML
    private ImageView imgLogo;
    
    private ObservableList<specialist> data;
	private DbConnection dc;
	
	@Override	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		dc=new DbConnection();
		try{
			Image logo = new Image("/Images/logo.png");
			imgLogo.setImage(logo);
			
			ColID.setCellValueFactory(new PropertyValueFactory<>("idnum"));
			ColName.setCellValueFactory(new PropertyValueFactory<>("sname"));
			ColSpeciality.setCellValueFactory(new PropertyValueFactory<>("speciality"));
			ColSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
			ColPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
			
			ColName.setCellFactory(TextFieldTableCell.<specialist>forTableColumn());
			ColSpeciality.setCellFactory(TextFieldTableCell.<specialist>forTableColumn());
			ColSalary.setCellFactory(TextFieldTableCell.<specialist>forTableColumn());
			ColPhone.setCellFactory(TextFieldTableCell.<specialist>forTableColumn());

			getData();
		
			btnDelete.setOnAction((ActionEvent e) -> {        	 
				ObservableList<specialist> selectedRows = table_specialist.getSelectionModel().getSelectedItems();
				ArrayList<specialist> rows = new ArrayList<>(selectedRows);
				rows.forEach(row -> {
				table_specialist.getItems().remove(row); 
				deleteRow(row); 
				table_specialist.refresh();
				});   
			});
		}
		catch(SQLException ex){
			System.err.println("Error"+ex);
		}
		 catch (ClassNotFoundException e) {
			e.printStackTrace();
		}  
	}	
	
	private void getData() throws SQLException,ClassNotFoundException  {
		// TODO Auto-generated method stub
		
		try{
			Connection conn=dc.Connect();
			data=FXCollections.observableArrayList();
			ResultSet rs= conn.createStatement().executeQuery("select * from specialist");
			while ( rs.next() ) 
				data.add(new specialist(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5)));
			
			conn.close();
			
		}
		catch(SQLException ex){
			System.err.println("Error"+ex);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		table_specialist.setItems(null);
		table_specialist.setItems(data);
		
	}
	
	@FXML
	private void insertData() {
		specialist rc; 
        int i=0,flag=0;
        while(i< data.size()){
        	if(data.get(i).getSname().toString().equalsIgnoreCase(addSname.getText())){
        		flag=1;
        		break;
        	}
        	++i;
        }
        if(flag==0){
        	if(addPhone.getText().length()<=11){
				if(addID.getText().matches("[0-9]+") && addSalary.getText().matches("[0-9]+")  && addPhone.getText().matches("[0-9]+") ){
					rc = new specialist(addID.getText(),addSname.getText(),addPhone.getText(),addSpeciality.getText(),addSalary.getText());
					data.add(rc);
					try {				
						Connection conn=dc.Connect();
						ExecuteStatement("Insert into specialist values('"+rc.getIdnum()+"','"+rc.getSname()+"','"+ rc.getPhone()+"','"+ rc.getSpeciality()+"','"+ rc.getSalary()+"')");
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				else
					System.out.println("Please enter only digits for ID number and Salary and Phone ");
        	}
			else
				System.out.println("Please enter right number of digits for the phone number");
        }
        else
        	System.out.println("The entered specialist is already in the data base");
        addID.clear();
        addSname.clear();
    	addSpeciality.clear();
    	addSalary.clear();
    	addPhone.clear();
		
		}

	public void ExecuteStatement(String SQL) throws SQLException {

		try {
			Connection conn=dc.Connect();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();	 
			conn.close();
		}
		catch(SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");	  
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void updateSname() {
		ColName.setOnEditCommit((CellEditEvent<specialist, String> t) -> {((specialist) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSname(t.getNewValue());	
		try {
			Connection conn=dc.Connect();
			ExecuteStatement("update  specialist set SName= '" +t.getNewValue() +"' where IDNum = '"+t.getRowValue().getIdnum() +"';");
			conn.close();			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
	}
	
	@FXML
	public void updateSpeciality() {
		ColSpeciality.setOnEditCommit((CellEditEvent<specialist, String> t) -> {((specialist) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSpeciality(t.getNewValue());		
		try {
			Connection conn=dc.Connect();
			ExecuteStatement("update specialist set speciality = '" +t.getNewValue() +"' where IDNum ='"+ t.getRowValue().getIdnum() +"';");
			conn.close();			
			} catch (SQLException e) {
				e.printStackTrace();
			}  catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
	}
	@FXML
	public void updateSalary() {
		ColSalary.setOnEditCommit((CellEditEvent<specialist, String> t) -> {((specialist) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSalary(t.getNewValue());
			if(t.getNewValue().matches("[0-9]+")){
				try {
					Connection conn=dc.Connect();
					ExecuteStatement("update specialist set salaryPerApp = '" +t.getNewValue() +"' where IDNum ='"+t.getRowValue().getIdnum() +"';");
					conn.close();			
				} catch (SQLException e) {
					e.printStackTrace();
				}  catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			else
				System.out.println("Please enter only digits for the salary");
		});
	}
	
	@FXML
	public void updatePhone() {
		ColPhone.setOnEditCommit((CellEditEvent<specialist, String> t) -> {((specialist) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSalary(t.getNewValue());
			if(t.getNewValue().length()<=11){
				if(t.getNewValue().matches("[0-9]+")){
					try {
						Connection conn=dc.Connect();
						ExecuteStatement("update specialist set phoneNumber = '" +t.getNewValue() +"' where IDNum ='"+t.getRowValue().getIdnum() +"';");
						conn.close();			
					} catch (SQLException e) {
						e.printStackTrace();
					}  catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				else
					System.out.println("Please enter only digits for the phone number");
			}
			else
				System.out.println("Please enter right number of digits for the phone number");
		});
	}
	
	@FXML
	public void refresh(){
		table_specialist.refresh();  
		try {
			getData();
		} catch (ClassNotFoundException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	private void deleteRow(specialist row) {
		// TODO Auto-generated method stub		
		try {
			Connection conn=dc.Connect();
			ExecuteStatement("delete from  specialist where IDNum='" +row.getIdnum() + "';");
			conn.close();			
			} catch (SQLException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}

	@FXML
	private void DeleteAll() {
		Stage primaryStage = null;
		Stage stage = new Stage();
		stage.initOwner(primaryStage);
		Button yesButton = new Button("Confirm");
		yesButton.setOnAction(e -> {
			if(!data.isEmpty()){
				for (specialist row:data) {
        			deleteRow(row);
        			table_specialist.refresh();
        		}
			}
			else
				System.out.println("There is no available specialists!");
			table_specialist.getItems().removeAll(data);
    		stage.close();
			});
		
		Button noButton = new Button("Cancel");
		noButton.setOnAction(e -> stage.close());

		HBox root = new HBox();
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setAlignment(Pos.CENTER);
        root.setSpacing(10);

		root.getChildren().addAll(yesButton, noButton);
		Scene scene = new Scene(root, 300, 100);
		stage.setScene(scene);
		stage.setTitle("Confirm Delete?");
		stage.show();
		}

}

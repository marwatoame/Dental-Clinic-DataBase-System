package Teeth;

import static javafx.stage.Modality.NONE;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import MainMenu.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class teethcontroller implements Initializable{

    @FXML
    private TableView<teeth> table_teeth;
    
    @FXML
    private TableColumn<teeth, String> col_ID;

    @FXML
    private TableColumn<teeth, String> col_Name;

    @FXML
    private TableColumn<teeth, String> col_toothnum;

    @FXML
    private TableColumn<teeth, String> col_condition;

    @FXML
    private TableColumn<teeth, String> col_treatment;

    @FXML
    private TableColumn<teeth, String> col_cost;
    
    @FXML
   TextField txt_toothnum = new TextField();;

    @FXML
    TextField txt_condition = new TextField();;

    @FXML
    TextField txt_cost = new TextField();;

    @FXML
    TextField txt_treatment = new TextField();;
    
    @FXML
    TextField txt_ID = new TextField();;

    @FXML
    TextField txt_Name = new TextField();;

    @FXML
    private Button bt_add;

    @FXML
    private Button bt_delete;

    @FXML
    private Button bt_clear;

    @FXML
    private Button bt_refresh;

    @FXML
    private Button bt_chart;
    
    @FXML
    private ImageView imgLogo;
 
    
    private ObservableList<teeth> data;
	private DbConnection dc;
	
	
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		dc=new DbConnection();
		try{
			Image logo = new Image("/Images/logo.png");
			imgLogo.setImage(logo);
			col_ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
			col_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
			col_toothnum.setCellValueFactory(new PropertyValueFactory<>("toothNumber"));
			col_condition.setCellValueFactory(new PropertyValueFactory<>("condition"));
			col_treatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
			col_cost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
			
			

			//col_toothnum.setCellFactory(TextFieldTableCell.<teeth>forTableColumn());
			col_condition.setCellFactory(TextFieldTableCell.<teeth>forTableColumn());
			col_treatment.setCellFactory(TextFieldTableCell.<teeth>forTableColumn());
			col_cost.setCellFactory(TextFieldTableCell.<teeth>forTableColumn());
		

			getData();
			
			bt_add.setOnAction((ActionEvent e) -> {
	            teeth rc; 
	            int i=0,flag=0;
	            while(i< data.size()){
	            	if(data.get(i).getID().toString().equalsIgnoreCase(txt_ID.getText())){
	            		flag=1;
	            		break;
	            	}
	            	++i;
	            }
	            if(flag==0){
	            	rc = new teeth(txt_ID.getText(), txt_Name.getText(),txt_toothnum.getText(),txt_condition.getText(),txt_treatment.getText(),txt_cost.getText().toString());
	            	data.add(rc);
	            	insertData(rc);
	            }
	            else{
	            	System.out.println("The entered information is already in the data base");
	            	
	            }
	            txt_ID.clear();
	            txt_Name.clear();
	            txt_toothnum.clear();
	            txt_condition.clear();
	            txt_treatment.clear();
	            txt_cost.clear();
	        });
			
		/*	col_toothnum.setOnEditCommit((CellEditEvent<teeth, String> t) -> {((teeth) t.getTableView().getItems().get(t.getTablePosition().getRow())).setToothNumber(t.getNewValue());
			updatetoothnum( t.getRowValue().getID(),t.getNewValue());
	        		});
			*/
			col_condition.setOnEditCommit((CellEditEvent<teeth, String> t) -> {((teeth) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCondition(t.getNewValue());
			updatetooth_condition( t.getRowValue().getID(),t.getRowValue().getToothNumber(),t.getNewValue());
	        		});
			
			col_treatment.setOnEditCommit((CellEditEvent<teeth, String> t) -> {((teeth) t.getTableView().getItems().get(t.getTablePosition().getRow())).setTreatment(t.getNewValue());
			updatetooth_treatment( t.getRowValue().getID(),t.getRowValue().getToothNumber(),t.getNewValue());
			});
			
			col_cost.setOnEditCommit((CellEditEvent<teeth, String> t) -> {((teeth) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCost(t.getNewValue());
			updateCost( t.getRowValue().getID(),t.getRowValue().getToothNumber(),t.getNewValue());
	        });
		 
			bt_delete.setOnAction((ActionEvent e) -> {        	 
		       	 ObservableList<teeth> selectedRows = table_teeth.getSelectionModel().getSelectedItems();
		       	ArrayList<teeth> rows = new ArrayList<>(selectedRows);
		       	rows.forEach(row -> {
		       		table_teeth.getItems().remove(row); 
		       		deleteRow(row); 
		       		table_teeth.refresh();
		       		});   
		       });
				
				bt_refresh.setOnAction((ActionEvent e) -> {        	 
					table_teeth.refresh();  
		        	try {
						getData();
					} catch (ClassNotFoundException | SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        });
				
				bt_clear.setOnAction((ActionEvent e) -> {
		    		Stage primaryStage = null;
		    		DeleteAll(primaryStage, NONE, table_teeth);	
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
			ResultSet rs= conn.createStatement().executeQuery("select * from Teeth");
			while ( rs.next() ) 
				data.add(new teeth(rs.getString(1),rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));

			conn.close();
			
		}
		catch(SQLException ex){
			System.err.println("Error"+ex);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		table_teeth.setItems(null);
		table_teeth.setItems(data);			
	}
	
	private void insertData(teeth rc) {			
	try {
				Connection conn=dc.Connect();
				ExecuteStatement("Insert into Teeth (patientID_num, patientName, Tooth_number, Tooth_condition, Tooth_treatment, Cost) values('"+rc.getID()+"','"+rc.getName()+"','"+rc.getToothNumber()+"','"+rc.getCondition()+"','"+ rc.getTreatment() +"','"+ rc.getCost() +"')");
				conn.close();
	}catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}
	
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
	
/*public void updatetoothnum(String ID, String toothnum) {
		
		try {
			Connection conn=dc.Connect();
			ExecuteStatement("update Teeth set tooth_number = '" +toothnum +"' where patientID_num = '"+ID+"';");
			conn.close();
			
			} catch (SQLException e) {
				e.printStackTrace();
			}  catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	*/
	public void updatetooth_condition(String ID,String number, String tooth_condition) {
		
		try {
			Connection conn=dc.Connect();
			ExecuteStatement("update Teeth set tooth_condition = '" +tooth_condition +"'where patientID_num = '"+ID+"'and tooth_number ='"+number+"';");
			conn.close();
			
			} catch (SQLException e) {
				e.printStackTrace();
			}  catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
     public void updatetooth_treatment(String ID,String number, String tooth_treatment) {
		
		try {
			Connection conn=dc.Connect();
			ExecuteStatement("update Teeth set tooth_treatment = '" +tooth_treatment +"' where patientID_num = '"+ID+"'and tooth_number ='"+number+"';");
			conn.close();
			
			} catch (SQLException e) {
				e.printStackTrace();
			}  catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	
	
	public void updateCost(String ID,String number, String Cost) {
			
			try {
				Connection conn=dc.Connect();
				ExecuteStatement("update Teeth set Cost = '" +Cost +"' where patientID_num = '"+ID+"'and tooth_number ='"+number+"';");
				conn.close();
				
				} catch (SQLException e) {
					e.printStackTrace();
				}  catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
		}
	
	private void deleteRow(teeth row) {
 		// TODO Auto-generated method stub
 		
 		try {
 			Connection conn=dc.Connect();
 			ExecuteStatement("delete from  Teeth where patientID_num='" +row.getID() + "';");
 			conn.close();
 			
 			} catch (SQLException e) {
 				e.printStackTrace();
 			} catch (ClassNotFoundException e) {
 				e.printStackTrace();
 			}
 	}

 	private void DeleteAll(Window owner, Modality modality,   TableView<teeth> table) {
 		// Create a Stage with specified owner and modality
 		Stage stage = new Stage();
 		stage.initOwner(owner);
 		stage.initModality(modality);
 	//	Label modalityLabel = new Label(modality.toString());
 		
 		Button yesButton = new Button("Confirm");
 		yesButton.setOnAction(e -> {
 			if(!data.isEmpty()){
 			for (teeth row:data) {
         			deleteRow(row);
         			table.refresh();
         		}
 			}
 			else
 				System.out.println("There is no available teeth information!");
         		table.getItems().removeAll(data);
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
 	
 	//To switch between scenes
 		public void changescene (ActionEvent event) throws IOException { 
 		/*
 			Parent chart = FXMLLoader.load(getClass().getResource("/Teeth/TeethChart.fxml"));
 			Scene newscene = new Scene(chart); 
 			
 			//to get the stage information 
 			Stage window =(Stage) bt_chart.getScene().getWindow();
 			
 			window.setScene(newscene);
 			window.show();
 			*/

 			FXMLLoader fxmlloader= new FXMLLoader(getClass().getResource("/Teeth/TeethChart.fxml"));
 			AnchorPane root= (AnchorPane)fxmlloader.load();
 	        Stage stage= new Stage();
 	        stage.setScene(new Scene(root));
 	        stage.show(); 
 			
 		}
 		

 }
	

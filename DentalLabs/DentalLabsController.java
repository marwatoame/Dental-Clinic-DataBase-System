package DentalLabs;

import MainMenu.DbConnection;

import static javafx.stage.Modality.NONE;

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
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DentalLabsController implements Initializable{

    @FXML
    private TableView<DentalLabs> table_DentalLab;

    @FXML
    private TableColumn<DentalLabs, String> col_Name;

    @FXML
    private TableColumn<DentalLabs, String> col_ContactNum;

    @FXML
    private TableColumn<DentalLabs, String> col_Address;

    @FXML
    private TableColumn<DentalLabs, String> col_DateSent;

    @FXML
    private TableColumn<DentalLabs, String> col_ExpectedDuration;

    @FXML
    private TableColumn<DentalLabs, String> col_Cost;

    @FXML
    private ImageView img_logo;

    @FXML
    private TextField textfield_Name;

    @FXML
    private TextField textfield_Address;

    @FXML
    private TextField textfield_Num;

    @FXML
    private TextField textfield_Date;

    @FXML
    private TextField textfield_Duration;

    @FXML
    private TextField textfield_Cost;

    @FXML
    private Button bt_add;

    @FXML
    private Button bt_delete;

    @FXML
    private Button bt_refresh;
    
    @FXML
    private Button bt_clear;
    
    private ObservableList<DentalLabs> data;
	private DbConnection dc;
   
	@Override
    public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		dc=new DbConnection();
		try{
			Image logo = new Image("/Images/logo.png");
			img_logo.setImage(logo);
			col_Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
			col_ContactNum.setCellValueFactory(new PropertyValueFactory<>("Contact_Num"));
			col_Address.setCellValueFactory(new PropertyValueFactory<>("Address"));
			col_DateSent.setCellValueFactory(new PropertyValueFactory<>("DateSent"));
			col_ExpectedDuration.setCellValueFactory(new PropertyValueFactory<>("ExpectedDuration"));
			col_Cost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
			
			col_ContactNum.setCellFactory(TextFieldTableCell.<DentalLabs>forTableColumn());
			col_Address.setCellFactory(TextFieldTableCell.<DentalLabs>forTableColumn());
			col_DateSent.setCellFactory(TextFieldTableCell.<DentalLabs>forTableColumn());
			col_ExpectedDuration.setCellFactory(TextFieldTableCell.<DentalLabs>forTableColumn());
			col_Cost.setCellFactory(TextFieldTableCell.<DentalLabs>forTableColumn());


			getData();
		 
			bt_add.setOnAction((ActionEvent e) -> {
	            DentalLabs rc; 
	            int i=0,flag=0;
	            while(i< data.size()){
	            	if(data.get(i).getName().toString().equalsIgnoreCase(textfield_Name.getText())){
	            		flag=1;
	            		break;
	            	}
	            	++i;
	            }
	            if(flag==0){
	            	rc = new DentalLabs(textfield_Name.getText(),textfield_Address.getText(),textfield_Num.getText(),textfield_Date.getText(),textfield_Duration.getText(),textfield_Cost.getText().toString());
	            	data.add(rc);
	            	insertData(rc);
	            }
	            else{
	            	System.out.println("The entered information is already in the data base");
	            	
	            }
	            textfield_Name.clear();
	            textfield_Address.clear();
	            textfield_Num.clear();
	            textfield_Date.clear();
	            textfield_Duration.clear();
	            textfield_Cost.clear();
	        });
			
			col_ContactNum.setOnEditCommit((CellEditEvent<DentalLabs, String> t) -> {((DentalLabs) t.getTableView().getItems().get(t.getTablePosition().getRow())).setContact_Num(t.getNewValue());
			updateContact_Num( t.getRowValue().getName(),t.getNewValue());
	        		});
			
			col_Address.setOnEditCommit((CellEditEvent<DentalLabs, String> t) -> {((DentalLabs) t.getTableView().getItems().get(t.getTablePosition().getRow())).setAddress(t.getNewValue());
			updateAddress( t.getRowValue().getName(),t.getNewValue());
			});
			
			col_DateSent.setOnEditCommit((CellEditEvent<DentalLabs, String> t) -> {((DentalLabs) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDateSent(t.getNewValue());
			updateDateSent( t.getRowValue().getName(),t.getNewValue());
	        });
			
			col_ExpectedDuration.setOnEditCommit((CellEditEvent<DentalLabs, String> t) -> {((DentalLabs) t.getTableView().getItems().get(t.getTablePosition().getRow())).setExpectedDuration(t.getNewValue());
			updateExpectedDuration( t.getRowValue().getName(),t.getNewValue());
	        });
			
			col_Cost.setOnEditCommit((CellEditEvent<DentalLabs, String> t) -> {((DentalLabs) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCost(t.getNewValue());
			updateCost( t.getRowValue().getName(),t.getNewValue());
	        });
	        
			bt_delete.setOnAction((ActionEvent e) -> {        	 
	       	 ObservableList<DentalLabs> selectedRows = table_DentalLab.getSelectionModel().getSelectedItems();
	       	ArrayList<DentalLabs> rows = new ArrayList<>(selectedRows);
	       	rows.forEach(row -> {
	       		table_DentalLab.getItems().remove(row); 
	       		deleteRow(row); 
	       		table_DentalLab.refresh();
	       		});   
	       });
			
			bt_refresh.setOnAction((ActionEvent e) -> {        	 
				table_DentalLab.refresh();  
	        	try {
					getData();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	        });
			
			bt_clear.setOnAction((ActionEvent e) -> {
	    		Stage primaryStage = null;
	    		DeleteAll(primaryStage, NONE, table_DentalLab);	
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
					ResultSet rs= conn.createStatement().executeQuery("select * from DentalLabs order by Name");
					while ( rs.next() ) 
						data.add(new DentalLabs(rs.getString(1),rs.getString(2),rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)));
					
					conn.close();
					
				}
				catch(SQLException ex){
					System.err.println("Error"+ex);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				table_DentalLab.setItems(null);
				table_DentalLab.setItems(data);			
			}
			
			private void insertData(DentalLabs rc) {
				
				try {			
						Connection conn=dc.Connect();
						ExecuteStatement("Insert into DentalLabs (Name, Contact_Num, Address, DateSent , ExpectedDuration, cost) values('"+rc.getName()+"','"+rc.getContact_Num()+"','"+ rc.getAddress() +"','"+ rc.getDateSent() +"','" + rc.getExpectedDuration() + "','"+ rc.getCost() +"')");
						conn.close();
						} catch (SQLException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
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
				
				public void updateContact_Num(String Name, String Contact_Num) {
					try {
						Connection conn=dc.Connect();
						ExecuteStatement("update DentalLabs set Contact_Num = '" +Contact_Num +"' where Name = '"+Name +"';");
						conn.close();						
						} catch (SQLException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
				}
				
				public void updateAddress(String Name, String Address) {
					
					try {
						Connection conn=dc.Connect();
						ExecuteStatement("update DentalLabs set Address = '" +Address +"' where Name = '"+Name +"';");
						conn.close();						
						} catch (SQLException e) {
							e.printStackTrace();
						}  catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
				}
				
                public void updateDateSent(String Name, String DateSent) {
					
					try {
						Connection conn=dc.Connect();
						ExecuteStatement("update DentalLabs set DateSent = '" +DateSent +"' where Name = '"+Name +"';");
						conn.close();						
						} catch (SQLException e) {
							e.printStackTrace();
						}  catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
				}
                
                 public void updateExpectedDuration(String Name, String ExpectedDuration) {
					
					try {
						Connection conn=dc.Connect();
						ExecuteStatement("update DentalLabs set  ExpectedDuration = '" +ExpectedDuration +"' where Name = '"+Name +"';");
						conn.close();						
						} catch (SQLException e) {
							e.printStackTrace();
						}  catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
				}
                 
                 public void updateCost(String Name, String Cost) {
 					
 					try {
 						Connection conn=dc.Connect();
 						ExecuteStatement("update DentalLabs set  Cost = '" +Cost +"' where Name = '"+Name +"';");
 						conn.close(); 						
 						} catch (SQLException e) {
 							e.printStackTrace();
 						}  catch (ClassNotFoundException e) {
 							e.printStackTrace();
 						}
 				}
                 
             	private void deleteRow(DentalLabs row) {
             		// TODO Auto-generated method stub
             		
             		try {
             			Connection conn=dc.Connect();
             			ExecuteStatement("delete from  DentalLabs where Name='" +row.getName() + "';");
             			conn.close();             			
             			} catch (SQLException e) {
             				e.printStackTrace();
             			} catch (ClassNotFoundException e) {
             				e.printStackTrace();
             			}
             	}

             	private void DeleteAll(Window owner, Modality modality,   TableView<DentalLabs> table) {
             		// Create a Stage with specified owner and modality
             		Stage stage = new Stage();
             		stage.initOwner(owner);
             		stage.initModality(modality);
             	//	Label modalityLabel = new Label(modality.toString());
             		
             		Button yesButton = new Button("Confirm");
             		yesButton.setOnAction(e -> {
             			if(!data.isEmpty()){
             			for (DentalLabs row:data) {
                     			deleteRow(row);
                     			table.refresh();
                     		}
             			}
             			else
             				System.out.println("There is no available dental labs information!");
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

             }
				

				
				
				





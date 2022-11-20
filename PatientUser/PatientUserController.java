package PatientUser;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import MainMenu.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PatientUserController implements Initializable{


    @FXML
    private TextField addPassword;

    @FXML
    private TextField addID;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnGoBack;
    
    @FXML
    private Label textDisplay;
    
    @FXML
    private ImageView imgLogo;

    @FXML
    private ImageView imgEmail;

    @FXML
    private ImageView imgPhone;

    @FXML
    private ImageView imgFacebook;

    @FXML
    private ImageView imgLock;
    
    private ObservableList<patientuser> data;
	private DbConnection dc;
	public static String id;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
		dc=new DbConnection();
		try{
			Image lock = new Image("/Images/lock.png");
			imgLock.setImage(lock);
			Image phone = new Image("/Images/phone.png");
			imgPhone.setImage(phone);
			Image facebook = new Image("/Images/facebook.png");
			imgFacebook.setImage(facebook);
			Image email = new Image("/Images/email.png");
			imgEmail.setImage(email);
			Image logo = new Image("/Images/logo.png");
			imgLogo.setImage(logo);
			getData();
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
			ResultSet rs= conn.createStatement().executeQuery("select patientID_num,p_password from patient");
			while ( rs.next() ) 
				data.add(new patientuser(rs.getString(1),rs.getString(2)));	
			conn.close();			
		}
		catch(SQLException ex){
			System.err.println("Error"+ex);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void Login(ActionEvent e) throws IOException, InterruptedException{
		
		if( addID.getText().isEmpty() ==  false && addPassword.getText().isEmpty() == false){
			int flag=0;
			for(int i=0;i<data.size();++i){
				if(data.get(i).getID().contentEquals(addID.getText()) && data.get(i).getPassword().contentEquals(addPassword.getText())){
					flag=1;
					id=addID.getText();
					break;
				}
			}
			if (flag==1){
					Stage stage=(Stage) addID.getScene().getWindow();
					Parent root= FXMLLoader.load(getClass().getResource("/PatientUser/page2.fxml"));
					Scene scene= new Scene(root);
					stage.setScene(scene);
					stage.show();
				
			}
			else
				textDisplay.setText("Wrong ID or password!");
		}
		else if( addID.getText().isEmpty() ==  true && addPassword.getText().isEmpty() == false)
			textDisplay.setText("Please enter the ID!");
		else if( addID.getText().isEmpty() ==  false && addPassword.getText().isEmpty() == true)
			textDisplay.setText("Please enter the password!");
		else
			textDisplay.setText("Please enter ID and password!");
		
		addID.clear();
		addPassword.clear();
	}
	
	@FXML
	private void GoBack(ActionEvent e) throws IOException {
		Stage stage =(Stage) btnGoBack.getScene().getWindow();
		Parent root= FXMLLoader.load(getClass().getResource("/MainMenu/MainPage.fxml"));
		Scene scene= new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
}

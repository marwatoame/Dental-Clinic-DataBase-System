package DoctorUser;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DoctorUser.doctoruser;
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

public class DoctorUserController implements Initializable{


    @FXML
    private TextField addPassword;

    @FXML
    private TextField addUsername;

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
    
    private ObservableList<doctoruser> data;
	private DbConnection dc;
	
	public static String username;
	public static String password;
	
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
			ResultSet rs= conn.createStatement().executeQuery("select username,password1 from doctorUser order by username");
			while ( rs.next() ) 
				data.add(new doctoruser(rs.getString(1),rs.getString(2)));	
			conn.close();		
			username=data.get(0).getUsername();
			password=data.get(0).getPassword();
		}
		catch(SQLException ex){
			System.err.println("Error"+ex);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	private void Login(ActionEvent e) throws IOException, InterruptedException{
		
		if( addUsername.getText().isEmpty() ==  false && addPassword.getText().isEmpty() == false){
			if(data.get(0).getUsername().contentEquals(addUsername.getText()) && data.get(0).getPassword().contentEquals(addPassword.getText())){
				Stage stage=(Stage) addUsername.getScene().getWindow();
				Parent root= FXMLLoader.load(getClass().getResource("/MainMenu/MainMenu.fxml"));
				Scene scene= new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
			else
				textDisplay.setText("Wrong username or password!");
		}
		else if( addUsername.getText().isEmpty() ==  true && addPassword.getText().isEmpty() == false)
			textDisplay.setText("Please enter the username!");
		else if( addUsername.getText().isEmpty() ==  false && addPassword.getText().isEmpty() == true)
			textDisplay.setText("Please enter the password!");
		else
			textDisplay.setText("Please enter username and password!");
		
		addUsername.clear();
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

package DoctorUser;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import MainMenu.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class changeUsernamePass implements Initializable{

	@FXML
    private TextField addPassword;

    @FXML
    private TextField addUsername;

    @FXML
    private Button btnChange;
    
    @FXML
    private Label textDisplay;
    
    @FXML
    private ImageView imgLogo;

    private DbConnection dc;
    
    String username;
    String password;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    	username=DoctorUserController.username;
		password=DoctorUserController.password;
		dc=new DbConnection();
		Image logo = new Image("/Images/logo.png");
		imgLogo.setImage(logo);  
	}
    
    public void ExecuteStatement(String SQL) throws SQLException, ClassNotFoundException {

		try {
			Connection conn=dc.Connect();
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();			 
		}
		catch(SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!"); 
		}  
	}
	
    @FXML
	private void Change(ActionEvent e) {
		
		if( addUsername.getText().isEmpty() ==  false && addPassword.getText().isEmpty() == false){
				
			try {
				Connection conn=dc.Connect();
				ExecuteStatement("delete from  doctorUser where username ='" +username + "';");
				ExecuteStatement("insert into  doctorUser values('"+addUsername.getText()+"','"+ addPassword.getText()+"')");
				textDisplay.setText("Username and password have been changed!");
				conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}catch (NullPointerException e1) {
					e1.printStackTrace();
				}
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
    
}

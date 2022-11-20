package PatientUser;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import MainMenu.DbConnection;
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

public class changePassword implements Initializable{
	
	@FXML
    private TextField addPassword;

    @FXML
    private Button btnChange;
    
    @FXML
    private Button btnBack;
    
    @FXML
    private Label textDisplay;
    
    @FXML
    private ImageView imgLogo;

    private DbConnection dc;
    
    String idNum;
    
    @Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
    	idNum= PatientUserController.id;
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
		
		if( addPassword.getText().isEmpty() == false){
				
			try {
				Connection conn=dc.Connect();
				ExecuteStatement("update patient set p_password ='" +addPassword.getText() + "'where patientID_num ='" +idNum+"'");
				textDisplay.setText("Password has been changed!");
				conn.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				}catch (NullPointerException e1) {
					e1.printStackTrace();
				}
		}
		else
			textDisplay.setText("Please enter the password!");
		addPassword.clear();
	}   
    @FXML
	private void GoBack(ActionEvent e) throws IOException {
		Stage stage =(Stage) btnBack.getScene().getWindow();
		Parent root= FXMLLoader.load(getClass().getResource("/PatientUser/page2.fxml"));
		Scene scene= new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
}
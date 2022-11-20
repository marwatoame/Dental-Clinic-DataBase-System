package MainMenu;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class MainPageController implements Initializable{
	
	    @FXML
	    private ImageView imgLogo;

	    @FXML
	    private ImageView imgEmail;

	    @FXML
	    private ImageView imgPhone;

	    @FXML
	    private ImageView imgFacebook;

	    @FXML
	    private Button btnPatient;

	    @FXML
	    private Button btnDoctor;

	    @FXML
	    private ImageView imgDoctor;

	    @FXML
	    private ImageView imgPatient;
	    
	    @FXML
	    private ImageView imgTeeth;


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		Image teeth = new Image("/Images/marvel1.png");
		imgTeeth.setImage(teeth);
		Image patient = new Image("/Images/person.png");
		imgPatient.setImage(patient);
		Image doctor = new Image("/Images/Doctor.png");
		imgDoctor.setImage(doctor);
		Image phone = new Image("/Images/phone.png");
		imgPhone.setImage(phone);
		Image facebook = new Image("/Images/facebook.png");
		imgFacebook.setImage(facebook);
		Image email = new Image("/Images/email.png");
		imgEmail.setImage(email);
		Image logo = new Image("/Images/logo.png");
		imgLogo.setImage(logo);
		
	}
	
	@FXML
	private void gotoDoctor(ActionEvent e) throws IOException {
		Stage stage =(Stage) btnDoctor.getScene().getWindow();
		Parent root= FXMLLoader.load(getClass().getResource("/DoctorUser/DoctorUser.fxml"));
		Scene scene= new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void gotoPatient(ActionEvent e) throws IOException {
		Stage stage =(Stage) btnPatient.getScene().getWindow();
		Parent root= FXMLLoader.load(getClass().getResource("/PatientUser/PatientUser.fxml"));
		Scene scene= new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}

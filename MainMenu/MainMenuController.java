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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainMenuController implements Initializable{

		@FXML
	    private Button btnPatients;
	    
	    @FXML
	    private BorderPane borderPane;
	    
	    @FXML
	    private Button btnMainMenu;

	 /*   @FXML
	    private Button btnTreatment;
	    */
	    @FXML
	    private Button btnDoctors;
	    
	    @FXML
	    private Button btnSpecialists;
	    
	 	@FXML
	    private Button btnAppointment;

	    @FXML
	    private Button btnTeeth;

	    @FXML
	    private Button btnPayment;

	    @FXML
	    private Button btnDebt;

	    @FXML
	    private Button btnReport;
  
	    @FXML
	    private ImageView imgLogo;
	    
	    @FXML
	    private Button btnChange;
	    
	    @FXML
	    private Button btnDentalLabs;

		@Override
		public void initialize(URL location, ResourceBundle resources) {
			Image logo = new Image("/Images/logo.png");
			imgLogo.setImage(logo);
			//AnchorPane Main2 = FXMLLoader.load(getClass().getResource("Mainscreen.fxml"));
			//borderPane.setCenter(Main2);
			
		}
		public void goToPatients(ActionEvent event) throws IOException{
			AnchorPane Main = FXMLLoader.load(getClass().getResource("/patient/Main.fxml"));
			borderPane.setCenter(Main);
		}
		/*public void goToTreatment(ActionEvent event) throws IOException{
			AnchorPane Main = FXMLLoader.load(getClass().getResource("/Treatment/treatmentMenu.fxml"));
			borderPane.setCenter(Main);
		}*/
		public void goToDoctors(ActionEvent event) throws IOException{
			AnchorPane Main = FXMLLoader.load(getClass().getResource("/Doctors/doctorsMenu.fxml"));
			borderPane.setCenter(Main);
		}
		public void goToSpecialists(ActionEvent event) throws IOException{
			AnchorPane Main = FXMLLoader.load(getClass().getResource("/Specialist/specialistMenu.fxml"));
			borderPane.setCenter(Main);
		}
		public void goToDentalLabs(ActionEvent event) throws IOException{
			AnchorPane Main = FXMLLoader.load(getClass().getResource("/DentalLabs/DentalLabs.fxml"));
			borderPane.setCenter(Main);
		}
		public void goToTeeth(ActionEvent event) throws IOException{
			AnchorPane Main = FXMLLoader.load(getClass().getResource("/Teeth/Teeth.fxml"));
			borderPane.setCenter(Main);
		}
		public void goToDebt(ActionEvent event) throws IOException{
			AnchorPane Main = FXMLLoader.load(getClass().getResource("/debt/debt.fxml"));
			borderPane.setCenter(Main);
		}
		public void goToReport(ActionEvent event) throws IOException{
			AnchorPane Main = FXMLLoader.load(getClass().getResource("/report/report.fxml"));
			borderPane.setCenter(Main);
		}
		public void goToPayment(ActionEvent event) throws IOException{
			AnchorPane Main = FXMLLoader.load(getClass().getResource("/payment/payment.fxml"));
			borderPane.setCenter(Main);
		}
		public void goToAppointment(ActionEvent event) throws IOException{
			AnchorPane Main = FXMLLoader.load(getClass().getResource("/Appointment/appointment.fxml"));
			borderPane.setCenter(Main);
		}
		
		
		@FXML
		private void goToMainMenu(ActionEvent e) throws IOException {
			Stage stage =(Stage) btnMainMenu.getScene().getWindow();
			Parent root= FXMLLoader.load(getClass().getResource("/MainMenu/MainPage.fxml"));
			Scene scene= new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		
		public void goToChange(ActionEvent event) throws IOException{
			AnchorPane Main = FXMLLoader.load(getClass().getResource("/DoctorUser/changeUsernamePass.fxml"));
			borderPane.setCenter(Main);
		}
}

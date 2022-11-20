package PatientUser;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Doctors.doctors;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class page2Controller implements Initializable{
	
	@FXML
	private Button btnBack;
    @FXML
    private Label password;

    @FXML
    private Label record;

    @FXML
    private Label name;

    @FXML
    private Label ID;

    @FXML
    private Label number;

    @FXML
    private Label age;

    @FXML
    private ImageView imgLogo;
    
    @FXML
    private TableView<patientPayments> table_payments;

    @FXML
    private TableColumn<patientPayments, String> ColumnValue;

    @FXML
    private TableColumn<patientPayments, String> columnCurrency;

    @FXML
    private TableColumn<patientPayments, String> columnMethod;

    @FXML
    private TableColumn<patientPayments, String> columnDate;

    @FXML
    private TableView<patientTeeth> table_teeth;

    @FXML
    private TableColumn<patientTeeth, String> columnTooth;

    @FXML
    private TableColumn<patientTeeth, String> columnConditions;

    @FXML
    private TableColumn<patientTeeth, String> columnTreatment;

    @FXML
    private TableColumn<patientTeeth, String> columnCost;

    @FXML
    private TableView<PatientAppointment> table_appointments;
    @FXML
    private TableColumn<PatientAppointment, String> colAppDate;

    @FXML
    private TableColumn<PatientAppointment, String> colTiming;

    @FXML
    private TableColumn<PatientAppointment, String> colDoctor;

    @FXML
    private TableColumn<PatientAppointment, String> colSpecialist;


    
    private DbConnection dc;
    private ObservableList<String> data1;
    private ObservableList<PatientAppointment> appointmentData;
    private ObservableList<patientTeeth> teethData;
    private ObservableList<patientPayments> paymentData;


    
    String idNum;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		dc=new DbConnection();
		try{
			

			colAppDate.setCellValueFactory(new PropertyValueFactory<>("app_date"));
			colTiming.setCellValueFactory(new PropertyValueFactory<>("timing"));
			colDoctor.setCellValueFactory(new PropertyValueFactory<>("doctor"));
			colSpecialist.setCellValueFactory(new PropertyValueFactory<>("specialist"));
			
			columnTooth.setCellValueFactory(new PropertyValueFactory<>("toothNumber"));
			columnConditions.setCellValueFactory(new PropertyValueFactory<>("condition"));
			columnTreatment.setCellValueFactory(new PropertyValueFactory<>("treatment"));
			columnCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
			
			ColumnValue.setCellValueFactory(new PropertyValueFactory<>("value"));
			columnCurrency.setCellValueFactory(new PropertyValueFactory<>("currency"));
			columnMethod.setCellValueFactory(new PropertyValueFactory<>("method"));
			columnDate.setCellValueFactory(new PropertyValueFactory<>("date"));
			
			idNum= PatientUserController.id;
			Image logo = new Image("/Images/logo.png");
			imgLogo.setImage(logo);
			getData();
			addAppointments() ;
			addTeeth();
			addPayment();


			//getPaymentData();
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
			data1=FXCollections.observableArrayList();
			ResultSet rs= conn.createStatement().executeQuery("select * from patient where patientID_num ='" +idNum + "';");
			if (rs.next()){
				data1.add(rs.getString(1));	
				data1.add(rs.getString(2));
				data1.add(rs.getString(3));
				data1.add(rs.getString(4));
				data1.add(rs.getString(5));
				data1.add(rs.getString(6));
			}
			conn.close();	
			ID.setText(data1.get(0));
			name.setText(data1.get(1));
			number.setText(data1.get(2));
			age.setText(data1.get(3));
			record.setText(data1.get(4));
			password.setText(data1.get(5));
		}
		catch(SQLException ex){
			System.err.println("Error"+ex);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
/*	private void getPaymentData() throws SQLException,ClassNotFoundException  {
		// TODO Auto-generated method stub
		
		try{
			Connection conn=dc.Connect();
			paymentData=FXCollections.observableArrayList();
			ResultSet rs= conn.createStatement().executeQuery("select pvalue,currency,method,date_of_payment from patient_pays_payment where patientID_num ='" +idNum + "';");
			if (rs.next()){
				paymentData.add(rs.getString(1));	
				paymentData.add(rs.getString(2));
				paymentData.add(rs.getString(3));
				paymentData.add(rs.getString(4));
			}
			conn.close();	
			value.setText(data1.get(0));
			currency.setText(data1.get(1));
			method.setText(data1.get(2));
			date.setText(data1.get(3));
		}
		catch(SQLException ex){
			System.err.println("Error"+ex);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}*/
	
	public void addAppointments() {
		appointmentData = FXCollections.observableArrayList();

		try {
			Connection conn = dc.Connect();
			ResultSet rs = conn.createStatement().executeQuery("select app_date,timing, DID, SID from doctor_has_appointment where PID ='" +idNum+ "';");
			while ( rs.next()) {
				appointmentData.add(new PatientAppointment(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));	
			}	
			for(int i=0;i<appointmentData.size();++i){
				rs = conn.createStatement().executeQuery("select SName from specialist where IDNum ='" +appointmentData.get(i).getSpecialist()+ "';");
				while(rs.next())
					appointmentData.get(i).setSpecialist(rs.getString(1));
				rs = conn.createStatement().executeQuery("select Doctor_Name from Doctors where ID_Number ='" +appointmentData.get(i).getDoctor()+ "';");
				while(rs.next())
					appointmentData.get(i).setDoctor(rs.getString(1));
			}
			conn.close();

		}
		catch(SQLException | ClassNotFoundException ex) {
			System.err.println("Error"+ex);
		}	
		table_appointments.setItems(null);
		table_appointments.setItems(appointmentData);		
	}

	public void addTeeth() {
		teethData = FXCollections.observableArrayList();

		try {
			Connection conn = dc.Connect();
			ResultSet rs = conn.createStatement().executeQuery("select tooth_number,tooth_condition, tooth_treatment, Cost from teeth where patientID_num ='" +idNum+ "';");
			while ( rs.next()) {
				teethData.add(new patientTeeth(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));	

			}	

		}
		
		catch(SQLException | ClassNotFoundException ex) {
			System.err.println("Error"+ex);
		}	
		table_teeth.setItems(teethData);		
	}

	public void addPayment() {
		paymentData = FXCollections.observableArrayList();

		try {
			Connection conn = dc.Connect();
			ResultSet rs= conn.createStatement().executeQuery("select pvalue,currency,method,date_of_payment from patient_pays_payment where patientID_num ='" +idNum + "';");
			while ( rs.next()) {
				paymentData.add(new patientPayments(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));	

			}	

		}
		
		catch(SQLException | ClassNotFoundException ex) {
			System.err.println("Error"+ex);
		}	
		table_payments.setItems(paymentData);		
	}

	
	@FXML
	private void GoBack(ActionEvent e) throws IOException {
		Stage stage =(Stage) btnBack.getScene().getWindow();
		Parent root= FXMLLoader.load(getClass().getResource("/MainMenu/MainPage.fxml"));
		Scene scene= new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	private void ChangePassword(ActionEvent e) throws IOException {
		Stage stage =(Stage) btnBack.getScene().getWindow();
		Parent root= FXMLLoader.load(getClass().getResource("/PatientUser/changePassword.fxml"));
		Scene scene= new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

}

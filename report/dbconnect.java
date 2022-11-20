package report;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;

public class dbconnect {

	public Connection Connect() throws ClassNotFoundException {
		
		try {

			String url = "jdbc:mysql://localhost:3306/DentalClinic?useSSL=false";
			String user = "root";
			String password = "root1234";
			 Connection conn = DriverManager.getConnection(url,user,password) ;
			 return conn;
		}
		
		catch( SQLException ex) {
			
			Logger.getLogger(dbconnect.class.getName()).log(Level.SEVERE,null,ex);
		}
		return null;
	}

}
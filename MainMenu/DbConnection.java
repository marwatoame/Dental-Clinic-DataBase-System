package MainMenu;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class DbConnection {
	
	
	
	public Connection Connect() throws ClassNotFoundException, SQLException {
		try{
			String dbUsername = "root";
			String dbPassword = "root1234";
			String dbURL = "127.0.0.1";
			String port = "3306";
			String dbName = "simpledb";
			String url="jdbc:mysql://" + dbURL + ":" + port + "/" + dbName + "?verifyServerCertificate=false&useSSL=false";
			
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url,dbUsername,dbPassword);
			return conn;
		}
		catch(ClassNotFoundException | SQLException ex){
			Logger.getLogger(DbConnection.class.getName()).log(Level.SEVERE,null,ex);
		}
		return null;
	}

}
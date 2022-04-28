import java.sql.*;
import javax.swing.*;

public class SQLiteConnection {
	Connection conn=null;

	public static Connection dbConnector(){
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:G:\\Code\\Java\\SQLite\\employee.sqlite");
			JOptionPane.showMessageDialog(null, "connection successfull");
			return conn;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}
	
}

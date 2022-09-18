package DBHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;

import Controller.LibraryController;

public class ConnectionGenerator {

	// private constructor
	private static Connection con;
	
	private static void createConnection() {
		try {

			Class.forName("org.sqlite.JDBC");

			con = DriverManager.getConnection("jdbc:sqlite:C:\\SQLite\\database\\Library.db");
		} catch (ClassNotFoundException e) {
			System.out.println("SQL Driver not found [" + e + "]");
		} catch (SQLException e) {
			System.out.println("SQL Exception [" + e + "]");
		}
	}

	/**
     * To create a connectionInstance but if 
     * the connectionInstance alr exists then just return that connectionInstance
     * Applies Singleton
     * static method 
     * @return Connection instance
     */
	public static Connection getConnection() {
		if (con == null) {
			createConnection();
		}
		return con;
	}
}

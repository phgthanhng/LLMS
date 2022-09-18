package UnitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import DBHelper.ConnectionGenerator;

class searchTitle {
	
	private static Connection con = ConnectionGenerator.getConnection();

	@Test
	void test() {
		String output = "";
		String expected = "SN = 0143105426, Title = Pride and Prejudice, Author = Jane Austen, Publisher = Penguin Classics";
		String title = "Pride and Prejudice";
		try {
			String sql = "SELECT * FROM Books WHERE Title = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, title);
			ResultSet rs = stm.executeQuery();
			output = "SN = " + rs.getString("SN") + ", Title = " + rs.getString("Title") + ", Author = " + rs.getString("Author") 
				+ ", Publisher = " + rs.getString("Publisher");
			rs.close();
			stm.close();
			con.close();
		} catch(SQLException e) {
			System.out.println("Error Retrieving Data [" + e + "]");
		}
		assertEquals(expected, output);
	}

}

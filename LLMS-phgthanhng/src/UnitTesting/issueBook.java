package UnitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import DBHelper.ConnectionGenerator;

/**
 * To test method issuedBook and borrowBook
 * @author PhuongThanh
 *
 */
class issueBook {
	
	private static Connection con = ConnectionGenerator.getConnection();

	@Test
	void test() {
		String output = "";
		String expected = "0733325831, 3028";
		String sn = "0733325831";
		try {
			String sql = "SELECT * FROM IssuedBooks WHERE SN = ? AND ReturnDate IS NULL";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, sn);
			ResultSet rs = stm.executeQuery();
			output = rs.getString("SN") + ", " + rs.getInt("StudentId");
			rs.close();
			stm.close();
			con.close();
		} catch(SQLException e) {
			System.out.println("Error Retrieving Data [" + e + "]");
		}
		assertEquals(expected, output);
	}
}

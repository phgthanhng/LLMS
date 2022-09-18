package UnitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import DBHelper.ConnectionGenerator;

/**
 * To test method returnBook and toReturnBook
 * @author PhuongThanh
 *
 */
class returnBook {

	private static Connection con = ConnectionGenerator.getConnection();

	@Test
	void test() {
		String output = "";
		String expected = "0733325831";
		try {
			String sql = "SELECT * FROM IssuedBooks WHERE SN = ?";
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, expected);
			ResultSet rs = stm.executeQuery();
			output = rs.getString("SN");
			rs.close();
			stm.close();
			con.close();
		} catch(SQLException e) {
			System.out.println("Error Retrieving Data [" + e + "]");
		}
		assertEquals(expected, output);
	}
}

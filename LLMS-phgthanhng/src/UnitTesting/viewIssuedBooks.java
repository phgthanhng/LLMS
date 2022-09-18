package UnitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import DBHelper.ConnectionGenerator;
/**
 * to test method viewIssuedBook in LibrarianDAO
 * @author PhuongThanh
 *
 */
class viewIssuedBooks {

	private static Connection con = ConnectionGenerator.getConnection();

	@Test
	void test() {
		String output = "";
		String expected = "SN = 0143105426, Title = Pride and Prejudice, Author = Jane Austen, Student ID = 1045, Issued Date = 2021-12-11\n"
				+ "SN = 0143105426, Title = Pride and Prejudice, Author = Jane Austen, Student ID = 2677, Issued Date = 2021-12-13\n"
				+ "SN = 0733325831, Title = All the Colours of Paradise, Author = Glenda Millard, Student ID = 3028, Issued Date = 2021-12-13\n"
				+ "SN = 0714819697, Title = The illuminated manuscript, Author = Janet Backhouse, Student ID = 5052, Issued Date = 2021-12-13\n";
		try {
			String sql = "SELECT b.SN, b.Title, b.Author, I.StudentId, I.IssuedDate, I.ReturnDate FROM Books b Join IssuedBooks I ON b.SN = I.SN WHERE I.ReturnDate IS NULL";
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while (rs.next())
				output += "SN = " + rs.getString("SN") + ", Title = " + rs.getString("Title") + ", Author = "
						+ rs.getString("Author") + ", Student ID = " + rs.getString("StudentId") + ", Issued Date = " + rs.getString("IssuedDate") + "\n";
			rs.close();
			stm.close();
			con.close();
		} catch(SQLException e) {
			System.out.println("Error Retrieving Data [" + e + "]");
		}
		assertEquals(expected, output);
	}

}

package UnitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import DBHelper.ConnectionGenerator;
import DBHelper.StudentDAO;

class searchAuthor {
	private static Connection con = ConnectionGenerator.getConnection();

	@Test
	void test() {
		String output = "";
		String author = "Stephen King";
		String expected = "SN = 2253260622, Title = L'Outsider (Imaginaire), Author = Stephen King, Publisher = LGF, Added Date = 2021-12-09\n"
				+ "SN = 0965018512, Title = Secret Windows: Essays and Fiction on the Craft of Writing, Author = Stephen King, Publisher = Quality Paperback Bookclub, Added Date = 2020-10-13\n";
		StudentDAO obj = new StudentDAO();
		output = obj.searchAuthor(author).toString();
				
//		try {
//			String sql = "SELECT * FROM Books WHERE Author = ?";
//			PreparedStatement stm = con.prepareStatement(sql);
//			stm.setString(1, author);
//			ResultSet rs = stm.executeQuery();
//			while (rs.next()) {
//				output += "SN = " + rs.getString("SN") + ", Title = " + rs.getString("Title") 
//				+ ", Author = " + rs.getString("Author") + ", Publisher = " + rs.getString("Publisher") + ", Added Date = " + rs.getString("AddedDate");
//				System.out.println("\n");
//			}
//			rs.close();
//			stm.close();
//			con.close();
//		} catch(SQLException e) {
//			System.out.println("Error Retrieving Data [" + e + "]");
//		}
		assertEquals(expected, output);
	}

}

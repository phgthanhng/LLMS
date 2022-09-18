package UnitTesting;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import DBHelper.ConnectionGenerator;

/**
 * to test method viewCatalog in StudentDAO
 * @author PhuongThanh
 *
 */
class viewCatalog {

	private static Connection con = ConnectionGenerator.getConnection();

	@Test
	void test() {
		String output = "";
		String expected = "SN = 2253260622, Title = L'Outsider (Imaginaire), Author = Stephen King, Publisher = LGF, Quantity = 3\n"
				+ "SN = 0714819697, Title = The illuminated manuscript, Author = Janet Backhouse, Publisher = Phaidon, Quantity = 5\n"
				+ "SN = 006201563X, Title = Sense and Sensibility, Author = Jane Austen, Publisher = HarperTeen, Quantity = 11\n"
				+ "SN = 0965018512, Title = Secret Windows: Essays and Fiction on the Craft of Writing, Author = Stephen King, Publisher = Quality Paperback Bookclub, Quantity = 4\n"
				+ "SN = 0143105426, Title = Pride and Prejudice, Author = Jane Austen, Publisher = Penguin Classics, Quantity = 5\n"
				+ "SN = 0733325831, Title = All the Colours of Paradise, Author = Glenda Millard, Publisher = ABC Books, Quantity = 2\n"
				+ "SN = 0143105426, Title = Pride and Prejudice, Student ID = Jane Austen, Issued Date = 2021-12-11\n"
				+ "SN = 0143105426, Title = Pride and Prejudice, Student ID = Jane Austen, Issued Date = 2021-12-13\n"
				+ "SN = 0733325831, Title = All the Colours of Paradise, Student ID = Glenda Millard, Issued Date = 2021-12-13\n"
				+ "SN = 0714819697, Title = The illuminated manuscript, Student ID = Janet Backhouse, Issued Date = 2021-12-13\n";

		try {
			String sql = "SELECT * FROM Books";
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while (rs.next())
				output += "SN = " + rs.getString("SN") + ", Title = " + rs.getString("Title") + ", Author = "
						+ rs.getString("Author") + ", Publisher = " + rs.getString("Publisher") + ", Quantity = "
						+ rs.getString("Quantity") + "\n";
			rs.close();
			stm.close();
		} catch (SQLException e) {
			System.out.println("Error Retrieving Data [" + e + "]");
		}
		try {
			String sql = "SELECT b.SN, b.Title, b.Author, I.StudentId, I.IssuedDate, I.ReturnDate FROM Books b Join IssuedBooks I ON b.SN = I.SN WHERE I.ReturnDate IS NULL";
			PreparedStatement stm = con.prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			while (rs.next())
				output += "SN = " + rs.getString("SN") + ", Title = " + rs.getString("Title") + ", Student ID = "
						+ rs.getString("Author") + ", Issued Date = " + rs.getString("IssuedDate") + "\n";
			rs.close();
			stm.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("Error Retrieving Data [" + e + "]");
		}
		assertEquals(expected, output);
	}

}

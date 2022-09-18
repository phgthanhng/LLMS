package DBHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import Controller.LibraryController;
import Model.BooksModel;
import Model.IssuedBooksModel;
import Model.StudentModel;
import View.mainView;

public class StudentDAO {
	private static Connection con;

	public static Locale currentLocale = new Locale("en", "US");
	public static ResourceBundle messages = ResourceBundle.getBundle("Source", currentLocale);

	public StudentDAO() {
		setCon(ConnectionGenerator.getConnection());
	}

	/**
	 * 
	 * to get records from Books title by searching the title
	 * 
	 * @return return records from Books table that satisfy the criteria as a list
	 *         sorted by "SN"
	 */
	public List<BooksModel> searchTitle(String title) {
		currentLocale = mainView.getCurrentLocale();
		messages = mainView.getMessages();
		List<BooksModel> list = new ArrayList<>();
		try {
			String sql = "SELECT SN, Title, Author, Publisher FROM Books WHERE Title LIKE ?";

			PreparedStatement stm = getCon().prepareStatement(sql);
			stm.setString(1, "%" + title + "%");
			ResultSet rs = stm.executeQuery();
			BooksModel book = null;
			while (rs.next()) {
				list.add(book);
				String row = messages.getString("sn") + " = " + rs.getString("SN") + ", " + messages.getString("title")
						+ " = " + rs.getString("Title") + ", " + messages.getString("author") + " = "
						+ rs.getString("Author") + ", " + messages.getString("publisher") + " = "
						+ rs.getString("Publisher");
				System.out.println(row);
			}
			rs.close();
			stm.close();

			return list;
		} catch (SQLException e) {
			System.out.println(messages.getString("retrievingErr") + " [" + e.getMessage() + "]");
			return null;
		}
	}

	/**
	 * to get records from Books table by searching the author
	 * 
	 * @return records from Books table that satisfy the criteria as a list sorted
	 *         by "SN"
	 */
	public List<BooksModel> searchAuthor(String author) {
		currentLocale = mainView.getCurrentLocale();
		messages = mainView.getMessages();
		List<BooksModel> list = new ArrayList<>();
		try {
			String sql = "SELECT SN, Title, Author, Publisher, AddedDate FROM Books WHERE Author LIKE ?";
			PreparedStatement stm = getCon().prepareStatement(sql);
			stm.setString(1, "%" + author + "%");
			ResultSet rs = stm.executeQuery();
			BooksModel book = null;
			while (rs.next()) {
				list.add(book);
				String row = messages.getString("sn") + " = " + rs.getString("SN") + ", " + messages.getString("title")
						+ " = " + rs.getString("Title") + ", " + messages.getString("author") + " = "
						+ rs.getString("Author") + ", " + messages.getString("publisher") + " = "
						+ rs.getString("Publisher") + ", " + messages.getString("addedDate") + " = "
						+ rs.getString("AddedDate");

				System.out.println(row);
			}
			rs.close();
			stm.close();

			return list;
		} catch (SQLException e) {
			System.out.println(messages.getString("retrievingErr") + " [" + e.getMessage() + "]");
			return null;
		}
	}

	/**
	 * To issue a book to a student, student information should be verified first.
	 * if the book is available, quantity will be decreased by one and the number of
	 * issued copies will be decreased by one the corresponding record in
	 * issuedBooks table is deleted from the table
	 * 
	 * @param book
	 * @return true if success
	 */
	public boolean borrow(BooksModel book, StudentModel student) {
		currentLocale = mainView.getCurrentLocale();
		messages = mainView.getMessages();
		Scanner console = new Scanner(System.in);

		// check if student ID is valid
		while (checkStudentID(student.getStdId()) == false)
			student.setStdId(console.nextInt());

		// check if book's serial number if valid
		while (checkAvaiBook(book.getSn()) == false)
			book.setSn(console.next());

		try {
			con.setAutoCommit(false);

			// Adding issued book to table IssuedBooks
			String issueBook = "INSERT INTO IssuedBooks (SN, StudentId, IssuedDate) "
					+ "SELECT b.SN, s.StudentId, DATE() FROM Books b, Students s WHERE b.SN = ? AND s.StudentId = ?";
			PreparedStatement stm = getCon().prepareStatement(issueBook);
			stm.setString(1, book.getSn());
			stm.setInt(2, student.getStdId());
			stm.executeUpdate();
			stm.close();

			// decrease quantity of the issued book by 1
			String updateQ = "UPDATE Books SET Quantity = Quantity - 1 WHERE SN = ?";
			PreparedStatement newStm = getCon().prepareStatement(updateQ);
			newStm.setString(1, book.getSn());
			newStm.executeUpdate();

			newStm.close();
			con.commit();
			System.out.println(messages.getString("borrowSuccess"));
			return true;
		} catch (SQLException e) {
			System.out.println(messages.getString("issueingErr") + " [" + e.getMessage() + "]");
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println(messages.getString("rollbackErr") + " [" + e1.getMessage() + "]");
			}
			return false;
		}
	}

	/**
	 * to return a book check if the issued book and student information is correct,
	 * then increase "quantity" of the book by 1 decrease the number of issued
	 * copies by 1 the corresponding record in IssuedBooks table is deleted from the
	 * table
	 * 
	 * @param book
	 * @return true if success
	 */
	public boolean returnBook(BooksModel book, StudentModel student) {
		currentLocale = mainView.getCurrentLocale();
		messages = mainView.getMessages();
		Scanner console = new Scanner(System.in);

		// check if student ID is valid
		while (checkStudentID(student.getStdId()) == false)
			student.setStdId(console.nextInt());

		// check if book's serial number if valid
		while (checkAvaiBook(book.getSn()) == false)
			book.setSn(console.next());

		try {
			con.setAutoCommit(false);

			// Adding reteurnDate of the book to table IssuedBooks
			String returnBook = "UPDATE IssuedBooks SET ReturnDate = DATE() WHERE SN = ? AND StudentId = ? AND ReturnDate IS NULL";
			PreparedStatement stm = getCon().prepareStatement(returnBook);
			stm.setString(1, book.getSn());
			stm.setInt(2, student.getStdId());
			int rowAffected = stm.executeUpdate();
			stm.close();

			// if there's such book in IssuedBookTable, then update the book's quantity
			if (rowAffected == 1) {
				System.out.println(messages.getString("returnSuccess"));
				// increase quantity of the issued book by 1
				String updateQ = "UPDATE Books SET Quantity = Quantity + 1 WHERE SN = ?";
				PreparedStatement newStm = getCon().prepareStatement(updateQ);
				newStm.setString(1, book.getSn());
				newStm.executeUpdate();

				newStm.close();
				con.commit();
			} else
				System.out.println(messages.getString("invalidBook"));
			return true;
		} catch (SQLException e) {
			System.out.println(messages.getString("returnErr") + " [" + e.getMessage() + "]");
			try {
				con.rollback();
			} catch (SQLException e1) {
				System.out.println(messages.getString("rollbackErr") + " [" + e1.getMessage() + "]");
			}
			return false;
		}
	}

	/**
	 * to view the data from both IssuedBooks table and Books table data from
	 * issuedBooks table contains only books that hasn't been returned
	 * 
	 * @return a map of issuedBooks and Books, sorted by SN
	 */
	public static List<Object> viewCatalog() {
		currentLocale = mainView.getCurrentLocale();
		messages = mainView.getMessages();
		List<Object> catalogue = new ArrayList<>();

		// adding available list to the list
		try {
			String sql = "SELECT * FROM Books";
			PreparedStatement stm = getCon().prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			BooksModel book = null;
			System.out.println(messages.getString("avaiBooks"));
			while (rs.next()) {
				catalogue.add(book);
				String row = messages.getString("sn") + " = " + rs.getString("SN") + ", " + messages.getString("title")
						+ " = " + rs.getString("Title") + ", " + messages.getString("author") + " = "
						+ rs.getString("Author") + ", " + messages.getString("publisher") + " = "
						+ rs.getString("Publisher") + ", " + messages.getString("addedDate") + " = "
						+ rs.getString("AddedDate");
				System.out.println(row);
			}
			rs.close();
			stm.close();
		} catch (SQLException e) {
			System.out.println(messages.getString("retrievingErr") + " [" + e.getMessage() + "]");
		}

		// adding issued books to the list
		try {
			String sql = "SELECT b.SN, b.Title, b.Author, I.StudentId, I.IssuedDate, I.ReturnDate FROM Books b Join IssuedBooks I ON b.SN = I.SN WHERE I.ReturnDate IS NULL";
			PreparedStatement stm = getCon().prepareStatement(sql);
			ResultSet rs = stm.executeQuery();
			IssuedBooksModel issuedBook = null;
			System.out.println("\n" + messages.getString("issueBooks"));
			while (rs.next()) {
				catalogue.add(issuedBook);
				catalogue.add(issuedBook);
				String row = messages.getString("sn") + " = " + rs.getString("SN") + ", " + messages.getString("title")
						+ " = " + rs.getString("Title") + ", " + messages.getString("author") + " = "
						+ rs.getString("Author") + ", " + messages.getString("stdId") + " = "
						+ rs.getString("StudentId") + ", " + messages.getString("issuedDate") + " = "
						+ rs.getString("IssuedDate");
				System.out.println(row);
			}
			rs.close();
			stm.close();
		} catch (SQLException e) {
			System.out.println(messages.getString("retrievingErr") + " [" + e.getMessage() + "]");
		}
		return catalogue;
	}

	/**
	 * to check if student ID is valid
	 * 
	 * @param id
	 * @return true if id is valid, false otherwise
	 */
	public boolean checkStudentID(int id) {
		currentLocale = mainView.getCurrentLocale();
		messages = mainView.getMessages();
		try {
			String sql = "SELECT * FROM Students WHERE StudentId = ?";
			PreparedStatement stm = getCon().prepareStatement(sql);
			stm.setInt(1, id);
			ResultSet rs = stm.executeQuery();
			if (rs.next() == false) {
				System.out.println(messages.getString("invalidStdId"));
				rs.close();
				stm.close();
				return false;
			} else {
				rs.close();
				stm.close();
				return true;
			}
		} catch (SQLException e) {
			System.out.println(messages.getString("retrievingErr") + " [" + e.getMessage() + "]");
			return false;
		}
	}

	/**
	 * to check if book's sn is valid and if there're copies left to borrow
	 * 
	 * @param SN book's serial number
	 * @return true if sn is valid and book's quantity is > 0
	 */
	public boolean checkAvaiBook(String SN) {
		currentLocale = mainView.getCurrentLocale();
		messages = mainView.getMessages();
		try {
			String sql = "SELECT Quantity FROM Books WHERE SN = ?";
			PreparedStatement stm = getCon().prepareStatement(sql);
			stm.setString(1, SN);
			ResultSet rs = stm.executeQuery();
			if (rs.next() == false) {
				System.out.println(messages.getString("invalidSN"));
				rs.close();
				stm.close();
				return false;
			} else if (rs.getInt("Quantity") == 0) {
				System.out.println(messages.getString("unavaiBook"));
				rs.close();
				stm.close();
				return false;
			} else {
				rs.close();
				stm.close();
				return true;
			}
		} catch (SQLException e) {
			System.out.println(messages.getString("retrievingErr") + " [" + e.getMessage() + "]");
			return false;
		}
	}

	public static Connection getCon() {
		return con;
	}

	public static void setCon(Connection con) {
		StudentDAO.con = con;
	}
}

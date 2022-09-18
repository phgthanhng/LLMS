package View;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import Controller.LibraryController;
import DBHelper.LibrarianDAO;
import DBHelper.StudentDAO;
import Model.BooksModel;
import Model.StudentModel;

public class mainView {
	private StudentDAO std = new StudentDAO();
	private LibrarianDAO lib = new LibrarianDAO();
	public static Locale currentLocale = new Locale("en", "US");
	public static ResourceBundle messages = ResourceBundle.getBundle("Source", currentLocale);
	
//	public static void setLang() {
//		currentLocale = LibraryController.currentLocale;
//		messages = LibraryController.messages;
//	}
	
	private void print(String text) {		
		System.out.println(text);
	}
	
	public void exit() {
		print(messages.getString("exit"));
	}
	
	public int language() {
		print("");
		print("Choose your languge:");
		print("1. English");
		print("2. French");
		
		int i = 0;
		Scanner console = new Scanner(System.in);
		i = console.nextInt();
		return i;
	}
	public static Locale getCurrentLocale() {
		return currentLocale;
	}

	public static void setCurrentLocale(Locale currentLocale) {
		mainView.currentLocale = currentLocale;
	}

	public static ResourceBundle getMessages() {
		return messages;
	}

	public static void setMessages(ResourceBundle messages) {
		mainView.messages = messages;
	}

	public int screen1() {
		print("");		
		print(messages.getString("screen1"));
		print(messages.getString("welcome"));
		print(messages.getString("1") + messages.getString("studentOpt"));
		print(messages.getString("2") + messages.getString("librarianOpt"));
		print(messages.getString("3") + messages.getString("exitOpt"));
		
		int i = 0;
		Scanner console = new Scanner(System.in);
		i = console.nextInt();
		return i;
	}
	
	public int stdScreen() {
		print("");
		print( messages.getString("welcome"));
		print(messages.getString("option"));
		print(messages.getString("1") + messages.getString("titleOpt"));
		print(messages.getString("2") + messages.getString("authorOpt"));
		print(messages.getString("3") + messages.getString("catalogOpt"));
		print(messages.getString("4") + messages.getString("borrowOpt"));
		print(messages.getString("5") + messages.getString("returnOpt"));
		print(messages.getString("6") + messages.getString("exitOpt"));
		
		int i = 0;
		Scanner console = new Scanner(System.in);
		i = console.nextInt();
		return i;
	}
	
	public int librarianScreen() {
		
		print("");
		print( messages.getString("welcome"));
		print(messages.getString("option"));
		print(messages.getString("1") + messages.getString("addOpt"));
		print(messages.getString("2") + messages.getString("issueOpt"));
		print(messages.getString("3") + messages.getString("returnOpt"));
		print(messages.getString("4") + messages.getString("viewIssuedOpt"));
		print(messages.getString("5") + messages.getString("exitOpt"));
		
		int i = 0;
		Scanner console = new Scanner(System.in);
		i = console.nextInt();
		return i;

	}
	public void stdScreenFunction() throws SQLException {
		BooksModel book = new BooksModel();
		StudentModel student = new StudentModel();
		Scanner console = new Scanner(System.in);
		int option = stdScreen();
		while (option < 1 || option > 6) {
			System.out.println(messages.getString("invalid"));
			option = stdScreen();
		}
		while (option != 6) {
			if (option == 1) {
				System.out.println(messages.getString("insertTitle"));
				book.setTitle(console.nextLine());
				std.searchTitle(book.getTitle());
			} else if (option == 2) {
				System.out.println(messages.getString("insertAuthor"));
				book.setAuthor(console.nextLine());
				std.searchAuthor(book.getAuthor());
			} else if (option == 3) {
				StudentDAO.viewCatalog();
			} else if (option == 4) {
				System.out.println(messages.getString("insertStdId"));
				student.setStdId(console.nextInt());

				System.out.println(messages.getString("insertSn"));
				console.nextLine();
				book.setSn(console.nextLine());

				std.borrow(book, student);
			} else if (option == 5) {
				System.out.println(messages.getString("insertStdId"));
				student.setStdId(console.nextInt());

				System.out.println(messages.getString("insertSn"));
				console.nextLine();
				book.setSn(console.nextLine());

				std.returnBook(book, student);
			}
			System.out.println("\n" + messages.getString("nextOperation"));
			option = stdScreen();
		}
		if (option == 6) {
			exit();
			StudentDAO.getCon().close();
			System.exit(0);
		}
	}
	
	public void librarianScreenFunction() throws SQLException {
		BooksModel book = new BooksModel();
		StudentModel student = new StudentModel();
		Scanner console = new Scanner(System.in);
		int option = librarianScreen();
		while (option < 1 || option > 5) {
			System.out.println(messages.getString("invalid"));
			option = librarianScreen();
		}

		while (option != 5) {
			// add book
			if (option == 1) {
				System.out.println(messages.getString("insertSn"));
				book.setSn(console.nextLine());
				System.out.println(messages.getString("insertTitle"));
				book.setTitle(console.nextLine());
				System.out.println(messages.getString("insertAuthor"));
				book.setAuthor(console.nextLine());
				System.out.println(messages.getString("insertPub"));
				book.setPublisher(console.nextLine());
				System.out.println(messages.getString("insertQuan"));
				book.setQuantity(console.nextInt());

				lib.addBook(book);

			}
			// issue book
			else if (option == 2) {
				System.out.println(messages.getString("insertStdId"));
				student.setStdId(console.nextInt());
				console.nextLine();

				System.out.println(messages.getString("insertSn"));
				book.setSn(console.nextLine());

				lib.issueBook(book, student);
			}
			// return book
			else if (option == 3) {
				System.out.println(messages.getString("insertStdId"));
				student.setStdId(console.nextInt());
				console.nextLine();

				System.out.println(messages.getString("insertSn"));
				book.setSn(console.nextLine());

				lib.toReturnBook(book, student);
			}
			// view issued books
			else if (option == 4) {
				LibrarianDAO.viewIssuedBooks();
			}
			System.out.println("\n" + messages.getString("nextOperation"));
			option = librarianScreen();
		}
		if (option == 5) {
			exit();
			StudentDAO.getCon().close();
			System.exit(0);
		}
	}
	
	public void chooseLang(int option) {

		String language = "";
		String country = "";

		if (option == 1) {
			language = new String("en");
			country = new String("US");
			mainView.currentLocale = new Locale(language, country);
			mainView.messages = ResourceBundle.getBundle("Source", currentLocale);

		} else if (option == 2) {
			language = new String("fr");
			country = new String("FR");
			mainView.currentLocale = new Locale(language, country);
			mainView.messages = ResourceBundle.getBundle("Source", currentLocale);

		} else {
			System.out.println(messages.getString("invalid"));
			option = language();
		}
	}
}

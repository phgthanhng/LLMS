package Controller;

import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

import Model.BooksModel;
import Model.StudentModel;
import View.mainView;
import DBHelper.LibrarianDAO;
import DBHelper.StudentDAO;

public class LibraryController {

	public mainView view = new mainView();
	private StudentDAO std = new StudentDAO();
	private LibrarianDAO lib = new LibrarianDAO();
	public static Locale currentLocale = new Locale("en", "US");
	public static ResourceBundle messages = ResourceBundle.getBundle("Source", currentLocale);

	public void start() throws SQLException {
		int option = view.language();
		while (option < 1 || option > 2) {
			System.out.println(messages.getString("invalid"));
			option = view.language();
		}

		view.chooseLang(option);

		option = view.screen1();
		while (option < 1 || option > 3) {
			System.out.println(messages.getString("invalid"));
			option = view.screen1();
		}
		if (option == 1) {
			view.stdScreenFunction();
		} else if (option == 2) {
			view.librarianScreenFunction();
		} else {
			view.exit();
			StudentDAO.getCon().close();
			System.exit(0);
		}
	}
}

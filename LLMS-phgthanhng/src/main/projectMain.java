package main;

import java.sql.SQLException;

import Controller.LibraryController;

public class ProjectMain {

	public static void main(String[] args) throws SQLException {
		LibraryController controller = new LibraryController();
		controller.start();
	}
}
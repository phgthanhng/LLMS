package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentModel {

	int stdId;
	String name;
	String contact;

	public StudentModel() {

	}

	public StudentModel(ResultSet rs) throws SQLException {
		this.stdId = rs.getInt("StudentId");
		this.name = rs.getString("Name");
		this.contact = rs.getString("Contact");
	}

	public int getStdId() {
		return stdId;
	}

	public void setStdId(int stdId) {
		this.stdId = stdId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}

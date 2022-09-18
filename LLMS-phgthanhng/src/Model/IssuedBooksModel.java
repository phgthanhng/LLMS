package Model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IssuedBooksModel {
	int id;
	String sn;
	String stdId;
	Date issueDate;
	Date returnDate;

	public IssuedBooksModel() {

	}

	public IssuedBooksModel(ResultSet rs) throws SQLException {
		this.id = rs.getInt("Id");
		this.sn = rs.getString("SN");
		this.stdId = rs.getString("StudentId");
		this.issueDate = rs.getDate("IssuedDate");
		this.returnDate = rs.getDate("ReturnDate");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getStdId() {
		return stdId;
	}

	public void setStdId(String stdId) {
		this.stdId = stdId;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

}

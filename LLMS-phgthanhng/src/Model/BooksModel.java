package Model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BooksModel {

	String sn;
	String title;
	String author;
	String publisher;
	int quantity;
	Date addedDate;
	public BooksModel() {

	}

	public BooksModel(ResultSet rs) throws SQLException {
		this.sn = rs.getString("SN");
		this.title = rs.getString("Title");
		this.author = rs.getString("Author");
		this.publisher = rs.getString("Publisher");
		this.quantity = rs.getInt("Quantity");
		this.addedDate = rs.getDate("AddedDate");
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}

}

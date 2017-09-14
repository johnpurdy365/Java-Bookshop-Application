import java.sql.*;
import java.util.*;

public class BookQueries{

	//Declare our connectionand arraylist
	Connection con;
	ArrayList<Book> contactList = new ArrayList<Book>();
	//Declare preparedstatements for display, update, insert and delete functions
	private PreparedStatement displayAllBooks;
	private PreparedStatement updatePrice;
	private PreparedStatement insertNewBook;
	private PreparedStatement deleteBook;

	public BookQueries(){
		//declare our connection parameters
		String url = "jdbc:mysql://localhost:3307/";
		String databaseName = "bookshop";
		String userName = "root";
		String password = "usbw";

		try{
			//establish the database connection
			con = DriverManager.getConnection(url + databaseName, userName, password);
			//Initialise preparedstatements for display, update, insert and delete functions
			displayAllBooks = con.prepareStatement("SELECT * FROM book");
			updatePrice = con.prepareStatement("UPDATE book SET price = ? WHERE (bookName = ?) ");
			insertNewBook = con.prepareStatement("INSERT INTO book (bookName, authorName, price)" + "VALUES (?, ?, ?)");
			deleteBook = con.prepareStatement("DELETE FROM book WHERE (bookName = ?)");
			} catch(SQLException e){
				e.printStackTrace();
			}
	}//end BookQueries constructor

	//uses preparedstatement from BookQueries above
	//to select all books available using the method SelectAllBooks
	public ArrayList SelectAllBooks(){
		ArrayList bookList = new ArrayList();
		try{
			ResultSet resultSet = displayAllBooks.executeQuery();
			while(resultSet.next()){
				String bookName = resultSet.getString("bookName");
				String authorName = resultSet.getString("authorName");
				double price = resultSet.getDouble("price");
				Book book = new Book(bookName, authorName, price);
				bookList.add(book);
				}//end while
			} catch(SQLException e){
				e.printStackTrace();
			}return bookList;//returns all books back to the user
	}//end SelectAllBooks method

	//uses preparedstatement from BookQueries above
	//the method updateBook is used to update the price of a book
	public int updateBook(Book book){
		try{
			updatePrice.setDouble(1, book.getPrice());
			updatePrice.setString(2, book.getBookName());
			return updatePrice.executeUpdate();
			} catch(SQLException e){
				e.printStackTrace();
			}return 0;
	}//end updateBook method

	//uses preparedstatement from BookQueries above
	//the method insertBook is used to add a new book to the database
	public int insertBook(Book book){
		try{
			insertNewBook.setString(1, book.getBookName());
			insertNewBook.setString(2, book.getAuthorName());
			insertNewBook.setDouble(3, book.getPrice());
			return insertNewBook.executeUpdate();
			} catch(SQLException e){
				e.printStackTrace();
			}return 0;
	} //end insertBook method

	//uses preparedstatement from BookQueries above
	//the method deleteBook is used to delete a book from the database
	public int deleteBook(Book book){
		try{
			deleteBook.setString(1, book.getBookName());
			return deleteBook.executeUpdate();
			} catch(SQLException e){
				e.printStackTrace();
			}return 0;
	} //end deleteBook method

	//Method closeConnection closes the connection when queries are complete
	//and will automatically close all other resources
	public void closeConnection(){
		try{
			con.close();
			} catch(SQLException e){
				e.printStackTrace();
			}
	}//end closeConnection method

	public static void main(String [] args){
		BookQueries ups = new BookQueries();
		//constructors for update, insert and delete
		Book updating = new Book();
		Book inserting = new Book();
		Book deleting = new Book();
		int updateResult = ups.updateBook(updating);
		int insertResult = ups.insertBook(inserting);
		int deleteResult = ups.deleteBook(deleting);
		//calls closeConnection method when finished
		ups.closeConnection();
	}//end main

}//end class
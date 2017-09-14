public class Book{
	private String bookName;
	private String authorName;
	private double price;

	public Book(){}

	//constructor for book class and variables
	public Book(String bookName, String authorName, double price){
		setBookName(bookName);
		setAuthorName(authorName);
		setPrice(price);
	}

	//set all setters first

	//set the bookName
	public void setBookName(String bookName){
		this.bookName = bookName;
	}

	//set the authorName
	public void setAuthorName(String authorName){
			this.authorName = authorName;
	}

	//set the price
	public void setPrice(double price){
			this.price = price;
	}

	//now use the getters for all the setters

	//get first name
	public String getBookName(){
		return bookName;
	}

	//get AuthorName
	public String getAuthorName(){
		return authorName;
	}

	//get the price
	public double getPrice(){
		return price;
	}

	//use the toString method to return all getters
	public String toString(){
		return "Book: " + getBookName() + ", Author: " + getAuthorName() + ", Price: " + price + " \n ";
	}
}
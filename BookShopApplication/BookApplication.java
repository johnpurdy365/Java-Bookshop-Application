import java.util.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookApplication implements ActionListener {

	private JFrame frame;
	private JPanel p1, tab1, tab2, tab3, tab4;
	private JTabbedPane tp;
	private BookQueries dbConn;
	private ArrayList<Book> bookList;
	private JButton buttonDisplayAll, buttonUpdatePrice, buttonInsertBook, buttonDeleteBook;
	private JLabel labelUpdateBookName, labelUpdateBookPrice, labelInsertAuthorName, labelInsertBookName, labelInsertPrice, labelDeleteBookLabel;
	private JTextField updateBookName, updatePriceField, insertAuthor, insertBookName, insertPrice, labelDeleteBook;
	private JTable table;
	private JScrollPane scrollpane;
	private ArrayList<Object[]> list;

	public static void main(String [] args){
		BookApplication bookApplication =  new BookApplication();
		bookApplication.frame.setVisible(true);
	}

	//constructor for booklist and connection to database
	public BookApplication(){
		dbConn = new BookQueries();
		bookList = new ArrayList<Book>();
		bookList = dbConn.SelectAllBooks();
		organiseComponents();
	}

	private void organiseComponents(){
		//start with the jframe
		frame = new JFrame("Bookshop Application");//title
		frame.setSize(550, 330);//frame size
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//close on exit

		//add JPanel for Jtp
		p1 = new JPanel();
		p1.setLayout(null);
		frame.getContentPane().add(p1);

		//add Jtp
		tp = new JTabbedPane();
		tp.setSize(550,300);
		tp.setLocation(0,0);
		p1.add(tp);

		// Display all books tab
		tab1 = new JPanel();
		tab1.setLayout(null);
		tp.add(tab1, "Display all books");
		table = new JTable(){
			@Override
			public boolean isCellEditable(int row, int column){
				return false;
			}
		};

		//use a table to display all books
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] {"Book Name", "Author Name", "Price"}));
		table.setFillsViewportHeight(true);
		scrollpane = new JScrollPane(table);
		scrollpane.setBounds(41,25,415,126);
		tab1.add(scrollpane);

		//button to display all books available
		buttonDisplayAll = new JButton("Display all books");
		buttonDisplayAll.setBounds(174,180,129,23);
		buttonDisplayAll.addActionListener(this);
		tab1.add(buttonDisplayAll);

		//Update price of book tab
		tab2 = new JPanel();
		tab2.setLayout(null);
		tp.add(tab2, "Update Price of a book");

		//Update book label
		labelUpdateBookName = new JLabel("Enter book name: ");
		labelUpdateBookName.setBounds(20,35,140,29);
		tab2.add(labelUpdateBookName);

		//Update book text field
		updateBookName = new JTextField();
		updateBookName.setBounds(175, 35, 113, 29);
		tab2.add(updateBookName);

		//Update price label
		labelUpdateBookPrice = new JLabel("Enter price: ");
		labelUpdateBookPrice.setBounds(312,27,140,44);
		tab2.add(labelUpdateBookPrice);

		//Update price test field
		updatePriceField = new JTextField();
		updatePriceField.setBounds(412, 35, 105, 29);
		tab2.add(updatePriceField);

		//Update price button
		buttonUpdatePrice = new JButton("Update");
		buttonUpdatePrice.setBounds(47,153,113,23);
		buttonUpdatePrice.addActionListener(this);
		tab2.add(buttonUpdatePrice);

		//Insert tab
		tab3 = new JPanel();
		tab3.setLayout(null);
		tp.add(tab3, "Insert a new book");

		//Insert button
		buttonInsertBook = new JButton("Insert");
		buttonInsertBook.setBounds(306,122,129,23);
		buttonInsertBook.addActionListener(this);
		tab3.add(buttonInsertBook);

		//Insert label
		labelInsertAuthorName = new JLabel("Author name:");
		labelInsertAuthorName.setBounds(20,35,120,29);
		tab3.add(labelInsertAuthorName);

		//Insert Text field
		insertAuthor = new JTextField();
		insertAuthor.setBounds(147, 35, 120, 29);
		tab3.add(insertAuthor);

		//Insert label
		labelInsertBookName = new JLabel("Enter book name:");
		labelInsertBookName.setBounds(273,27,140,44);
		tab3.add(labelInsertBookName);

		//Insert text field
		insertBookName = new JTextField();
		insertBookName.setBounds(386, 35, 106, 29);
		tab3.add(insertBookName);

		//Insert label
		labelInsertPrice = new JLabel("Insert price:");
		labelInsertPrice.setBounds(20,119,165,29);
		tab3.add(labelInsertPrice);

		//Insert text field
		insertPrice = new JTextField();
		insertPrice.setBounds(131, 119, 106, 29);
		tab3.add(insertPrice);

		//Delete tab
		tab4 = new JPanel();
		tab4.setLayout(null);
		tp.add(tab4, "Delete a book");

		//Delete button
		buttonDeleteBook = new JButton("Delete book");
		buttonDeleteBook.setBounds(370,78,129,23);
		buttonDeleteBook.addActionListener(this);
		tab4.add(buttonDeleteBook);

		//Delete label
		labelDeleteBookLabel = new JLabel("Enter book name: ");
		labelDeleteBookLabel.setBounds(20,67,140,44);
		tab4.add(labelDeleteBookLabel);

		//Insert text field
		labelDeleteBook = new JTextField();
		labelDeleteBook.setBounds(174, 75, 157, 29);
		tab4.add(labelDeleteBook);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if(e.getSource() == buttonDisplayAll){
			displayTable();
		}
		if(e.getSource() == buttonUpdatePrice){
			Book update = new Book();
			String bookName = updateBookName.getText().toString(); //takes in the book name to find
			String subPrice = updatePriceField.getText().toString(); //obtains the new price from user
			double price = Double.parseDouble(subPrice); //changes price to a double if string is entered
			update.setBookName(bookName);//updates the book name
			update.setPrice(price);//updates the book price

			int value = dbConn.updateBook(update);//sends updates to the database

			if (value > 0)
				//if book updates the user is alerted
				JOptionPane.showMessageDialog(frame, "Book updated. Thank you!");
			else
				//if the book is not found user is displayed a message
				JOptionPane.showMessageDialog(frame, "Im sorry. This book was not found!");
		}

		if(e.getSource() == buttonInsertBook){
			String bookname = insertBookName.getText().toString();//takes in user information for inserting book name
			String authorname = insertAuthor.getText().toString();//takes in user information for inserting author name
			String bookPrice = insertPrice.getText().toString();//takes in user information for inserting a price
			double price = Double.parseDouble(bookPrice); //changes price to a double if string is entered

			//create a book object and insert into the database
			Book inserting = new Book(bookname, authorname, price);
			int insert = dbConn.insertBook(inserting);

			displayTable();

			//if the book is added successfully display message to user
			if (insert == 1)
				JOptionPane.showMessageDialog(frame, "Thank you " + insert + " book added");
			//else display message to the user that the book was not added successfully
			else
				JOptionPane.showMessageDialog(frame, "I'm sorry " + insert + " book was not added");
		}

		if(e.getSource()== buttonDeleteBook){
			String bookName = labelDeleteBook.getText().toString();//takes in user information for deleting book name
			Book deleting = new Book();
			deleting.setBookName(bookName);//sets the book to be deleted
			int delete = dbConn.deleteBook(deleting);//deletes book from the database

			if (delete > 0)
				//if book is deleted successfully display message to user
				JOptionPane.showMessageDialog(frame, "Thank you Book " + delete + " deleted!");
			else
				//else if book was not deleted successfully inform user
				JOptionPane.showMessageDialog(frame, "Im sorry " + delete + " book was not found!");
			displayTable();
		}
	}

	//Method to display all books in the database
	//method uses a table. Source code of table from class mate Melvin
	public void displayTable(){
		list = new ArrayList<Object[]>();
		bookList = dbConn.SelectAllBooks();//selects all books from method in BookQueries
		//Loop through all books and display all
		for (int i = 0; i < bookList.size(); i++) {
			list.add(new Object[] {
					bookList.get(i).getBookName(),
					bookList.get(i).getAuthorName(),
					bookList.get(i).getPrice()
			});

		}
		table.setModel(new DefaultTableModel(list.toArray(new Object[][] {}),
				new String[] {"Book", "Author", "Price"}));
	}
}

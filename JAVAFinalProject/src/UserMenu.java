import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import net.proteanit.sql.DbUtils;

public class UserMenu {

	public static void user_menu(String UID) {

		JFrame f = new JFrame("User Functions"); // Give dialog box name as User functions
		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Exit user menu on closing
		// the dialog box

		JButton viewButton = new JButton(Strings.user_menu_view_books);// creating instance of JButton
		viewButton.setBounds(20, 20, 120, 25);// x axis, y axis, width, height
		viewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFrame f = new JFrame(Strings.user_menu_books_available); // View books stored in database
				// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				Connection connection = connect();
				String sql = "select * from BOOKS"; // Retreive data from database
				try {
					Statement statement = connection.createStatement(); // connect to database
					statement.executeUpdate(SQLQuery.CHOOSE_DATABASE); // use librabry
					statement = connection.createStatement();
					ResultSet resultSet = statement.executeQuery(sql);
					JTable bookList = new JTable(); // show data in table format
					bookList.setModel(DbUtils.resultSetToTableModel(resultSet));

					JScrollPane scrollPane = new JScrollPane(bookList); // enable scroll bar

					f.add(scrollPane); // add scroll bar
					f.setSize(800, 400); // set dimensions of view books frame
					f.setVisible(true);
					f.setLocationRelativeTo(null);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}

			}
		});

		JButton myBook = new JButton(Strings.user_menu_my_books);// creating instance of JButton
		myBook.setBounds(150, 20, 120, 25);// x axis, y axis, width, height
		myBook.addActionListener(new ActionListener() { // Perform action
			public void actionPerformed(ActionEvent e) {

				JFrame jFrame = new JFrame(Strings.user_menu_my_books); // View books issued by user
				// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				int UID_int = Integer.parseInt(UID); // Pass user ID

				// .iid,issued.uid,issued.bid,issued.issued_date,issued.return_date,issued,
				Connection connection = connect(); // connect to database
				// retrieve data
				String sql = "select distinct issued.*,books.bname,books.genre,books.price from issued,books "
						+ "where ((issued.uid=" + UID_int
						+ ") and (books.bid in (select bid from issued where issued.uid=" + UID_int
						+ "))) group by iid";
				String sql1 = Strings.sql_select_bid + UID_int;
				try {
					Statement stmt = connection.createStatement();
					// use database
					stmt.executeUpdate(SQLQuery.CHOOSE_DATABASE);
					stmt = connection.createStatement();
					// store in array
					ArrayList booksList = new ArrayList();

					ResultSet resultSet = stmt.executeQuery(sql);
					JTable book_list = new JTable(); // store data in table format
					book_list.setModel(DbUtils.resultSetToTableModel(resultSet));
					// enable scroll bar
					JScrollPane scrollPane = new JScrollPane(book_list);

					jFrame.add(scrollPane); // add scroll bar
					jFrame.setSize(800, 400); // set dimensions of my books frame
					jFrame.setVisible(true);
					jFrame.setLocationRelativeTo(null);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1);
				}

			}
		});

		f.add(myBook); // add my books
		f.add(viewButton); // add view books
		f.setSize(300, 100);// 400 width and 500 height
		f.setLayout(null);// using no layout managers
		f.setVisible(true);// making the frame visible
		f.setLocationRelativeTo(null);
	}
}

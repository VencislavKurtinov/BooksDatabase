package com.booksDB.DAO;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.booksDB.Exceptions.BookException;

import com.booksDB.connections.DBConnection;
import com.booksDB.interfaces.BookDAO;
import com.booksDB.models.Book;
import com.booksDB.models.User;
import com.itextpdf.text.pdf.PdfReader;

public class BookDatabaseDAO implements BookDAO {
	private static final String SELECT_FROM_BOOKS_ORDER_BY_DATE_DESC = "SELECT * FROM books ORDER BY date DESC";
	private static final String INSERT_INTO_BOOKS_ID_BOOKS_GANRE_PAGES_CONTENTS_NAME_DATE_VALUES_NULL = "INSERT INTO books (id_books,ganre,pages,contents,name,date) VALUES (null,?,?,?,?,?)";
	private static final String INSERT_INTO_USERS_HAS_BOOKS_USERS_ID_USERS_BOOKS_ID_BOOKS_VALUES = "INSERT INTO users_has_books (users_id_users,books_id_books) VALUES (?,?)";
	private static final int MAX_SIZE_OF_MEMORY = 5096;
	private static final int MAX_NUMBER_OF_PAGES = 1000;
	private static BookDAO bookDatabaseDAO = null;
	private List<Book> allBooks;

	public BookDatabaseDAO() throws BookException {
		try {
			Connection con = DBConnection.getInstance().getConnection();
			Statement statment = con.createStatement();
			ResultSet rs = statment.executeQuery(SELECT_FROM_BOOKS_ORDER_BY_DATE_DESC);
			this.allBooks = new ArrayList<>();

			while (rs.next()) {
				int id = rs.getInt("id_books");
				String name = rs.getString("name");
				int pages = rs.getInt("pages");
				String ganre = rs.getString("ganre");
				Date date = rs.getDate("date");

				this.allBooks.add(new Book(id, ganre, pages, date, name));
			}
		} catch (SQLException e) {
			throw new BookException("Do not have a books", e);

		}

	}

	@Override
	public void addBook(Book book, User user) throws BookException {
		try {
			if (book != null && book.getPath().contains(".pdf")) {

				String inFile = book.getPath();
				FileInputStream io = new FileInputStream(inFile);
				byte[] contents = new byte[(int) inFile.length()];
				DataInputStream dis;
				dis = new DataInputStream(io);
				dis.readFully(contents);
				dis.close();
				PdfReader reader = new PdfReader(new FileInputStream(inFile));
				int pages = reader.getNumberOfPages();
				if (pages < MAX_NUMBER_OF_PAGES && (int) inFile.length() < MAX_SIZE_OF_MEMORY) {
					Connection con = DBConnection.getInstance().getConnection();

					synchronized (con) {
						con.setAutoCommit(false);
						try {
							PreparedStatement ps = (PreparedStatement) con.prepareStatement(
									INSERT_INTO_BOOKS_ID_BOOKS_GANRE_PAGES_CONTENTS_NAME_DATE_VALUES_NULL,
									Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, book.getGanre());
							ps.setInt(2, pages);
							ps.setBytes(3, contents); // byte[] array
							ps.setString(4, book.getName());
							ps.setDate(5, new java.sql.Date(Calendar.getInstance().getTimeInMillis()));
							ps.executeUpdate();

							ResultSet rs = ps.getGeneratedKeys();
							rs.next();
							book.setId(rs.getInt(1));

							ps = (PreparedStatement) con.prepareStatement(
									INSERT_INTO_USERS_HAS_BOOKS_USERS_ID_USERS_BOOKS_ID_BOOKS_VALUES);
							ps.setInt(1, user.getId());
							ps.setInt(2, book.getId());

							ps.executeUpdate();
							con.commit();
							synchronized (allBooks) {
								allBooks.add(book);
							}

						} finally {
							con.setAutoCommit(true);
						}
					}
				}

				else {
					throw new BookException("Too long book.Max number of pages is" + MAX_NUMBER_OF_PAGES
							+ ".Max memory size is" + MAX_SIZE_OF_MEMORY);
				}
			}

			else {
				throw new BookException("Other format book");
			}
		} catch (SQLException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BookException("Can`t add a book", e);
		}
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> result = new ArrayList<>();
		result.addAll(this.allBooks);
		return result;
	}

	public static BookDAO getBookDatabaseDAO() throws BookException {

		synchronized (BookDatabaseDAO.class) {

			if (bookDatabaseDAO == null) {
				bookDatabaseDAO = new BookDatabaseDAO();
			}
		}

		return bookDatabaseDAO;
	}
}

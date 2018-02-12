package com.booksDB.interfaces;

import java.util.List;

import com.booksDB.Exceptions.BookException;
import com.booksDB.models.Book;
import com.booksDB.models.User;

public interface BookDAO {

	void addBook(Book book, User user) throws BookException;

	List<Book> getAllBooks();

}
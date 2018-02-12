package com.booksDB.interfaces;

import com.booksDB.Exceptions.UserException;
import com.booksDB.models.User;

public interface UserDAO {
	
	public void addUser(User user) throws UserException;

	User getUserByNickNameAndPassword(String email, String password) throws UserException;
}

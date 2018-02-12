package com.booksDB.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.booksDB.connections.DBConnection;
import com.booksDB.Exceptions.UserException;
import com.booksDB.interfaces.UserDAO;
import com.booksDB.models.User;
import com.mysql.jdbc.Statement;

public class UserDatabaseDAO implements UserDAO {
	private static UserDatabaseDAO dataBaseUserDAO = null;
	
	private static final String SELECT_FROM_USERS_WHERE_NICKNAME_AND_PASSWORD = "SELECT * FROM users WHERE nickname = ? AND password = sha1(?) ";
	private static final String ADD_USER_QUERY = "Insert into users values(null,sha1(?),?)";


	public void addUser(User user) throws UserException  {
		if (user != null) {
			Connection con = DBConnection.getInstance().getConnection();
			try {
				PreparedStatement ps = con.prepareStatement(ADD_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
				ps.setString(2, user.getNickName());
				ps.setString(1, user.getPassword());
				ps.executeUpdate();

				ResultSet rs = ps.getGeneratedKeys();
				rs.next();
				user.setId(rs.getInt(1));

			} catch (SQLException e) {
				e.printStackTrace();
				throw new UserException("Can`t add a User", e);
			}
		}
		

	}


	public User getUserByNickNameAndPassword(String nickName, String password) throws UserException {
		Connection con = DBConnection.getInstance().getConnection();
		try {
			PreparedStatement ps = con.prepareStatement(SELECT_FROM_USERS_WHERE_NICKNAME_AND_PASSWORD);
			ps.setString(1, nickName);
			ps.setString(2, password);
			ResultSet result = ps.executeQuery();

			result.next();
			String name = result.getString(3);
			int id = result.getInt(1);
			String userPassword = result.getString(2);
			return new User(id, name, userPassword);

		} catch (SQLException e) {
			e.printStackTrace();
			throw new UserException("Can`t find a User with  this nickName " + nickName + " and password: " + password, e);
		}

	}

	public static UserDAO getDataBaseUserDAO() {

		synchronized (UserDatabaseDAO.class) {

			if (dataBaseUserDAO == null) {
				dataBaseUserDAO = new UserDatabaseDAO();
			}
		}

		return dataBaseUserDAO;
	}


}

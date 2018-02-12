package com.booksDB.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.booksDB.DAO.UserDatabaseDAO;
import com.booksDB.Exceptions.UserException;
import com.booksDB.connections.DBConnection;
import com.booksDB.models.User;

@WebServlet("/Register")
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = -4907448809468716980L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			HttpSession sesion = request.getSession();
			String nickName = request.getParameter("nickName");
			String password = request.getParameter("password");
			if (nickName != null && !nickName.isEmpty() && password != null && !password.isEmpty()) {

				User user = new User(nickName, password);

				try {
					UserDatabaseDAO.getDataBaseUserDAO().addUser(user);
				} catch (UserException e) {
					System.out.println("Register servlet post method");
					request.setAttribute("error", "This nickname is hired or invalide!");
					request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
					return;
				}
				sesion.setMaxInactiveInterval(720);
				sesion.setAttribute("logged", true);
				sesion.setAttribute("user", user);
				request.getRequestDispatcher("Home.jsp").forward(request, response);

			} else {
				System.out.println("Invalide Nickname or Password!");
				request.setAttribute("error", "Invalide Nickname or Password!");
				request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
				return;
			}
		} catch (Exception e) {
			System.out.println("Register servlet");
			request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);

			return;

		}
	}

	@Override
	public void destroy() {
		DBConnection.getInstance().closeConnection();
	}

}

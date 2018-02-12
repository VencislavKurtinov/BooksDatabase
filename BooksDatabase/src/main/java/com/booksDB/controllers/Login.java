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

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession sesion = request.getSession();
			String nickName = request.getParameter("nickName");
			String password = request.getParameter("password");
			if (nickName != null && !nickName.isEmpty() && password != null && !password.isEmpty()) {
				User user = null;
				try {
					user = UserDatabaseDAO.getDataBaseUserDAO().getUserByNickNameAndPassword(nickName, password);
				} catch (UserException e) {
					request.setAttribute("error", "Do not have a user with this nickName and password");
					request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
					return;
				}
				sesion.setMaxInactiveInterval(120);
				sesion.setAttribute("user", user);
				sesion.setAttribute("logged", true);
				response.sendRedirect("Home.jsp");

			} else {

				request.setAttribute("error", "Invalid password or nickName!");
				request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
			}
		} catch (Exception e) {
			System.out.println("DoPost Login Servlet");
			request.setAttribute("error", "Do not have a user with this nickName and password");
			request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
			return;
		}

	}

	@Override
	public void destroy() {
		DBConnection.getInstance().closeConnection();
	}
}

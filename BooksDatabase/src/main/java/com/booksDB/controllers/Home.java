package com.booksDB.controllers;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.booksDB.DAO.BookDatabaseDAO;
import com.booksDB.Exceptions.BookException;
import com.booksDB.interfaces.BookDAO;
import com.booksDB.models.Book;
import com.booksDB.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession sesion = request.getSession();
			Object logged = sesion.getAttribute("logged");

			boolean isLogged = (logged != null && ((boolean) logged));
			if (sesion.isNew() || !isLogged) {
				request.setAttribute("error", "Please log in system!");
				request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
			} else {
				BookDAO dao = null;
				try {
					dao = BookDatabaseDAO.getBookDatabaseDAO();
				} catch (BookException e) {
					request.setAttribute("error", "Somthing  went wrong. Please try later!");
					request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
					System.out.println("Don`t have any books!");
					e.printStackTrace();
				}
				String query = request.getParameter("query");
				String ganre = request.getParameter("ganre");
				String min = request.getParameter("minPages");
				String max = request.getParameter("maxPages");
				response.setContentType("text/json");
				List<Book> books = dao.getAllBooks();
				List<Book> offerBooks = dao.getAllBooks();

				if (ganre != null) {
					books.removeIf((book) -> book.getGanre().equalsIgnoreCase(ganre));
				}

				if (min != null && min != "") {
					int minPages = Integer.parseInt(min);
					books.removeIf((book) -> book.getPages() < minPages);
				}

				if (max != null && min != "") {
					int maxPages = Integer.parseInt(max);
					books.removeIf((book) -> book.getPages() > maxPages);
				}

				if (query != null) {
					books.removeIf((book) -> !book.getName().startsWith(query));
				}

				if (books.isEmpty() && query != null) {
					String[] offer = query.split(" ");
					for (int i = 0; i < offer.length; i++) {
						for (Iterator<Book> iterator = offerBooks.iterator(); iterator.hasNext();) {
							Book book2 = (Book) iterator.next();
							if (offer[i].startsWith((book2.getName()))) {
								books.add(book2);
							}
						}
					}
				}

				Gson gson = new GsonBuilder().create();
				response.getWriter().println(gson.toJson(books));

			}
		} catch (Exception e) {
			request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
			e.printStackTrace();
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession sesion = request.getSession();
			Object logged = sesion.getAttribute("logged");

			boolean isLogged = (logged != null && ((boolean) logged));
			if (sesion.isNew() || !isLogged) {
				request.setAttribute("error", "Please log in system!");
				request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
			} else {
				User user = (User) sesion.getAttribute("user");

				String ganre = request.getParameter("ganre");
				String name = request.getParameter("name");
				String path = request.getParameter("path");
				boolean validGanre = (ganre != null
						&& ((ganre.equals("action")) || (ganre.equals("programming")) || (ganre.equals("crime"))));
				boolean validName = (name != null && name.isEmpty());
				boolean validPath = (path != null && path.isEmpty());
				if (validGanre && validName && validPath) {
					Book book = new Book(ganre, path, name);
					// da napisha tranzakciq v addbook i za users has books
					try {
						BookDatabaseDAO.getBookDatabaseDAO().addBook(book, user);
					} catch (BookException e) {
						request.setAttribute("error", e.getMessage());
						request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);

						e.printStackTrace();
					}
				} else {
					request.setAttribute("error", "Invalid parameters!");
					request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
				}

			}
		} catch (Exception e) {
			request.getRequestDispatcher("ErrorPage.jsp").forward(request, response);
			e.printStackTrace();
			return;
		}
	}

}

package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Book;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.UserBook;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles fetching of books saved by user.
 */
@WebServlet("/user-book")
public class UserBookServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();

    UserBook userBook = new UserBook("test@example.com", "09c24253-45d6-41e9-924f-7227241ecb73", "READ");
    datastore.storeUserBook(userBook);
  }

  /**
   * Responds with a JSON representation of a list of Book objects saved by
   * {@code user} with reading status {@code status}.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("application/json");

    String user = request.getParameter("user");

    List<UserBook> userBooks = datastore.getUserBooks(user);
    Gson gson = new Gson();
    String json = gson.toJson(userBooks);

    response.getOutputStream().println(json);
  }

  /**
   * Receives a JSON representation of a UserBook and stores it in the datastore.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }

    String user = userService.getCurrentUser().getEmail();
    String bookId = request.getParameter("bookId");
    String status = request.getParameter("status");

    UserBook userBook = new UserBook(user, bookId, status);
    datastore.storeUserBook(userBook);

    // response.sendRedirect("/user-bookshelf.html?user=" + user);
  }
}

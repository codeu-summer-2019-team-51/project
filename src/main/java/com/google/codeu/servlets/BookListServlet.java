package com.google.codeu.servlets;

import com.google.codeu.data.Book;
import com.google.codeu.data.Datastore;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Handles fetching of all books for the book list.
 */
@WebServlet("/book-list")
public class BookListServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with a JSON representation of a list of Book objects.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("application/json");

    List<Book> books = datastore.getAllBooks();
    Gson gson = new Gson();
    String json = gson.toJson(books);

    response.getOutputStream().println(json);
  }
}

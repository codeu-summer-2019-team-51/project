package com.google.codeu.servlets;

import com.google.codeu.data.Book;
import com.google.codeu.data.Datastore;
import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles fetching and saving book data.
 */
@WebServlet("/about-book")
public class AboutBookServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with all details for a particular book.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("application/json");
    String bookIdString = request.getParameter("book");
    if (bookIdString == null || bookIdString.equals("")) {
      //Request is invalid, return empty response.
      return;
    }
    Book bookData = datastore.getBook(bookIdString);
    if (bookData == null) {
      return;
    }
    Gson gson = new Gson();
    String json = gson.toJson(bookData);
    response.getWriter().println(json);
  }

  /**
   * Stores a new Book.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String title = Jsoup.clean(request.getParameter("title"), Whitelist.none());
    List<String> authors = new ArrayList<String>();
    if (request.getParameter("authors") != null) {
      String input = Jsoup.clean(request.getParameter("authors"), Whitelist.none());
      for (String s : input.split(",")) {
        authors.add(s);
      }
    }

    String description = Jsoup.clean(request.getParameter("description"), Whitelist.none());

    Book book = new Book(title, authors, description);
    datastore.storeBook(book);
    response.sendRedirect("/aboutbook.html?id=" + book.getId());
  }
}

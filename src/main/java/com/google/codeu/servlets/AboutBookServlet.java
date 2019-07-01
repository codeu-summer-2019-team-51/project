package com.google.codeu.servlets;

import com.google.codeu.data.Book;
import com.google.codeu.data.Datastore;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

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
    System.out.println("CHECK REQUEST: "+request.getQueryString());
    String bookIdString = request.getParameter("book");
    System.out.println("DEBUG 1: " + bookIdString);
    if (bookIdString == null || bookIdString.equals("")) {
      //Request is invalid, return empty response.
      return;
    }
    Book bookData = datastore.getBook(bookIdString);
    if (bookData == null) {
      return;
    }
    System.out.println("DEBUG 2: " + bookData);
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
      for (String s: input.split(",")) {
        authors.add(s);
      }
    }

    Book book = new Book(title, authors);
    datastore.storeBook(book);
    response.sendRedirect("/aboutbook.html?id=" + book.getId());
  }
}

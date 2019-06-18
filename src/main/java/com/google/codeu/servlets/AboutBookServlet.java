/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codeu.servlets;

import java.io.IOException;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Book;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Handles fetching and saving book data.
 */
@WebServlet("/aboutbook")
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
    response.setContentType("text/html");
    String book = request.getParameter("book");
    if (book == null || book.equals("")) {
      //Request is invalid, return empty response.
      return;
    }
    UUID bookId = UUID.fromString(book);
    Book bookData = datastore.getBook(bookId);
    if (bookData == null) {
      return;
    }
    response.getOutputStream().println(bookData.getTitle());
    for (int i = 0; i < bookData.getAuthors().size() - 1; i++) {
      response.getOutputStream().print(bookData.getAuthors().get(i) + ", ");
    }
    response.getOutputStream().print(bookData.getAuthors()
        .get(bookData.getAuthors().size() - 1));
    response.getOutputStream().println(bookData.getAvgRating());
    if (bookData.getReviews() != null) {
      for (int i = 0; i < bookData.getReviews().size(); i++) {
        response.getOutputStream().println("1. " + bookData.getReviews().get(i));
      }
    }
  }

  /**
   * Stores a new Book.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    String title = Jsoup.clean(request.getParameter("title"), Whitelist.none());
    List<String> authors = new ArrayList<String>();
    authors.add("Stephen Hawking");
    authors.set(0, Jsoup.clean(request.getParameter("authors"),
        Whitelist.none()));
//    while (request.getParameter("authors") != null) {
//      authors.add(Jsoup.clean(request.getParameter("authors"),
//          Whitelist.none()));
//    }
    Book book = new Book(title, authors);
    datastore.storeBook(book);
    response.sendRedirect("/user-page.html");
  }
}

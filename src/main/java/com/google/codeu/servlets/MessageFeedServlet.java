package com.google.codeu.servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * This servlet will respond with a hard-coded "this will be my message feed"
 * whenever the user requests the /feed URL.
 */

@WebServlet("/feed")
public class MessageFeedServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    res.getOutputStream().println("this will be my message feed!");
  }

}

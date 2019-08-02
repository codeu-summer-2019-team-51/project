package com.google.codeu.servlets;

import com.google.codeu.data.Datastore;
import com.google.codeu.data.Message;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


/**
 * This servlet will respond with a hard-coded "this will be my message feed"
 * whenever the user requests the /feed URL.
 */

@WebServlet("/feed")
public class MessageFeedServlet extends HttpServlet {
  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    res.setContentType("application/json");

    List<Message> messageList = datastore.getAllMessages();
    Gson gson = new Gson();
    String json = gson.toJson(messageList);

    res.getOutputStream().println(json);
  }

}

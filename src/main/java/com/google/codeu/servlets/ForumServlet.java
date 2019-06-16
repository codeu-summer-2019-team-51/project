package com.google.codeu.servlets;

import com.google.codeu.data.Community;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Handles fetching forum contents.
 */
@WebServlet("/forum")
public class ForumServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with data of all the communities in the forum.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException {

    response.setContentType("application/json");

    List<Community> communities = datastore.getAllCommunities();
    Gson gson = new Gson();
    String json = gson.toJson(communities);

    response.getOutputStream().println(json);
  }
}

package com.google.codeu.servlets;

import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*Handles fetching all users for the community page*/

@WebServlet("/user-list")
public class UserListServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    response.setContentType("application/json");
    Set<String> userEmailsWithReviews = datastore.getUserEmailsWithReviews();
    Map<String, User> userData = datastore.getAllUsers();

    List<User> usersWithReviews = new ArrayList<User>();
    for (String userEmail : userEmailsWithReviews) {
      User user = userData.get(userEmail);
      if (user == null) {
        user = new User(userEmail);
      }
      usersWithReviews.add(user);
    }

    Gson gson = new Gson();
    String json = gson.toJson(usersWithReviews);
    response.getOutputStream().println(json);
  }
}

package com.google.codeu.servlets;

import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;
import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*Handles fetching all users for the community page*/

@WebServlet("/users")
public class UserServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    response.setContentType("application/json");

    String user = request.getParameter("user");
    if (user != null) {
      // Return only userData of specified user

      if (user.equals("")) {
        // Request is invalid, return empty response
        return;
      }

      User userData = datastore.getUser(user);
      Gson gson = new Gson();
      String json = gson.toJson(userData);
      response.getOutputStream().println(json);

    } else {
      // Return all userData
      Set<String> userEmailsWithReviews = datastore.getUserEmailsWithReviews();
      Map<String, User> allUserData = datastore.getAllUsers();

      List<User> usersWithReviews = new ArrayList<User>();
      for (String userEmail : userEmailsWithReviews) {
        User userData = allUserData.get(userEmail);
        if (userData == null) {
          userData = new User(userEmail);
        }
        usersWithReviews.add(userData);
      }

      Gson gson = new Gson();
      String json = gson.toJson(usersWithReviews);
      response.getOutputStream().println(json);
    }
  }
}

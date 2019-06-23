package com.google.codeu.servlets;

import com.google.codeu.common.Tree;
import com.google.codeu.data.Comment;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Thread;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Handles fetching thread comments.
*/
@WebServlet("/thread")
public class ThreadServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, IllegalArgumentException {

    response.setContentType("application/json");

    String threadId = request.getParameter("id");
    Thread thread = datastore.getThread(threadId);
    Tree<Comment> comments = datastore.getComments(threadId);

    Gson gson = new Gson();
    String threadJson = gson.toJson(thread);
    String commentsJson = comments.toJson();

    String resultJson = String.format(
        "{\"thread\":%s,\"comments\":%s}",
        threadJson,
        commentsJson);

    response.getOutputStream().println(resultJson);
  }

  // @Override
  // public void doPost(HttpServletRequest request, HttpServletResponse response)
  //     throws IOException {
  //
  //   UserService userService = UserServiceFactory.getUserService();
  //   if (!userService.isUserLoggedIn()) {
  //     response.sendRedirect("/index.html");
  //     return;
  //   }
  //
  //   String userEmail = userService.getCurrentUser().getEmail();
  //   String name = Jsoup.clean(request.getParameter("name"), Whitelist.none());
  //   String description = Jsoup.clean(request.getParameter("description"),
  //       Whitelist.none());
  //   String communityId = request.getParameter("communityId");
  //
  //   Thread thread = new Thread(name, description, userEmail, communityId);
  //   datastore.storeThread(thread);
  //
  //   response.sendRedirect("/community.html?id=" + communityId);
  // }
}

package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.Thread;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

 /**
 * Handles fetching community threads.
 */
@WebServlet("/community")
public class CommunityServlet extends HttpServlet {

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
    // for debugging: datastore.storeThread(new Thread("Thread name",
    //     "Thread description", "kcelinef@codeustudents.com",
    //     "c9f2fc95-4102-4e37-8d26-8bbbc418ac2b"));
  }

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, IllegalArgumentException {

    response.setContentType("application/json");

    String communityId = request.getParameter("id");

    List<Thread> threads = datastore.getThreads(communityId);
    Gson gson = new Gson();
    String json = gson.toJson(threads);

    response.getOutputStream().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }

    String userEmail = userService.getCurrentUser().getEmail();
    String name = Jsoup.clean(request.getParameter("name"), Whitelist.none());
    String description = Jsoup.clean(request.getParameter("description"),
        Whitelist.none());
    String communityId = request.getParameter("communityId");

    Thread thread = new Thread(name, description, userEmail, communityId);
    datastore.storeThread(thread);

    response.sendRedirect("/community.html?id=" + communityId);
  }
}

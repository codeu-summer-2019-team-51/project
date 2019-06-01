package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Handles fetching and saving user data.
 */
@WebServlet("/profile-pic")
public class ProfilePicServlet extends HttpServlet {

  private final String uploadDirectory = "image";

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Responds with the profile picture for a particular user.
   */
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    response.setContentType("text/html");

    String user = request.getParameter("user");

    if (user == null || user.equals("")) {
      // Request is invalid, return empty response
      return;
    }

    User userData = datastore.getUser(user);

    if (userData == null || userData.getProfilePic() == null) {
      return;
    }

    String filePath = uploadDirectory + File.separator + user
        + userData.getProfilePic().get(0);

    response.getOutputStream().println(filePath);
  }

  /**
   * Saves uploaded profile picture.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {

    UserService userService = UserServiceFactory.getUserService();
    if (!userService.isUserLoggedIn()) {
      response.sendRedirect("/index.html");
      return;
    }

    String userEmail = userService.getCurrentUser().getEmail();
    String aboutMe = datastore.getUser(userEmail).getAboutMe();
    List<String> profilePic = new ArrayList<String>();

    if (ServletFileUpload.isMultipartContent(request)) {
      try {
        List<FileItem> multiparts = new ServletFileUpload(new DiskFileItemFactory())
            .parseRequest(request);
        for (FileItem item : multiparts) {
          if (!item.isFormField()) {
            String fileName = new File(item.getName()).getName();
            item.write(new File(
                uploadDirectory + File.separator + userEmail + fileName));
            profilePic.add(fileName);
          }
        }
        //File uploaded successfully
        request.setAttribute("message", "File Uploaded Successfully");
      } catch (Exception ex) {
        request.setAttribute("message", "File Upload Failed due to " + ex);
      }
    } else {
      request.setAttribute("message", "Sorry this Servlet only handles file upload request");
    }

    User user = new User(userEmail, aboutMe, profilePic);
    datastore.storeUser(user);

    response.sendRedirect("/user-page.html?user=" + userEmail);
  }
}

package com.google.codeu.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;
import com.google.codeu.data.User;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

  private final String uploadDirectory = "../data/profile-pic";

  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  /**
   * Saves uploaded profile picture.
   */
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

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
            item.write(new File(uploadDirectory + File.separator + fileName));
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

package com.google.codeu.servlets;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.codeu.data.Datastore;

/**
 *Handles fetching and saving book data.
 */
@WebServlet("/aboutbook")
public class AboutBookServlet extends HttpServlet {
    
  private Datastore datastore;
  
  @Override
  public void init() {
    datastore = new Datastore();
  }
}

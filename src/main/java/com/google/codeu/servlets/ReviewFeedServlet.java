package com.google.codeu.servlets;

import com.google.codeu.data.Datastore;
import com.google.codeu.data.Review;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/reviewFeed")
public class ReviewFeedServlet extends HttpServlet {
  private Datastore datastore;

  @Override
  public void init() {
    datastore = new Datastore();
  }

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
    res.setContentType("application/json");

    List<Review> reviewsList = datastore.getAllReviews();

    Gson gson = new Gson();
    String json = gson.toJson(reviewsList);
    res.getOutputStream().println(json);
  }
}

package com.google.codeu.data;

import java.util.List;
import java.util.UUID;

public class Book {
  private final UUID id;
  private String title;
  private List<String> authors;
  private List<Review> reviews;
  private double avgRating;

  /**
   * Creates a new Book object for every new book for cases when the user wants
   * to provide only the title and author of the book.
   */
  public Book(String title, List<String> authors) {
    this(UUID.randomUUID(), title, authors, null, 0);
  }

  /**
   * Creates a new Book object for every new book for cases when the user wants
   * to provide the complete description of the book.
   */
  public Book(UUID id, String title, List<String> authors, List<Review> reviews,
      double avgRating) {
    this.id = id;
    this.title = title;
    this.authors = authors;
    this.reviews = reviews;
    this.avgRating = avgRating;
  }

  public UUID getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<String> getAuthors() {
    return authors;
  }

  public void setAuthors(List<String> authors) {
    this.authors = authors;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void addReview(Review review) {
    reviews.add(review);
    this.avgRating = calcAvgRating(reviews);
  }

  public double getAvgRating() {
    return avgRating;
  }

  /**
   * Calculates the average rating of a book.
   */
  public double calcAvgRating(List<Review> reviews) {
    double average = 0.0;
    for (int i = 0; i < reviews.size(); i++) {
      average += reviews.get(i).getRating();
    }

    if (reviews.size() == 0) {
      return average;
    }
    return  average / (reviews.size());

  }

}

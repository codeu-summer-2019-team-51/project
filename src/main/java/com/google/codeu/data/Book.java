package com.google.codeu.data;

import java.util.List;

public class Book {
  private final Integer id;
  private String title;
  private List<String> authors;
  private List<Review> reviews;
  private Integer avgRating;

  /**Creates a new Book object for every new book. For cases when the user wants to provide
   * only the title and author of the book*/
  public Book(Integer id, String title, List<String> authors) {
    this.id = id;
    this.title = title;
    this.authors = authors;
  }

  /**Creates a new Book object for every new book.For cases when the user wants to provide
   * the complete description of the book.*/
  public Book(Integer id, String title, List<String> authors, List<Review> reviews) {
    this.id = id;
    this.title = title;
    this.authors = authors;
    this.reviews = reviews;
    this.avgRating = calAvgRating(reviews);
  }

  public Integer getId() {
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
  }

  /**Calculates the average rating of a book.*/
  public Integer calAvgRating(List<Review> reviews) {
    Integer average = 0;
    //average is pos 0
    // sum is pos 1
    if (reviews.size() == 0) {
    } else if (reviews.size() == 1) {
      average = reviews.get(0).getRating();
    } else {
      for (int i = 0; i < reviews.size(); i++) {
        average += reviews.get(i).getRating();
      }
      average = average/(reviews.size());
    }
   return average;
  }

}

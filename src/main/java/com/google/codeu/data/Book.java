package com.google.codeu.data;

import java.util.List;
import java.util.UUID;

public class Book {
  private final UUID id;
  private String title;
  private List<String> authors;
  private double avgRating;

  /**
   * Creates a new Book object for every new book for cases when the user wants
   * to provide only the title and author of the book.
   */
  public Book(String title, List<String> authors) {
    this(UUID.randomUUID(), title, authors, 0);
  }

  /**
   * Constructor method to retrieve existing data from datastore.
   */
  public Book(UUID id, String title, List<String> authors, double avgRating) {
    this.id = id;
    this.title = title;
    this.authors = authors;
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

  public double getAvgRating() {
    return avgRating;
  }

  public void setAvgRating(double avgRating) {
    this.avgRating = avgRating;
  }
}

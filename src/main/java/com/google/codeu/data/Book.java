package com.google.codeu.data;

import java.util.List;

public class Book {
  private final Int id;
  private String title;
  private List<String> authors;
  private List<Reviews> reviews;

  /**Creates a new Book object for every new book.*/
  public Book(Int id, String title, List<String> authors) {
    this.id = id;
    this.title = title;
    this.authors = authors;
  }
  
  /**Creates a new Book object for every new book.*/
  public Book(Int id, String title, List<String> authors, List<Reviews> reviews) {
    this.id = id;
    this.title = title;
    this.authors = authors;
    this.reviews = reviews;
  }

  public Int getId() {
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

  public List<Reviews> getReviews() {
    return reviews;
  }

  public void setReviews(List<Reviews> reviews) {
    this.reviews = reviews;
  }
}
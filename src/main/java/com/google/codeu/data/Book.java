package com.google.codeu.data;

import java.util.List;

public class Book {
  private final Int id;
  private String title;
  private List<String> authors;

  /**Creates a new Book object for every new book.*/
  public Book(Int id, String title, List<String> authors) {
    this.id = id;
    this.title = title;
    this.authors = authors;
  }

}
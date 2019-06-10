package com.google.codeu.data;

import java.util.List;

public class Book {
  private final Int id;
  private String title;
  private String[] authors;

  /*Creates a new Book object for every new book.*/
  public Book(Int id, String title, SAtring[] authors) {
    this.id = id;
    this.title = title;
    this.authors = authors;
  }
}
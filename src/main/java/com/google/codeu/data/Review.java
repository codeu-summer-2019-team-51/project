package com.google.codeu.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Review {
  private final UUID reviewId; //This is the ID of the review
  private final long timestamp;
  private String author; //The user posting the review
  private long rating;
  private String comment;
  private List<String> pictures;
  private final String bookId;
  private String bookName;

  /**
   * Creates a new Review object for every new review for cases when
   * the user wants to provide only a rating.
   */
  public Review(String author, long rating, String bookId, String bookName) {
    this(UUID.randomUUID(), System.currentTimeMillis(), author, rating, "",
        new ArrayList<String>(), bookId, bookName);
  }

  /**
   * Creates a new Review object for every new review for cases when
   * the user wants to provide a review without any pictures.
   */
  public Review(String author, long rating, String comment, String bookId,String bookName) {
    this(UUID.randomUUID(), System.currentTimeMillis(), author, rating, comment,
        new ArrayList<String>(), bookId, bookName);
  }

  /**
   * Creates a new Review object for every new review for cases when
   * the user wants to provide a complete review.
   */
  public Review(String author, long rating, String comment,
                List<String> pictures, String bookId, String bookName) {
    this(UUID.randomUUID(), System.currentTimeMillis(), author, rating, comment,
            pictures, bookId, bookName);
  }

  /**
   * Constructor method to retrieve existing data from datastore.
   */
  public Review(UUID reviewId, long timestamp, String author, long rating,
                String comment, List<String> pictures, String bookId, String bookName) {
    this.reviewId = reviewId;
    this.author = author;
    this.rating = rating;
    this.comment = comment;
    this.pictures = pictures;
    this.timestamp = timestamp;
    this.bookId = bookId;
    this.bookName = bookName;
  }

  public UUID getId() {
    return reviewId;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public long getRating() {
    return rating;
  }

  public void setRating(long rating) {
    this.rating = rating;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public List<String> getPictures() {
    return pictures;
  }

  public void setPictures(List<String> pictures) {
    this.pictures = pictures;
  }

  public String getBookId() {
    return bookId;
  }

  public String getBookName() {
    return bookName;
  }
}

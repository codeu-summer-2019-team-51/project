package com.google.codeu.data;

import java.util.List;
import java.util.UUID;

public class Review {
  private final UUID reviewId; //This is the ID of the review
  private final long timestamp;
  private String author; //The user posting the review
  private int rating;
  private String comment;
  private List<String> pictures;

  /**
   * Creates a new Review object for every new review for cases when
   * the user wants to provide only a rating.
   */
  public Review(String author, int rating) {
    this(UUID.randomUUID(),System.currentTimeMillis(), author, rating,null,
            null);
  }
  
  /**
   * Creates a new Review object for every new review for cases when
   * the user wants to provide a review without any pictures.
   */
  public Review(String author, int rating, String comment) {
    this(UUID.randomUUID(),System.currentTimeMillis(), author, rating, comment,
            null);
  }

  /**
   * Creates a new Review object for every new review for cases when
   * the user wants to provide a complete review.
   */
  public Review(String author, int rating, String comment,
                List<String> pictures) {
    this(UUID.randomUUID(),System.currentTimeMillis(), author, rating, comment,
            pictures);
  }
  
  /**
   * Standard constructor called within all other constructors.
   */
  public Review(UUID reviewId, long timestamp, String author, int rating, 
          String comment, List<String> pictures) {
    this.reviewId = reviewId;
    this.author = author;
    this.rating = rating;
    this.comment = comment;
    this.pictures = pictures;
    this.timestamp = timestamp;
  }

  public UUID getReviewId() {
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

  public int getRating() {
    return rating;
  }

  public void setRating(int rating) {
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

}

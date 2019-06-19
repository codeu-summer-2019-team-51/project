package com.google.codeu.data;

import java.util.List;
import java.util.UUID;

public class Review {
  private final UUID reviewId; //This is the ID of the review
  private long timestamp;
  private String author; //The user posting the review
  private long rating;
  private String comment;
  private List<String> pictures;

  /**
   * Creates a new Review object for every new review for cases when
   * the user wants to provide only a rating.
   */
  public Review(String author, int rating) {
    this.reviewId = UUID.randomUUID();
    this.author = author;
    this.rating = rating;
    this.timestamp = System.currentTimeMillis();
  }

  /**
   * Creates a new Review object for every new review for cases when
   * the user wants to provide a complete review.
   */
  public Review(String author, long rating, String comment,
                List<String> pictures) {
    this.reviewId = UUID.randomUUID();
    this.author = author;
    this.rating = rating;
    this.comment = comment;
    this.pictures = pictures;
    this.timestamp = System.currentTimeMillis();
  }

  public Review(String author, long rating, String comment, long timestamp) {
    this.reviewId = UUID.randomUUID();
    this.author = author;
    this.rating = rating;
    this.comment = comment;
    this.timestamp = timestamp;
    this.pictures = null;
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

  public long getRating() {
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

package com.google.codeu.data;

import java.util.List;
import java.util.UUID;

public class Review {
  private final UUID reviewId;
  private String author;
  private int rating;
  private String comment;
  private List<String> pictures;

  /** 
   * Creates a new Review object for every new review for cases when
   *  the user wants to provide only a rating.
   */
  public Review(int rating) {
    this.reviewId = UUID.randomUUID();
    this.rating = rating;
  }

  /** 
   * Creates a new Review object for every new review for cases when
   * the user wants to provide a complete review.
   */
  public Review(String author, int rating, String comment,
                List<String> pictures) {
    this.reviewId = UUID.randomUUID();
    this.author = author;
    this.rating = rating;
    this.comment = comment;
    this.pictures = pictures;
  }

  public UUID getReviewId() {
    return reviewId;
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

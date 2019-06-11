package com.google.codeu.data;

import java.util.List;

public class Review {
  private final Integer reviewId;
  private String author;
  private Integer rating;
  private String comment;
  private List<String> pictures;

  /** Creates a new Review object for every new review. For cases when
   *  the user wants to provide only a rating.*/
  public Review(Integer reviewId, Integer rating) {
    this.reviewId = reviewId;
    this.rating = rating;
  }

  /** Creates a new Review object for every new review. For cases when
   * the user wants to provide a complete review.*/
  public Review(Integer reviewId, String author, Integer rating, String comment,
          List<String> pictures) {
    this.reviewId = reviewId;
    this.author = author;
    this.rating = rating;
    this.comment = comment;
    this.pictures = pictures;
  }

  public Integer getReviewId() {
    return reviewId;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
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

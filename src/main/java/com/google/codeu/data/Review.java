package com.google.codeu.data;

import java.util.List;

public class Review {
  private final Int reviewId;
 private List<Int> ratings;
 private List<String> comments;
 private List<String> pictures;

 /** Creates a new Review object for every new review.*/
 public Review(Int reviewId, List<Int> ratings) {
   this.reviewId = reviewId;
   this.ratings = ratings;
 }

 public Review(Int reviewId, List<Int> ratings, List<String> comments, List<String> pictures) {
   this.reviewId = reviewId;
   this.ratings = ratings;
   this.comments = comments;
   this.pictures = pictures;
 }

  public Int getReviewId() {
    return reviewId;
  }

  public List<Int> getRatings() {
    return ratings;
  }

  public void setRatings(List<Int> ratings) {
    this.ratings = ratings;
  }

  public List<String> getComments() {
    return comments;
  }

  public void setComments(List<String> comments) {
    this.comments = comments;
  }

  public List<String> getPictures() {
    return pictures;
  }

  public void setPictures(List<String> pictures) {
    this.pictures = pictures;
  }
}
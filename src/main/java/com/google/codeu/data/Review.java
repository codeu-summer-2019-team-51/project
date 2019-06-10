package com.google.codeu.data;

import java.util.List;

public class Review {
  private final Integer reviewId;
  private Integer rating;
  private List<String> comments;
  private List<String> pictures;

  /** Creates a new Review object for every new review. For cases when
   *  the user wants to provide only a rating.*/
  public Review(Integer reviewId, Integer rating) {
    this.reviewId = reviewId;
    this.rating = rating;
  }

  /** Creates a new Review object for every new review. For cases when
   * the user wants to provide a complete review.*/
  public Review(Integer reviewId, Integer rating, List<String> comments, List<String> pictures) {
    this.reviewId = reviewId;
    this.rating = rating;
    this.comments = comments;
    this.pictures = pictures;
  }

  public Integer getReviewId() {
    return reviewId;
  }

  public Integer getRating() {
    return rating;
  }

  public void setRating(Integer rating) {
    this.rating = rating;
  }

  public List<String> getComments() {
    return comments;
  }

/**  public void setComments(List<String> comments) {
*    this.comments = comments;
*  }
* Will decide which one of the methods (commented one or the one mentioned below)
* will be used.
*/
  
  public void addComment (String comment) {
      comments.add(comment);
  }

  public List<String> getPictures() {
    return pictures;
  }

  public void setPictures(List<String> pictures) {
    this.pictures = pictures;
  }

}

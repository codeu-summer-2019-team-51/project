package com.google.codeu.data;

import java.util.List;

public class Book {

	private final Int id;
	private String title;
	private String[] authors;
	private List<Int> ratings;
	private List<String> reviews;
	private String coverPic;
	private List<String> morePics;//required or not?

	/*Creates a new Book object for every new book.*/
	public Book(Int id, String title, String[] authors) {
		this.id = id;
		this.title = title;
		this.authors = authors;
	}
	public Book(Int id, String title, String[] authors, List<Int> ratings, List<String> reviews, String coverPic, List<String> morePics){
		this.id = id;
		this.title = title;
		this.authors = authors;
		this.ratings = ratings;
		this.reviews = reviews;
		this.coverPic = coverPic;
		this.morePics = morePics;
	}

	/*Getters and Setters*/
	public Int getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getAuthors() {
		return authors;
	}

	public void setAuthors(String[] authors) {
		this.authors = authors;
	}

	public List<Int> getRatings() {
		return ratings;
	}

	public void setRatings(List<Int> ratings) {
		this.ratings = ratings;
	}

	public List<String> getReviews() {
		return reviews;
	}

	public void setReviews(List<String> reviews) {
		this.reviews = reviews;
	}

	public String getCoverPic() {
		return coverPic;
	}

	public void setCoverPic(String coverPic) {
		this.coverPic = coverPic;
	}

	public List<String> getMorePics() {
		return morePics;
	}

	public void setMorePics(List<String> morePics) {
		this.morePics = morePics;
	}
}
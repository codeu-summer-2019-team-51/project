/*
 * Copyright 2019 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.codeu.data;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.SortDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Provides access to the data stored in Datastore.
 */
public class Datastore {

  private DatastoreService datastore;

  public Datastore() {
    datastore = DatastoreServiceFactory.getDatastoreService();
  }

  /**
   * Stores the Message in Datastore.
   */
  public void storeMessage(Message message) {
    Entity messageEntity = new Entity("Message", message.getId().toString());
    messageEntity.setProperty("user", message.getUser());
    messageEntity.setProperty("text", message.getText());
    messageEntity.setProperty("timestamp", message.getTimestamp());

    datastore.put(messageEntity);
  }

  /**
   * Stores the Review in Datastore.
   */
  public void storeReview(Review review) {
    Entity reviewEntity = new Entity("Review", review.getReviewId().toString());
    reviewEntity.setProperty("author", review.getAuthor());
    reviewEntity.setProperty("comment", review.getComment());
    reviewEntity.setProperty("rating", review.getRating());
    reviewEntity.setProperty("timestamp", review.getTimestamp());
    datastore.put(reviewEntity);
  }


  /**
   * Gets messages posted by a specific user.
   *
   * @return a list of messages posted by the user, or empty list if user has never posted a
   * message. List is sorted by time descending.
   */
  public List<Message> getMessages(String user) {
    Query query =
            new Query("Message")
                    .setFilter(new Query.FilterPredicate("user", FilterOperator.EQUAL, user))
                    .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    return createListOfMessages(results, user);
  }

  /**
   * Gets messages posted by all users.
   *
   * @return a list of all messages posted. List is sorted by time descending.
   */
  public List<Message> getAllMessages() {
    Query query = new Query("Message").addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    return createListOfMessages(results, null);
  }

  /**
   * Creates a list of messages either with user id or without.
   *
   * @return a list of all messages posted or by a specific user
   */
  public List<Message> createListOfMessages(PreparedQuery results, String user) {
    List<Message> messages = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      try {
        String idString = entity.getKey().getName();
        UUID id = UUID.fromString(idString);
        String temp = user == null ? (String) entity.getProperty("user") : user;
        String text = (String) entity.getProperty("text");
        long timestamp = (long) entity.getProperty("timestamp");

        Message message = new Message(id, temp, text, timestamp);
        messages.add(message);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return messages;
  }

  /**
   * Creates a list of reviews with or without id.
   *
   * @return a list of all reviews posted or by a specific user
   */
  public List<Review> createListOfReviews(PreparedQuery results, String user) {
    List<Review> reviews = new ArrayList<>();
    for (Entity entity: results.asIterable()) {
      try {
        String idString = entity.getKey().getName();
        UUID id = UUID.fromString(idString);
        String temp = user == null ? (String) entity.getProperty("author") : user;
        long rating = (Long) entity.getProperty("rating");
        String text = (String) entity.getProperty("comment");
        long timestamp = (long) entity.getProperty("timestamp");

        Review review = new Review(temp,rating,text,timestamp);
        reviews.add(review);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }
    return reviews;
  }

  /** Stores the User in Datastore. */
  public void storeUser(User user) {
    Entity userEntity = new Entity("User", user.getEmail());
    userEntity.setProperty("email", user.getEmail());
    userEntity.setProperty("aboutMe", user.getAboutMe());
    userEntity.setProperty("profilePic", user.getProfilePic());
    datastore.put(userEntity);
  }

  /**
   * Returns the User owned by the email address, or
   * null if no matching User was found.
   */
  public User getUser(String email) {
    Query query = new Query("User")
        .setFilter(new Query.FilterPredicate("email", FilterOperator.EQUAL, email));
    PreparedQuery results = datastore.prepare(query);
    Entity userEntity = results.asSingleEntity();
    if (userEntity == null) {
      return null;
    }

    String aboutMe = (String) userEntity.getProperty("aboutMe");
    String profilePic = (String) userEntity.getProperty("profilePic");
    User user = new User(email, aboutMe, profilePic);

    return user;
  }

  /**
   * Gets reviews posted by all users.
   *
   * @return a list of all reviews posted. List is sorted by time descending.
   */
  public List<Review> getAllReviews() {
    Query query = new Query("Review").addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    return createListOfReviews(results, null);
  }

  /**
   * Gets reviews posted by a specific user.
   *
   * @return a list of reviews posted by the user, or empty list if user has never posted a review.
   */
  public List<Review> getReviews(String user) {
    Query query =
            new Query("Review")
                    .setFilter(new Query.FilterPredicate("user", FilterOperator.EQUAL, user))
                    .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    return createListOfReviews(results, user);
  }

  /**
   * Stores the Book in Datastore.
   */
  public void storeBook(Book book) {
    Entity bookEntity = new Entity("Book", book.getId().toString());
    bookEntity.setProperty("title", book.getTitle());
    bookEntity.setProperty("authors", book.getAuthors());
    bookEntity.setProperty("reviews", book.getReviews());
    bookEntity.setProperty("avgRating", book.getAvgRating());

    datastore.put(bookEntity);
  }

  /**
   * Gets all books.
   *
   * @return a list of books. List is sorted by title.
   */
  public List<Book> getAllBooks() {
    List<Book> books = new ArrayList<Book>();

    Query query = new Query("Book").addSort("title", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        String idString = entity.getKey().getName();
        UUID id = UUID.fromString(idString);
        String title = (String) entity.getProperty("title");
        List<String> authors = (List<String>) entity.getProperty("authors");
        List<Review> reviews = (List<Review>) entity.getProperty("reviews");
        double avgRating = (double) entity.getProperty("avgRating");

        Book book = new Book(id, title, authors, reviews, avgRating);
        books.add(book);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return books;
  }
}

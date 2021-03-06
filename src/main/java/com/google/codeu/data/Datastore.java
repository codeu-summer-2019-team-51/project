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
import com.google.codeu.common.Tree;

import java.util.*;

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
   * Returns messages posted by a specific user.
   *
   * @return a list of messages posted by the user, or empty list if user has
   * never posted a message. List is sorted by time descending.
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
   * Returns messages posted by all users.
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
  private List<Message> createListOfMessages(PreparedQuery results, String user) {
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
  private List<Review> createListOfReviews(PreparedQuery results, String user) {
    List<Review> reviews = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      try {
        String idString = entity.getKey().getName();
        UUID id = UUID.fromString(idString);
        String temp = user == null ? (String) entity.getProperty("author") : user;
        long rating = (Long) entity.getProperty("rating");
        String text = (String) entity.getProperty("comment");
        long timestamp = (long) entity.getProperty("timestamp");
        String bookId = (String) entity.getProperty("bookId");
        List<String> pictures = (List<String>) entity.getProperty("pictures");
        String bookName = getBook(bookId).getTitle();

        Review review = new Review(id, timestamp, temp, rating, text, pictures, bookId, bookName);
        reviews.add(review);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }
    return reviews;
  }


  /**
   * Stores the User in Datastore.
   */
  public void storeUser(User user) {
    Entity userEntity = new Entity("User", user.getEmail());
    userEntity.setProperty("aboutMe", user.getAboutMe());
    userEntity.setProperty("profilePic", user.getProfilePic());
    datastore.put(userEntity);
  }

  /**
   * Returns the User owned by the email address, or
   * null if no matching User was found.
   */
  public User getUser(String email) {
    Key key = KeyFactory.createKey("User", email);
    try {
      Entity userEntity = datastore.get(key);
      return entityToUser(userEntity);
    } catch (EntityNotFoundException e) {
      return null;
    }
  }

  /**
   * Returns the list of Users who have posted reviews.
   */
  public Set<String> getUserEmailsWithReviews() {
    Set<String> users = new HashSet<>();
    Query query = new Query("Review");
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      users.add((String) entity.getProperty("author"));
    }
    return users;
  }

  /**
   * Returns a list of all users.
   */
  public Map<String, User> getAllUsers() {
    Query query = new Query("User");
    PreparedQuery results = datastore.prepare(query);

    Map<String, User> users = new HashMap<String, User>();
    for (Entity entity : results.asIterable()) {
      try {
        String userEmail = entity.getKey().getName();
        users.put(userEmail, entityToUser(entity));
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return users;
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
  public List<Review> getReviewsByUser(String user) {
    Query query =
        new Query("Review")
            .setFilter(new Query.FilterPredicate("author", FilterOperator.EQUAL, user))
            .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    return createListOfReviews(results, user);
  }

  /**
   * Returns a list of books. List is sorted by title.
   */
  public List<Book> getAllBooks() {
    List<Book> books = new ArrayList<Book>();

    Query query = new Query("Book").addSort("title", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        books.add(entityToBook(entity));
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }
    return books;
  }

  /**
   * Stores the Review in Datastore.
   */
  public void storeReview(Review review) {
    Entity reviewEntity = new Entity("Review", review.getId().toString());
    reviewEntity.setProperty("timestamp", review.getTimestamp());
    reviewEntity.setProperty("author", review.getAuthor());
    reviewEntity.setProperty("rating", review.getRating());
    reviewEntity.setProperty("comment", review.getComment());
    reviewEntity.setProperty("pictures", review.getPictures());
    reviewEntity.setProperty("bookId", review.getBookId());

    datastore.put(reviewEntity);
  }

  /**
   * Returns a list of books. List is sorted by title.
   */
  public List<Review> getReviewsForBook(String bookId) {
    List<Review> reviews = new ArrayList<Review>();

    Query query = new Query("Review")
        .setFilter(new Query.FilterPredicate("bookId",
            FilterOperator.EQUAL, bookId))
        .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        reviews.add(entityToReview(entity, bookId));
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }
    return reviews;
  }

  /**
   * Stores the {@link UserBook} in Datastore.
   */
  public void storeUserBook(UserBook userBook) {
    Entity userBookEntity =
        new Entity("UserBook", userBook.getIdString());
    userBookEntity.setProperty("user", userBook.getUser());
    userBookEntity.setProperty("bookId", userBook.getBookId());
    userBookEntity.setProperty("status", userBook.getStatus());
    userBookEntity.setProperty("timestamp", userBook.getTimestamp());

    datastore.put(userBookEntity);
  }

  /**
   * Returns a list of {@link UserBook}s.
   */
  public List<UserBook> getUserBooks(String user) {
    List<UserBook> userBooks = new ArrayList<UserBook>();

    Query query = new Query("UserBook")
        .setFilter(new Query.FilterPredicate("user",
            FilterOperator.EQUAL, user))
        .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        UserBook userBook = entityToUserBook(entity);

        Key bookKey = KeyFactory.createKey("Book", userBook.getBookId());
        Book book = entityToBook(datastore.get(bookKey));
        userBook.setBook(book);

        userBooks.add(userBook);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return userBooks;
  }

  /**
   * Stores the {@code community} in Datastore.
   */
  public void storeCommunity(Community community) {
    Entity communityEntity = new Entity("Community", community.getId().toString());
    communityEntity.setProperty("name", community.getName());
    communityEntity.setProperty("description", community.getDescription());
    communityEntity.setProperty("members", community.getMembers());

    datastore.put(communityEntity);
  }

  /**
   * Returns the Community with the specified {@code idString} or
   * null if no matching Community was found.
   */
  public Community getCommunity(String idString) {
    Key key = KeyFactory.createKey("Community", idString);
    try {
      Entity communityEntity = datastore.get(key);
      return entityToCommunity(communityEntity);
    } catch (EntityNotFoundException e) {
      return null;
    }
  }

  /**
   * Returns a list of all {@code community}s.
   */
  //TODO: decide how to sort communities
  public List<Community> getAllCommunities() {
    Query query = new Query("Community")
        .addSort("name", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    List<Community> communities = new ArrayList<Community>();
    for (Entity entity : results.asIterable()) {
      try {
        communities.add(entityToCommunity(entity));
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return communities;
  }

  /**
   * Stores the {@code thread} in Datastore.
   */
  public void storeThread(Thread thread) {
    Entity threadEntity = new Entity("Thread", thread.getId().toString());
    threadEntity.setProperty("name", thread.getName());
    threadEntity.setProperty("description", thread.getDescription());
    threadEntity.setProperty("creator", thread.getCreator());
    threadEntity.setProperty("communityId", thread.getCommunityId());

    datastore.put(threadEntity);
  }

  /**
   * Returns the Thread with the specified {@code idString} or
   * null if no matching Thread was found.
   */
  public Thread getThread(String idString) {
    Key key = KeyFactory.createKey("Thread", idString);
    try {
      Entity threadEntity = datastore.get(key);
      return entityToThread(threadEntity);
    } catch (EntityNotFoundException e) {
      return null;
    }
  }

  /**
   * Returns a list of threads posted in a community, or empty list if the
   * community has no thread. List is sorted by name ascending.
   */
  //TODO: find a better way to sort threads
  public List<Thread> getThreads(String communityId) {
    List<Thread> threads = new ArrayList<Thread>();

    Query query =
        new Query("Thread")
            .setFilter(new Query.FilterPredicate("communityId",
                FilterOperator.EQUAL, communityId))
            .addSort("name", SortDirection.ASCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        threads.add(entityToThread(entity, communityId));
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return threads;
  }

  /**
   * Stores the {@code comment} in Datastore.
   */
  public void storeComment(Comment comment) {
    Entity commentEntity = new Entity("Comment", comment.getId().toString());
    commentEntity.setProperty("text", comment.getText());
    commentEntity.setProperty("user", comment.getUser());
    commentEntity.setProperty("timestamp", comment.getTimestamp());
    commentEntity.setProperty("parentId", comment.getParentId());
    commentEntity.setProperty("threadId", comment.getThreadId());

    datastore.put(commentEntity);
  }

  /**
   * Returns a tree of comments posted in a thread, or empty tree if the
   * thread has no comment. If a node in a tree has multiple children, the
   * children are sorted by timestamp descending.
   */
  public Tree<Comment> getComments(String threadId) {
    Tree<Comment> comments = new Tree<Comment>();

    Query query =
        new Query("Comment")
            .setFilter(new Query.FilterPredicate("threadId",
                FilterOperator.EQUAL, threadId))
            .addSort("timestamp", SortDirection.DESCENDING);
    PreparedQuery results = datastore.prepare(query);

    for (Entity entity : results.asIterable()) {
      try {
        Comment comment = entityToComment(entity, threadId);
        String childId = entity.getKey().getName();
        String parentId = comment.getParentId();
        comments.add(childId, comment, parentId);
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }

    return comments;
  }

  /**
   * Creates a list of messages either with user id or without.
   *
   * @return a list of all messages posted or by a specific user
   */
  private List<Message> createList(PreparedQuery results, String user) {
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
   * Converts Entity to User.
   */
  private User entityToUser(Entity entity) {
    String email = entity.getKey().getName();
    String aboutMe = (String) entity.getProperty("aboutMe");
    String profilePic = (String) entity.getProperty("profilePic");

    User user = new User(email, aboutMe, profilePic);
    return user;
  }

  /**
   * Converts Entity to Book.
   */
  private Book entityToBook(Entity entity) {
    String idString = entity.getKey().getName();

    UUID id = UUID.fromString(idString);
    String title = (String) entity.getProperty("title");
    List<String> authors = (List<String>) entity.getProperty("authors");
    double avgRating = (double) getBookRating(idString);
    String description = (String) entity.getProperty("description");

    Book book = new Book(id, title, authors, avgRating, description);
    return book;
  }

  /**
   * Converts Entity to Review.
   */
  private Review entityToReview(Entity entity, String bookId) {
    String idString = entity.getKey().getName();

    UUID id = UUID.fromString(idString);
    long timestamp = (long) entity.getProperty("timestamp");
    String author = (String) entity.getProperty("author");
    long rating = (long) entity.getProperty("rating");
    String comment = (String) entity.getProperty("comment");
    List<String> pictures = (List<String>) entity.getProperty("pictures");
    String bookName = getBook(bookId).getTitle();

    Review review = new Review(id, timestamp, author, rating, comment, pictures,
        bookId, bookName);
    return review;
  }

  /**
   * Converts Entity to UserBook.
   */
  private UserBook entityToUserBook(Entity entity) {
    String idString = entity.getKey().getName();

    String user = (String) entity.getProperty("user");
    String bookId = (String) entity.getProperty("bookId");
    String status = (String) entity.getProperty("status");
    long timestamp = (long) entity.getProperty("timestamp");

    UserBook userBook = new UserBook(idString, user, bookId, status, timestamp);
    return userBook;
  }

  /**
   * Converts Entity to Community.
   */
  private Community entityToCommunity(Entity entity) {
    String idString = entity.getKey().getName();

    UUID id = UUID.fromString(idString);
    String name = (String) entity.getProperty("name");
    String description = (String) entity.getProperty("description");
    Set<String> members =
        new HashSet<String>((List<String>) entity.getProperty("members"));
    // Note that members retrieved from datastore is of instance type List

    Community community = new Community(id, name, description, members);
    return community;
  }

  /**
   * Converts Entity to Thread.
   */
  private Thread entityToThread(Entity entity) {
    return entityToThread(entity, null);
  }

  /**
   * Converts Entity to Thread.
   */
  private Thread entityToThread(Entity entity, String communityId) {
    String idString = entity.getKey().getName();

    UUID id = UUID.fromString(idString);
    String name = (String) entity.getProperty("name");
    String description = (String) entity.getProperty("description");
    String creator = (String) entity.getProperty("creator");
    if (communityId == null) {
      communityId = (String) entity.getProperty("communityId");
    }

    Thread thread = new Thread(id, name, description, creator, communityId);
    return thread;
  }

  /**
   * Converts Entity to Comment.
   */
  private Comment entityToComment(Entity entity) {
    return entityToComment(entity, null);
  }

  /**
   * Converts Entity to Comment.
   */
  private Comment entityToComment(Entity entity, String threadId) {
    String idString = entity.getKey().getName();

    UUID id = UUID.fromString(idString);
    String text = (String) entity.getProperty("text");
    String user = (String) entity.getProperty("user");
    long timestamp = (long) entity.getProperty("timestamp");
    String parentId = (String) entity.getProperty("parentId");
    if (threadId == null) {
      threadId = (String) entity.getProperty("threadId");
    }

    Comment comment = new Comment(id, text, user, timestamp, parentId, threadId);
    return comment;
  }

  /**
   * Calculate book's average rating.
   */
  private double getBookRating(String bookId) {
    Query query = new Query("Review")
        .setFilter(new Query.FilterPredicate("bookId",
            FilterOperator.EQUAL, bookId));
    PreparedQuery results = datastore.prepare(query);

    long totalRating = 0;
    int count = 0;
    for (Entity entity : results.asIterable()) {
      try {
        totalRating += (long) entity.getProperty("rating");
        count++;
      } catch (Exception e) {
        System.err.println("Error reading message.");
        System.err.println(entity.toString());
        e.printStackTrace();
      }
    }
    if (count == 0) {
      return 0;
    }
    return (new Long(totalRating)).doubleValue()
        / (new Integer(count)).doubleValue();
  }

  /**
   * Stores the Book in Datastore.
   */
  public void storeBook(Book book) {
    Entity bookEntity = new Entity("Book", book.getId().toString());
    bookEntity.setProperty("id", book.getId().toString());
    bookEntity.setProperty("title", book.getTitle());
    bookEntity.setProperty("authors", book.getAuthors());
    bookEntity.setProperty("avgRating", book.getAvgRating());
    bookEntity.setProperty("description", book.getDescription());
    datastore.put(bookEntity);
  }

  /**
   * Returns the Book identified by the id, or
   * null if no matching Book was found.
   */
  public Book getBook(String id) {

    Key key = KeyFactory.createKey("Book", id);
    try {
      Entity bookEntity = datastore.get(key);
      return entityToBook(bookEntity);
    } catch (EntityNotFoundException e) {
      return null;
    }
  }
}

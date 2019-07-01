package com.google.codeu.data;

import java.util.UUID;

public class Post {

  private final UUID id;
  private final User user;
  private String text;
  private final long timestamp;
  //TODO: allow post to reply to another post so that all the posts in a thread
  //relate to each other in a tree structure

  /**
   * Constructs a new {@link Post} posted by {@code user} with {@code text}
   * content. Generates a random ID and uses the current system time for the
   * creation time.
   */
  public Post(User user, String text) {
    this(UUID.randomUUID(), user, text, System.currentTimeMillis());
  }

  /**
   * Constructs a new {@link Post} with all its attributes filled. It is
   * used to create a {@link Post} based on {@link Entity} stored in
   * {@link Datastore}.
   */
  public Post(UUID id, User user, String text, long timestamp) {
    this.id = id;
    this.user = user;
    this.text = text;
    this.timestamp = timestamp;
  }

  public UUID getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public String getText() {
    return text;
  }

  public void setText() {
    this.text = text;
  }

  public long getTimestamp() {
    return timestamp;
  }

}

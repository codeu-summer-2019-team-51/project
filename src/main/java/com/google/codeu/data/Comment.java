package com.google.codeu.data;

import java.util.UUID;

public class Comment {

  private final UUID id;
  private String text;
  private final String user;
  private final long timestamp;
  private final String parentId;
  private final String threadId;

  //TODO: allow comment to reply to another comment so that all the comments in
  //a thread relate to each other in a tree structure

  /**
   * Constructs a new {@link Comment} commented by {@code user} with
   * {@code text} content. Generates a random ID and uses the current system
   * time for the creation time.
   */
  public Comment(String text, String user, String parentId, String threadId) {
    this(UUID.randomUUID(), text, user, System.currentTimeMillis(), parentId,
        threadId);
  }

  /**
   * Constructs a new {@link Comment} with all its attributes filled. It is
   * used to create a {@link Comment} based on {@link Entity} stored in
   * {@link Datastore}.
   */
  public Comment(UUID id, String text, String user, long timestamp,
      String parentId, String threadId) {
    this.id = id;
    this.text = text;
    this.user = user;
    this.timestamp = timestamp;
    this.parentId = parentId;
    this.threadId = threadId;
  }

  public UUID getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public String getUser() {
    return user;
  }

  public void setText() {
    this.text = text;
  }

  public long getTimestamp() {
    return timestamp;
  }

  public String getParentId() {
    return parentId;
  }

  public String getThreadId() {
    return threadId;
  }
}

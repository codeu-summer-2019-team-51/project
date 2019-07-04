package com.google.codeu.data;

import java.util.HashMap;
import java.util.Map;

public class UserBook {
  private final String idString;
  // idString is concatenated from user and bookId to create a composite ID.
  // This is to prevent duplicate UserBook entities in datastore having the same
  // combination of user and bookId
  private final String user;
  private final String bookId;
  private ReadingStatus status;
  private long timestamp;

  /**
   * Constructs a new {@link UserBook} object when a {@code user} saves a book
   * with {@code bookId}. Generates ID by concatenating user and bookId.
   */
  public UserBook(String user, String bookId, String status) {
    this(user + bookId, bookId, user, status, System.currentTimeMillis());
  }

  /**
   * Constructs a new {@link UserBook} with all its attributes filled based on
   * {@link Entity} stored in {@link Datastore}.
   */
  public UserBook(String idString, String user, String bookId, String status,
      long timestamp) {
    this.idString = idString;
    this.bookId = bookId;
    this.user = user;
    this.status = ReadingStatus.fromString(status);
    this.timestamp = timestamp;
  }

  public String getIdString() {
    return idString;
  }

  public String getBookId() {
    return bookId;
  }

  public String getUser() {
    return user;
  }

  public String getStatus() {
    return status.toString();
  }

  public void setStatus(String status) {
    this.status = ReadingStatus.fromString(status);
  }

  public long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(long timestamp) {
    this.timestamp = timestamp;
  }

  // Ensures that reading status is nothing but one of the predefined statuses
  private enum ReadingStatus {
    READ           ("READ"),
    READING        ("READING"),
    WANT_TO_READ   ("WANT_TO_READ"),
    DID_NOT_FINISH ("DID_NOT_FINISH");

    private final String string;
    private static Map<String, ReadingStatus> map =
        new HashMap<String, ReadingStatus>();

    ReadingStatus(String string) {
      this.string = string;
    }

    public String toString() {
      return string;
    }

    public static ReadingStatus fromString(String string) {
      return map.get(string);
    }

    static {
      for (ReadingStatus status : ReadingStatus.values()) {
        map.put(status.string, status);
      }
    }
  }
}

package com.google.codeu.data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SavedBook {
  private final UUID id;
  private final String bookId;
  private final String user;
  private ReadingStatus status;
  private long timestamp;

  /**
   * Constructs a new {@link SavedBook} object when a {@code user} saves a book
   * with {@code bookId}. Generates a random ID.
   */
  public SavedBook(String bookId, String user, String status) {
    this(UUID.randomUUID(), bookId, user, status, System.currentTimeMillis());
  }

  /**
   * Constructs a new {@link SavedBook} with all its attributes filled based on
   * {@link Entity} stored in {@link Datastore}.
   */
  public SavedBook(UUID id, String bookId, String user, String status,
      long timestamp) {
    this.id = id;
    this.bookId = bookId;
    this.user = user;
    this.status = ReadingStatus.fromString(status);
    this.timestamp = timestamp;
  }

  public UUID getId() {
    return id;
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

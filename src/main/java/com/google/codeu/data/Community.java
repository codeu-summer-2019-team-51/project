package com.google.codeu.data;

import com.google.codeu.data.Thread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Community {

  private final UUID id;
  private String name;
  private String description;
  private List<String> members;
  private List<Thread> threads;
  //TODO: add moderators with more permissions than members

  /**
   * Constructs a new {@link Community} called {@code name} created by
   * {@code creator}. Generates a random ID.
   */
  public Community(String name, String description, String creator) {
    this(UUID.randomUUID(), name, description,
        (List<String>) Arrays.asList(creator),
        (List<Thread>) new ArrayList<Thread>());
  }

  /**
   * Constructs a new {@link Community} with all its attributes filled. It is
   * used to create a {@link Community} based on {@link Entity} stored in
   * {@link Datastore}.
   */
  public Community(UUID id, String name, String description, List<String> members,
      List<Thread> threads) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.members = members;
    this.threads = threads;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getMembers() {
    return members;
  }

  // Should we have a setMembers method or addMember and deleteMember methods?
  public void setMembers(List<String> members) {
    this.members = members;
  }

  public List<Thread> getThreads() {
    return threads;
  }

  // Should we have a setThreads method or addThread and deleteThread methods?
  public void setThreads(List<Thread> threads) {
    this.threads = threads;
  }
}

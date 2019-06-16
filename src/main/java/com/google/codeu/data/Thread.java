package com.google.codeu.data;

import java.util.UUID;

public class Thread {

  private final UUID id;
  private String name;
  private String description;
  private final String creator;
  private final String communityId;

  /**
   * Constructs a new {@link Thread} titled {@code name} with content
   * {@code description}. Generates a random ID.
   */
  public Thread(String name, String description, String creator,
      String communityId) {
    this(UUID.randomUUID(), name, description, creator, communityId);
  }

  /**
   * Constructs a new {@link Thread} with all its attributes filled. It is
   * used to create a {@link Thread} based on {@link Entity} stored in
   * {@link Datastore}.
   */
  public Thread(UUID id, String name, String description, String creator,
      String communityId) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.creator = creator;
    this.communityId = communityId;
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

  public String getCreator() {
    return creator;
  }

  public String getCommunityId() {
    return communityId;
  }
}

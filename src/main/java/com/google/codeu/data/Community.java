package com.google.codeu.data;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Community {

  private final UUID id;
  private String name;
  private String description;
  private Set<String> members;
  //TODO: add moderators with more permissions than members

  /**
   * Constructs a new {@link Community} called {@code name} created by
   * {@code creator}. Generates a random ID.
   */
  public Community(String name, String description, String creator) {
    this(UUID.randomUUID(), name, description,
        (new HashSet<String>(Arrays.asList(creator))));
  }

  /**
   * Constructs a new {@link Community} with all its attributes filled. It is
   * used to create a {@link Community} based on {@link Entity} stored in
   * {@link Datastore}.
   */
  public Community(UUID id, String name, String description,
                   Set<String> members) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.members = members;
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

  public Set<String> getMembers() {
    return members;
  }

  // Should we have a setMembers method or addMember and deleteMember methods?
  public void setMembers(Set<String> members) {
    this.members = members;
  }

  public void addMember(String member) {
    members.add(member);
  }
}

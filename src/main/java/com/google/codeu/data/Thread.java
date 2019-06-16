package com.google.codeu.data;

import com.google.codeu.data.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Thread {

  private final UUID id;
  private String name;
  private Post mainPost;
  private List<Post> posts;

  /**
   * Constructs a new {@link Thread} titled {@code name} with main post
   * {@code post}. Generates a random ID.
   */
  public Thread(String name, Post mainPost) {
    this(UUID.randomUUID(), name, mainPost, (List<Post>) new ArrayList<Post>());
  }

  /**
   * Constructs a new {@link Thread} with all its attributes filled. It is
   * used to create a {@link Thread} based on {@link Entity} stored in
   * {@link Datastore}.
   */
  public Thread(UUID id, String name, Post mainPost, List<Post> posts) {
    this.id = id;
    this.name = name;
    this.mainPost = mainPost;
    this.posts = posts;
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

  public Post getMainPost() {
    return mainPost;
  }

  public void setMainPost(Post post) {
    this.mainPost = mainPost;
  }

  public List<Post> getPosts() {
    return posts;
  }

  // Should we have a setPosts method or addPost and deletePost methods?
  public void setPosts(List<Post> posts) {
    this.posts = posts;
  }
}

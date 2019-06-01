package com.google.codeu.data;

import java.util.List;

public class User {

  private String email;
  private String aboutMe;
  private List<String> profilePic;

  /**
   * Creates a new User object.
   */
  public User(String email, String aboutMe, List<String> profilePic) {
    this.email = email;
    this.aboutMe = aboutMe;
    this.profilePic = profilePic;
  }

  public String getEmail() {
    return email;
  }

  public String getAboutMe() {
    return aboutMe;
  }

  public List<String> getProfilePic() {
    return profilePic;
  }
}

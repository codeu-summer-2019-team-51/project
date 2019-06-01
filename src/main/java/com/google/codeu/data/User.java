package com.google.codeu.data;

public class User {

  private String email;
  private String aboutMe;
  private String profilePic;

  /**
   * Creates a new User object.
   */
  public User(String email, String aboutMe, String profilePic) {
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

  public String getProfilePic() {
    return profilePic;
  }
}

package com.google.codeu.data;

public class User {

  private final String email;
  private String aboutMe;
  private String profilePic;

  /**
   * Creates a new User object for a new user.
   */
  public User(String email) {
    this.email = email;
  }

  /**
   * Creates a new User object based on an Entity stored in Datastore.
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

  public void setAboutMe(String aboutMe) {
    this.aboutMe = aboutMe;
  }

  public String getProfilePic() {
    return profilePic;
  }

  public void setProfilePic(String profilePic) {
    this.profilePic = profilePic;
  }
}

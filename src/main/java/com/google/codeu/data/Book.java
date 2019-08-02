package com.google.codeu.data;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

enum Tags {
    ACTION("ACTION"),
    HORROR("HORROR"),
    ROMANCE("ROMANCE"),
    COMEDY("COMEDY"),
    THRILLER("THRILLER"),
    NON_FICTION("NON_FICTION"),
    FICTION("FICTION"),
    BIOGRAPHY("BIOGRAPHY"),
    HISTORICAL("HISTORICAL");
    
    private final String str;
    private static Map<String, Tags> map = new HashMap<String, Tags>();
    
    Tags (String str) {
        this.str = str;
    }
    public String toString() {
        return str;
    }
   
    public static List<Tags> fromString(List<String> str) {
         List<Tags> tags = new ArrayList<Tags>();
         for(int i = 0;i<str.size(); i++){
             tags.add(map.get(str));
         }
        return tags;
    }
    
    static {
      for (Tags tags : Tags.values()) {
        map.put(tags.str, tags);
      }
    }
}

public class Book {

    private final UUID id;
    private String title;
    private List<String> authors;
    private double avgRating;
    private List<Tags> tags;

    /**
     * Creates a new Book object for every new book for cases when the user
     * wants to provide only the title and author of the book.
     */
    public Book(String title, List<String> authors) {
        this(UUID.randomUUID(), title, authors, 0, null);
    }
    
    /**
     * Creates a new Book object for every new book for cases when the user
     * wants to provide the title, author and tags of the book.
     */
    public Book(String title, List<String> authors, List<String> tags) {
        this(UUID.randomUUID(), title, authors, 0, tags);
    }

    /**
     * Constructor method to retrieve existing data from datastore.
     */
    public Book(UUID id, String title, List<String> authors, double avgRating, List<String> tags) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.avgRating = avgRating;
        this.tags = Tags.fromString(tags);
        
        
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getAuthors() {
        return authors;
    }

   
    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }
 
    public List<String> getTags() {
        List<String> bookTags = new ArrayList<String>();
        for (int i = 0; i<tags.size();i++){
            bookTags.add(tags.toString()); 
        }
        return bookTags;
    }

    public void setTags(List<String> tags) {
        this.tags = Tags.fromString(tags);
    }
 

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }
}

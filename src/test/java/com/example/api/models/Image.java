package com.example.api.models;

public class Image {
    private String title;
    private String description;
    private String url;
    
    // Default constructor
    public Image() {}
    
    // Parameterized constructor
    public Image(String title, String description, String url) {
        this.title = title;
        this.description = description;
        this.url = url;
    }
    
    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    @Override
    public String toString() {
        return "Image{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
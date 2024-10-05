package com.example.showwhub;

public class Movie {
    private String movie_name;
    private String imageURL;

    // Default constructor required for Firebase
    public Movie() {
    }

    public Movie(String movie_name, String imageURL) {
        this.movie_name = movie_name;
        this.imageURL = imageURL;
    }

    public String getMovieName() {
        return movie_name;
    }

    public void setMovieName(String movie_name) {
        this.movie_name = movie_name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

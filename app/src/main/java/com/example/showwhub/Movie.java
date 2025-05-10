package com.example.showwhub;

import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private String description;
    private String genre;
    private String releaseYear;
    private Double rating; // ⬅️ changed from String to Double
    private String posterUrl;
    private String castImageUrl;

    public Movie() {} // Needed for Firestore

    public Movie(String title, String description, String genre, String releaseYear, Double rating, String posterUrl, String castImageUrl) {
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.posterUrl = posterUrl;
        this.castImageUrl = castImageUrl;
    }

    // Getters and Setters
    public String getCastImageUrl() {
        return castImageUrl;
    }

    public void setCastImageUrl(String castImageUrl) {
        this.castImageUrl = castImageUrl;
    }

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getGenre() { return genre; }
    public String getReleaseYear() { return releaseYear; }
    public Double getRating() { return rating; }
    public String getPosterUrl() { return posterUrl; }

    // Setters
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setGenre(String genre) { this.genre = genre; }
    public void setReleaseYear(String releaseYear) { this.releaseYear = releaseYear; }
    public void setRating(Double rating) { this.rating = rating; }
    public void setPosterUrl(String posterUrl) { this.posterUrl = posterUrl; }

    // Optional: Overriding toString() for better logging/debugging
    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", genre='" + genre + '\'' +
                ", year='" + releaseYear + '\'' +
                ", rating=" + rating +
                ", posterUrl='" + posterUrl + '\'' +
                ", castImageUrl='" + castImageUrl + '\'' +
                '}';
    }
}

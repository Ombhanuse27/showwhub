package com.example.showwhub;

public class Booking {
    private String bookingDate;
    private String poster;
    private String movieName;
    private String movieTime;
    private int numberOfTickets;
    private int totalAmount;

    // Constructors
    public Booking() {}

    public Booking(String bookingDate, String poster, String movieName, String movieTime, int numberOfTickets, int totalAmount) {
        this.bookingDate = bookingDate;
        this.poster = poster;
        this.movieName = movieName;
        this.movieTime = movieTime;
        this.numberOfTickets = numberOfTickets;
        this.totalAmount = totalAmount;
    }

    // Getters and setters
    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieTime() {
        return movieTime;
    }

    public void setMovieTime(String movieTime) {
        this.movieTime = movieTime;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }
}

package com.example.showwhub;

public class Booking {
    private String booking_date;
    private String movie_name;
    private String movie_time; // Optional, set later
    private int number_of_tickets;
    private int total_amount;

    // Default constructor required for calls to DataSnapshot.getValue(Booking.class)
    public Booking() { }

    public Booking(String booking_date, String movie_name, String movie_time, int number_of_tickets, int total_amount) {
        this.booking_date = booking_date;
        this.movie_name = movie_name;
        this.movie_time = movie_time;
        this.number_of_tickets = number_of_tickets;
        this.total_amount = total_amount;
    }

    // Getters and Setters (if needed)
}

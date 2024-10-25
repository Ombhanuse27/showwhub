package com.example.showwhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class selectdt extends AppCompatActivity implements DateAdapter.OnDateClickListener {

    RecyclerView dateRecyclerView;
    Button time730, time1045;
    List<String> dateList;
    String selectedDate; // Variable to hold the selected date
    String movieName; // Variable to hold the movie name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectdate);

        // Get the movie name from the intent
        movieName = getIntent().getStringExtra("movie_name");

        dateRecyclerView = findViewById(R.id.dateRecyclerView);
        time730 = findViewById(R.id.time_7_30);
        time1045 = findViewById(R.id.time_10_45);

        // Generate dates starting from today and set up RecyclerView
        dateList = generateDates();
        DateAdapter dateAdapter = new DateAdapter(dateList, this); // Pass listener
        dateRecyclerView.setAdapter(dateAdapter);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Handle time selection buttons
        time730.setOnClickListener(view -> {
            if (selectedDate != null) {
                navigateToSeatSelection("07:30 PM");
            } else {
                Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
            }
        });

        time1045.setOnClickListener(view -> {
            if (selectedDate != null) {
                navigateToSeatSelection("10:45 PM");
            } else {
                Toast.makeText(this, "Please select a date first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Method to navigate to Seat Selection page with selected date and time
    private void navigateToSeatSelection(String selectedTime) {
        Intent intent = new Intent(selectdt.this, SeatSelectionFragment.class); // Change to SeatSelectionActivity if needed
        intent.putExtra("selected_date", selectedDate); // This should be the selected date
        intent.putExtra("selected_time", selectedTime);
        intent.putExtra("movie_name", movieName); // Pass the movie name
        startActivity(intent);
    }

    // Generate list of dates from today to the next 5 days
    private List<String> generateDates() {
        List<String> dateList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();  // Current date

        // Add dates to the list for the next 6 days (today + 5)
        for (int i = 0; i < 6; i++) {
            dateList.add(dateFormat.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);  // Move to the next day
        }

        return dateList;
    }

    // Implement the method to handle date click events
    @Override
    public void onDateClick(String date) {
        selectedDate = date; // Store selected date
        Toast.makeText(this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show(); // Debugging line
    }
}

package com.example.showwhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class selectdt extends AppCompatActivity {

    RecyclerView dateRecyclerView;
    Button time730, time1045;
    List<String> dateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectdate);

        dateRecyclerView = findViewById(R.id.dateRecyclerView);
        time730 = findViewById(R.id.time_7_30);
        time1045 = findViewById(R.id.time_10_45);

        // Generate dates starting from today and set up RecyclerView
        dateList = generateDates();
        DateAdapter dateAdapter = new DateAdapter(dateList);
        dateRecyclerView.setAdapter(dateAdapter);
        dateRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Handle time selection buttons
        time730.setOnClickListener(view -> {
            navigateToSeatSelection("07:30 PM");
        });

        time1045.setOnClickListener(view -> {
            navigateToSeatSelection("10:45 PM");
        });
    }

    // Method to navigate to Seat Selection page with selected time
    private void navigateToSeatSelection(String selectedTime) {
        Intent intent = new Intent(selectdt.this, SeatSelectionFragment.class);
        intent.putExtra("selected_time", selectedTime);
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
}

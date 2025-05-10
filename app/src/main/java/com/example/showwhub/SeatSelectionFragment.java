package com.example.showwhub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SeatSelectionFragment extends AppCompatActivity {

    private RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6;
    private ImageView seatView;
    private RadioGroup radioGroupSeats;
    private Button buttonSelectSeats;

    private final int seatPrice = 200; // price per ticket

    // Incoming data
    private String selectedTime;
    private String selectedDate;
    private String movieName;
    private String poster;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seatselection);

        // Get incoming data with null checks
        selectedTime = getIntent().getStringExtra("selected_time");
        selectedDate = getIntent().getStringExtra("selected_date");
        movieName = getIntent().getStringExtra("movie_name");
        poster = getIntent().getStringExtra("movie_poster");

        if (selectedTime == null || selectedDate == null || movieName == null || poster == null) {
            Toast.makeText(this, "Missing booking info!", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // Initialize views
        seatView = findViewById(R.id.seatView);
        radioGroupSeats = findViewById(R.id.radioGroupSeats);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);
        radioButton6 = findViewById(R.id.radioButton6);
        buttonSelectSeats = findViewById(R.id.buttonSelectSeats);

        // Default: 1 seat
        updateSeatImageAndButton(1);

        // Change listener for radio buttons
        radioGroupSeats.setOnCheckedChangeListener((group, checkedId) -> {
            int selectedSeats = getSelectedSeatCount(checkedId);
            updateSeatImageAndButton(selectedSeats);
        });
    }

    private int getSelectedSeatCount(int checkedId) {
        if (checkedId == R.id.radioButton1) return 1;
        else if (checkedId == R.id.radioButton2) return 2;
        else if (checkedId == R.id.radioButton3) return 3;
        else if (checkedId == R.id.radioButton4) return 4;
        else if (checkedId == R.id.radioButton5) return 5;
        else if (checkedId == R.id.radioButton6) return 6;
        else return 1;
    }

    private void updateSeatImageAndButton(int numberOfSeats) {
        // Set image dynamically
        int imageResId = getResources().getIdentifier("seat" + numberOfSeats, "drawable", getPackageName());
        seatView.setImageResource(imageResId);

        // Update button text
        int totalAmount = seatPrice * numberOfSeats;
        buttonSelectSeats.setText("Pay ₹" + totalAmount);
    }

    public void payRS(View view) {
        int selectedId = radioGroupSeats.getCheckedRadioButtonId();
        int numberOfSeats = getSelectedSeatCount(selectedId);
        int totalAmount = seatPrice * numberOfSeats;

        Toast.makeText(this, "Total Amount: ₹" + totalAmount, Toast.LENGTH_SHORT).show();



        // Pass data to DummyUPIPayment
        Intent intent = new Intent(this, DummyUPIPayment.class);
        intent.putExtra("bookingDate", selectedDate);
        intent.putExtra("moviePoster", poster); // key must match DummyUPIPayment.java
        intent.putExtra("movieName", movieName);
        intent.putExtra("movieTime", selectedTime);
        intent.putExtra("movieDate", selectedDate);
        intent.putExtra("numberOfTickets", numberOfSeats);
        intent.putExtra("totalAmount", totalAmount);
        startActivity(intent);
    }

    private String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        return sdf.format(new Date());
    }
}

package com.example.showwhub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class Movie1 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie1);


    }

    public void bookTicket(View view) {
        Intent intent = new Intent(Movie1.this, selectdt.class);
        startActivity(intent);
    }
}


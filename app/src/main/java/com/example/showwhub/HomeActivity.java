package com.example.showwhub;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.showwhub.R.id.*;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements MovieAdapter.OnMovieClickListener { // Implement the listener

    ViewFlipper flipper;
    ImageButton im1, im2, im3, im4;
    private RecyclerView recyclerViewMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.history) {
                    finish();
                    startActivity(new Intent(HomeActivity.this, historyPage.class));
                    return true;
                } else if (itemId == R.id.profile) {
                    finish();
                    startActivity(new Intent(HomeActivity.this, ProfileFragment.class));
                    return true;
                }
                return false;
            }
        });

        // Set default selection
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.home);
        }

        im1 = findViewById(R.id.im1);
        im2 = findViewById(R.id.im2);
        im3 = findViewById(R.id.im3);
        im4 = findViewById(R.id.im4);

        flipper = findViewById(R.id.flipper);

        int imgArray[] = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4};
        for (int img : imgArray) {
            showImg(img);
        }

        // Setup RecyclerView
        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        movieList = new ArrayList<>();
        movieAdapter = new MovieAdapter(this, movieList, this); // Pass the listener
        recyclerViewMovies.setAdapter(movieAdapter);

        fetchMoviesFromFirebase();
    }

    private void fetchMoviesFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("movies");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movieList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String movieName = dataSnapshot.child("movie_name").getValue(String.class);
                    String imageURL = dataSnapshot.child("imageURL").getValue(String.class);

                    Log.d("Firebase", "Movie Name from snapshot: " + movieName);
                    Log.d("Firebase", "Image URL from snapshot: " + imageURL);
                    Movie movie = new Movie(movieName, imageURL);
                    movieList.add(movie);
                }
                movieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
            }
        });
    }

    public void showImg(int img) {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(img);

        flipper.addView(imageView);
        flipper.setFlipInterval(3000);
        flipper.setAutoStart(true);

        flipper.setInAnimation(this, android.R.anim.slide_in_left);
        flipper.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(HomeActivity.this, Movie3.class); // Replace with your detail activity
        intent.putExtra("MOVIE_NAME", movie.getMovieName());
        intent.putExtra("IMAGE_URL", movie.getImageURL());
        startActivity(intent);
    }

    public void movie1(View view) {
        Intent intent = new Intent(HomeActivity.this, Movie1.class);
        startActivity(intent);
    }

    public void movie2(View view) {
        Intent intent = new Intent(HomeActivity.this, Movie2.class);
        startActivity(intent);
    }

    public void movie3(View view) {
        Intent intent = new Intent(HomeActivity.this, Movie3.class);
        startActivity(intent);
    }

    public void movie4(View view) {
        Intent intent = new Intent(HomeActivity.this, Movie4.class);
        startActivity(intent);
    }

    public void clk1(View view) {
        Animation am = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hyper);
        im1.startAnimation(am);
    }

    public void clk2(View view) {
        Animation am = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hyper);
        im2.startAnimation(am);
    }

    public void clk3(View view) {
        Animation am = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hyper);
        im3.startAnimation(am);
    }

    public void clk4(View view) {
        Animation am = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hyper);
        im4.startAnimation(am);
    }

    public void seeall(View view) {
        Intent intent = new Intent(HomeActivity.this, SeeAll.class);
        startActivity(intent);
    }
}

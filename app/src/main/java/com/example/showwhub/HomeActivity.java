// HomeActivity.java
package com.example.showwhub;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ViewFlipper flipper;
    ImageButton im1, im2, im3, im4;
    private RecyclerView recyclerViewMovies;
    private MovieAdapter movieAdapter;
    private List<Movie> movieList;
    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = FirebaseFirestore.getInstance();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
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
        });

        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.home);
        }

        im1 = findViewById(R.id.im1);
        im2 = findViewById(R.id.im2);
        im3 = findViewById(R.id.im3);
        im4 = findViewById(R.id.im4);

        flipper = findViewById(R.id.flipper);
        int imgArray[] = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4};
        for (int img : imgArray) showImg(img);

        recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        recyclerViewMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        movieList = new ArrayList<>();

        // Initialize the adapter and set the movie click listener
        movieAdapter = new MovieAdapter(this, movieList, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = new Intent(HomeActivity.this, MovieDetails.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });

        recyclerViewMovies.setAdapter(movieAdapter);

        fetchMoviesFromFirestore();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void fetchMoviesFromFirestore() {
        CollectionReference moviesRef = db.collection("addedMovies");

        moviesRef.orderBy("title", Query.Direction.ASCENDING).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                movieList.clear();
                QuerySnapshot querySnapshot = task.getResult();
                for (DocumentSnapshot document : querySnapshot) {
                    Movie movie = document.toObject(Movie.class);
                    if (movie != null) {
                        movieList.add(movie);
                    }
                }
                movieAdapter.notifyDataSetChanged();
            } else {
                Log.e("HomeActivity", "Error fetching movies: " + task.getException());
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

    public void movie2(View view) {
        startActivity(new Intent(HomeActivity.this, Movie2.class));
    }

    public void movie3(View view) {
        startActivity(new Intent(HomeActivity.this, Movie3.class));
    }

    public void movie4(View view) {
        startActivity(new Intent(HomeActivity.this, Movie4.class));
    }

    public void clk1(View view) {
        im1.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hyper));
    }

    public void clk2(View view) {
        im2.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hyper));
    }

    public void clk3(View view) {
        im3.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hyper));
    }

    public void clk4(View view) {
        im4.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.hyper));
    }

    public void seeall(View view) {
        startActivity(new Intent(HomeActivity.this, SeeAll.class));
    }
}

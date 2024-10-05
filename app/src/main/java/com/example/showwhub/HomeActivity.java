package com.example.showwhub;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private final Context context;
    private final List<Movie> movieList;

    public MovieAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.tvMovieName.setText(movie.getMovieName());
        Picasso.get().load(movie.getImageURL()).into(holder.ivMoviePoster);

        // Set an OnClickListener for the item
        holder.itemView.setOnClickListener(v -> {
            Intent intent;
            // Navigate to different activities based on movie name
            switch (movie.getMovieName()) {
                case "Movie 1":
                    intent = new Intent(context, Movie1.class);
                    break;
                case "Movie 2":
                    intent = new Intent(context, Movie2.class);
                    break;
                case "Movie 3":
                    intent = new Intent(context, Movie3.class);
                    break;
                // Add more cases as needed for additional movies
                default:
                    intent = new Intent(context, DefaultMovieActivity.class); // Default activity
                    break;
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivMoviePoster;
        TextView tvMovieName;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMoviePoster = itemView.findViewById(R.id.imageViewMovie);
            tvMovieName = itemView.findViewById(R.id.textViewMovieName);
        }
    }
}

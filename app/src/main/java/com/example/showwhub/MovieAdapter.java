package com.example.showwhub;

import android.content.Context;
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
    private final OnMovieClickListener onMovieClickListener;

    public MovieAdapter(Context context, List<Movie> movieList, OnMovieClickListener onMovieClickListener) {
        this.context = context;
        this.movieList = movieList;
        this.onMovieClickListener = onMovieClickListener;
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
        holder.movieName.setText(movie.getMovieName());
        Picasso.get().load(movie.getImageURL()).into(holder.movieImage);

        // Set click listener for each movie item
        holder.itemView.setOnClickListener(v -> onMovieClickListener.onMovieClick(movie));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        TextView movieName;
        ImageView movieImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieName = itemView.findViewById(R.id.textViewMovieName);
            movieImage = itemView.findViewById(R.id.imageViewMovie);
        }
    }

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }
}

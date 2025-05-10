package com.example.showwhub;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    private List<String> dateList;
    private OnDateClickListener onDateClickListener;
    private int selectedPosition = -1; // Track the selected position

    // Interface for date click events
    public interface OnDateClickListener {
        void onDateClick(String date);
    }

    // Constructor
    public DateAdapter(List<String> dateList, OnDateClickListener onDateClickListener) {
        this.dateList = dateList;
        this.onDateClickListener = onDateClickListener;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        String date = dateList.get(position);
        holder.bind(date);

        // Update the selected position
        // Highlight selected date by changing background color
        holder.itemView.setBackgroundColor(position == selectedPosition ? Color.parseColor("#D3D3D3") : Color.WHITE); // Use a light gray color

        // Set click listener on each date item
        holder.itemView.setOnClickListener(v -> {
            int previousPosition = selectedPosition; // Store previous position
            selectedPosition = holder.getAdapterPosition(); // Update the selected position
            notifyItemChanged(previousPosition); // Notify to refresh previous item
            notifyItemChanged(selectedPosition); // Notify to refresh current item
            onDateClickListener.onDateClick(date); // Notify the listener about the selected date
        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    // ViewHolder class for managing the individual date items
    class DateViewHolder extends RecyclerView.ViewHolder {
        TextView dateTextView;

        public DateViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.dateTextView); // Assuming your date item layout has a TextView with this ID
        }

        // Bind data to the ViewHolder
        public void bind(String date) {
            dateTextView.setText(date); // Set the date text to the TextView
        }
    }
}

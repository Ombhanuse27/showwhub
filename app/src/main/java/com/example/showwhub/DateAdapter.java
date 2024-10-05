package com.example.showwhub;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.DateViewHolder> {

    private List<String> dateList;
    private int selectedPosition = -1;  // To track the selected date

    public DateAdapter(List<String> dateList) {
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        holder.dateText.setText(dateList.get(position));

        // Highlight selected date
        if (selectedPosition == position) {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.darker_gray));
        } else {
            holder.itemView.setBackgroundColor(holder.itemView.getResources().getColor(android.R.color.white));
        }

        holder.itemView.setOnClickListener(v -> {
            selectedPosition = position;  // Update selected position
            notifyDataSetChanged();  // Refresh to reflect the selection change
        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    public class DateViewHolder extends RecyclerView.ViewHolder {
        TextView dateText;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.dateText);
        }
    }
}

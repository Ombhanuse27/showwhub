package com.example.showwhub;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private final ArrayList<Booking> bookingList;
    private final Context context;

    public BookAdapter(ArrayList<Booking> bookingList, Context context) {
        this.bookingList = bookingList;
        this.context = context;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orederrecyclerview, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Booking booking = bookingList.get(position);
        holder.bind(booking, position);
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder {
        private final TextView bookMovieName;
        private final TextView bookTotalAmount;
        private final TextView bookDate;
        private final TextView bookTime;
        private final TextView bookNumberOfTickets;
        private final ImageButton cancelButton;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookMovieName = itemView.findViewById(R.id.book_movie_name);
            bookTotalAmount = itemView.findViewById(R.id.book_total_amount);
            bookDate = itemView.findViewById(R.id.book_date);
            bookTime = itemView.findViewById(R.id.book_time);
            bookNumberOfTickets = itemView.findViewById(R.id.book_number_of_tickets);
            cancelButton = itemView.findViewById(R.id.cancelButton);
        }

        public void bind(Booking booking, int position) {
            bookMovieName.setText(booking.getMovieName());
            bookTotalAmount.setText(String.format("$%d", booking.getTotalAmount()));
            bookDate.setText(booking.getBookingDate());
            bookTime.setText(booking.getMovieTime());
            bookNumberOfTickets.setText(String.format("Tickets: %d", booking.getNumberOfTickets()));

            cancelButton.setOnClickListener(view -> {
                // Create an AlertDialog
                new AlertDialog.Builder(context)
                        .setTitle("Cancel Booking")
                        .setMessage("Do you want to cancel this booking?")
                        .setPositiveButton("Yes", (dialog, which) -> {
                            // If confirmed, delete from Firebase and remove the item from RecyclerView
                            deleteBookingFromFirebase(booking, position);
                        })
                        .setNegativeButton("No", null)
                        .show();
            });
        }

        private void deleteBookingFromFirebase(Booking booking, int position) {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference bookingRef = FirebaseDatabase.getInstance().getReference("bookings").child(userId);

            // Assuming you have a unique key for each booking in Firebase
            bookingRef.orderByChild("movieName").equalTo(booking.getMovieName())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                snapshot.getRef().removeValue(); // Delete from Firebase
                            }

                            // Remove the item from the local list and notify the adapter
                            bookingList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, bookingList.size());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle potential errors
                        }
                    });
        }
    }
}

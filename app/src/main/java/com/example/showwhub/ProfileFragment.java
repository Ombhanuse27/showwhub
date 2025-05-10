// ProfileFragment.java
package com.example.showwhub;

import static java.security.AccessController.getContext;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import com.google.firebase.firestore.FieldValue;

import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.Toast;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileFragment extends AppCompatActivity {

    private RecyclerView bookRecyclerView;

    private ArrayList<Booking> bookingList;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Button logout;

    ImageView profileImageView;
    EditText updateName, updateEmail;
    Button updateProfileButton;
    Uri imageUri;
    FirebaseFirestore db;
    final int IMAGE_REQUEST = 71;

    String CLOUD_NAME = "da9xvfoye";
    String UPLOAD_PRESET = "ml_default";

    EditText editTextFeedback, editTextEmail, editTextMovie;
    Button submitFeedbackButton;
    List<String> movieTitles = new ArrayList<>();
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




        editTextFeedback = findViewById(R.id.editTextFeedback);
        editTextEmail = findViewById(R.id.enterEmail);
        editTextMovie = findViewById(R.id.enterMovie);
        submitFeedbackButton = findViewById(R.id.submitFeedbackButton);

        profileImageView = findViewById(R.id.profileImage);
        updateName = findViewById(R.id.updateName);
        updateEmail = findViewById(R.id.updateEmail);
        updateProfileButton = findViewById(R.id.updateProfileButton);
        db = FirebaseFirestore.getInstance();




// Handle feedback submission
        submitFeedbackButton.setOnClickListener(v -> {

            String feedbackText = editTextFeedback.getText().toString().trim();
            String email = editTextEmail.getText().toString().trim();


            if (feedbackText.isEmpty() || email.isEmpty()) {
                Toast.makeText(ProfileFragment.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> feedback = new HashMap<>();
            feedback.put("email", email);
            feedback.put("movie", editTextMovie.getText().toString());
            feedback.put("feedback", feedbackText);
            feedback.put("timestamp", FieldValue.serverTimestamp());

            FirebaseFirestore.getInstance().collection("moviefeedbacks")
                    .add(feedback)
                    .addOnSuccessListener(docRef -> {
                        Toast.makeText(ProfileFragment.this, "Feedback submitted!", Toast.LENGTH_SHORT).show();
                        editTextFeedback.setText("");
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(ProfileFragment.this, "Failed to submit feedback", Toast.LENGTH_SHORT).show();
                    });
        });

        logout = findViewById(R.id.logout);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.history) {
                    finish();
                    startActivity(new Intent(ProfileFragment.this, historyPage.class));
                    return true;
                } else if (itemId == R.id.home) {
                    finish();
                    startActivity(new Intent(ProfileFragment.this,HomeActivity.class));
                    return true;
                }
                return false;
            }
        });

        // Set default selection
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.profile);
        }

        logout.setOnClickListener(v -> {
            // Sign out the user from Firebase
            FirebaseAuth.getInstance().signOut();

            // Redirect to the SignInActivity
            Intent intent = new Intent(ProfileFragment.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish(); // Close the ProfileFragment activity
        });



    }

    // Method to select image for profile
    public void selectProfileImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    // Handle the result of the image selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                profileImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                Toast.makeText(this, "Error loading image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void updateProfile(View view) {
        if (imageUri == null) {
            Toast.makeText(this, "Please select a profile image", Toast.LENGTH_SHORT).show();
            return;
        }

        File imageFile = FileUtils.getFileFromUri(this, imageUri);
        if (imageFile == null) {
            Toast.makeText(this, "File error", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        // Upload profile image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", imageFile.getName(), RequestBody.create(MediaType.parse("image/*"), imageFile))
                .addFormDataPart("upload_preset", UPLOAD_PRESET)
                .build();

        Request request = new Request.Builder()
                .url("https://api.cloudinary.com/v1_1/" + CLOUD_NAME + "/image/upload")
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(ProfileFragment.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        JSONObject json = new JSONObject(response.body().string());
                        String imageUrl = json.getString("secure_url");

                        // Update Firestore with the profile data
                        updateProfileDetails(imageUrl);
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(ProfileFragment.this, "Error parsing response", Toast.LENGTH_SHORT).show());
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(ProfileFragment.this, "Upload failed", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }
    private void updateProfileDetails(String imageUrl) {
        Map<String, Object> userProfileData = new HashMap<>();
        userProfileData.put("name", updateName.getText().toString());
        userProfileData.put("email", updateEmail.getText().toString());
        userProfileData.put("profileImageUrl", imageUrl);

        db.collection("movieusers").document(FirebaseAuth.getInstance().getCurrentUser().getUid()) // Use actual user ID
                .set(userProfileData)
                .addOnSuccessListener(aVoid -> runOnUiThread(() -> {
                    Toast.makeText(ProfileFragment.this, "Profile updated", Toast.LENGTH_SHORT).show();
                }))
                .addOnFailureListener(e -> runOnUiThread(() -> {
                    Toast.makeText(ProfileFragment.this, "Update failed", Toast.LENGTH_SHORT).show();
                }));
    }
}

package com.example.showwhub;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPass;
    EditText confirmPass;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // Initialize Firebase
        //FirebaseApp.initializeApp(this);

        // Bind UI elements
        inputEmail = findViewById(R.id.inputEmail);
        inputPass = findViewById(R.id.inputPass);
        confirmPass = findViewById(R.id.confirmPass);
        progressDialog = new ProgressDialog(this);

        // Initialize FirebaseAuth
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
    }

    // Method to handle register button click
    public void register(View view) {
        performAuth();
    }

    // Method to perform user authentication
    private void performAuth() {
        String email = inputEmail.getText().toString().trim();
        String pass = inputPass.getText().toString().trim();
        String confirm = confirmPass.getText().toString().trim();

        if (!email.matches(emailPattern)) {
            inputEmail.setError("Enter a correct email");
        } else if (pass.isEmpty() || pass.length() < 6) {
            inputPass.setError("Enter a valid password (minimum 6 characters)");
        } else if (!pass.equals(confirm)) {
            confirmPass.setError("Passwords do not match");
        } else {
            progressDialog.setMessage("Please Wait While Registering...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        sendUserToNextActivity();
                        Toast.makeText(MainActivity2.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity2.this, "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    // Method to navigate to the next activity
    private void sendUserToNextActivity() {
        Intent intent = new Intent(MainActivity2.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    // Method to handle already have an account button click
    public void already(View view) {
        Intent intent = new Intent(MainActivity2.this, MainActivity.class);
        startActivity(intent);
    }
}

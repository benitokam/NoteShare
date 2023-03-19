package com.example.noteshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText email;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        email = findViewById(R.id.email);
      password = findViewById(R.id.password);
        Button connecter = findViewById(R.id.connecter);
        Button compte = findViewById(R.id.compte);
        Intent i = new Intent(this, MainActivity.class);
        Intent   ZoneCompte = new Intent(this,Register.class);
        compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ZoneCompte);
            }
        });
        mAuth = FirebaseAuth.getInstance();
        // adding on click listener for our login button.
        connecter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hiding our progress bar.

                // getting data from our edit text on below line.
                String emails = email.getText().toString();
                String passwords = password.getText().toString();
                // on below line validating the text input.
                if (TextUtils.isEmpty(emails) && TextUtils.isEmpty(passwords)) {
                    Toast.makeText(getApplication(), "Mots de passe ou email incorrect..", Toast.LENGTH_SHORT).show();
                    return;
                }
                // on below line we are calling a sign in method and passing email and password to it.
                mAuth.signInWithEmailAndPassword(emails, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        // on below line we are checking if the task is success or not.
                        if (task.isSuccessful()) {
                            // on below line we are hiding our progress bar.

                            Toast.makeText(getApplication(), "Login Successful..", Toast.LENGTH_SHORT).show();
                            // on below line we are opening our mainactivity.
                            SharedPreferences prf = getSharedPreferences("Login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edipref = prf.edit();
                             edipref.putString("email",emails);
                             edipref.putBoolean("isConnected",true);
                             edipref.apply();
                            startActivity(i);
                            finish();
                        } else {
                            // hiding our progress bar and displaying a toast message.

                            Toast.makeText(getApplication(), "Please enter valid user credentials..", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
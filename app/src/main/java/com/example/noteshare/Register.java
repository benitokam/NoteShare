package com.example.noteshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class Register extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);
        EditText confpassword = findViewById(R.id.confirmpassword);
        Button compte = findViewById(R.id.NewCompte);


        // adding click listener for register button.
        compte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hiding our progress bar.


                // getting data from our edit text.
                String userName = email.getText().toString();
                String pwd = password.getText().toString();
                String cnfPwd = confpassword.getText().toString();
                mAuth = FirebaseAuth.getInstance();
                // checking if the password and confirm password is equal or not.
                if (!pwd.equals(cnfPwd)) {
                    Toast.makeText(getApplication(), "Please check both having same password..", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(pwd) && TextUtils.isEmpty(cnfPwd)) {

                    // checking if the text fields are empty or not.
                    Toast.makeText(getApplication(), "Please enter your credentials..", Toast.LENGTH_SHORT).show();
                } else {

                    // on below line we are creating a new user by passing email and password.
                    mAuth.createUserWithEmailAndPassword(userName, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete( Task<AuthResult> task) {
                            // on below line we are checking if the task is success or not.
                            if (task.isSuccessful()) {

                                // in on success method we are hiding our progress bar and opening a login activity.

                                Toast.makeText(getApplication(), "Enregistrement..", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplication(), Login.class);
                                startActivity(i);
                                finish();
                            } else {

                                // in else condition we are displaying a failure toast message.
                                Log.d("error",task.getException().getMessage());
                                Toast.makeText(getApplication(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
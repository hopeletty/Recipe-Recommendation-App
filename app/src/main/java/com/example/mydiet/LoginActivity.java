package com.example.mydiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    //create object of DatabaseReference class to access firebase Realtime Database
    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance
            ("https://mydiet-c351f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();

    EditText contact, password;
    TextView createAccount;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    // username = findViewById(R.id.username);
        password =  findViewById(R.id.password);
        createAccount = findViewById(R.id.createAccount);
        loginBtn =  findViewById(R.id.loginBtn);
        contact = findViewById(R.id.contact);


        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String phone = contact.getText().toString();
                String pass = password.getText().toString();

                if (phone.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter all the fields",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check contact is not registered before
                            if (snapshot.hasChild(phone)) {
                                //username exists in firebase
                                //get password of user from firebase and match it with user password entered
                                String getPassword = snapshot.child(phone).child("password").getValue(String.class);

                                if (getPassword.equals(pass)) {
                                    Toast.makeText(LoginActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                                    //open MainActivity on success
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                  //  finish();
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                }

                            }
                            else {
                                Toast.makeText(LoginActivity.this, "User not registered", Toast.LENGTH_SHORT).show();
                            }

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }

                    });
                }
            }

        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }


}
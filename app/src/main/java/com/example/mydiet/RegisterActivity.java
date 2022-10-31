package com.example.mydiet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {

    //create object of DatabaseReference class to access firebase Realtime Database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance
            ("https://mydiet-c351f-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
    //DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://mydiet-c351f-default-rtdb.asia-southeast1.firebasedatabase.app");
    EditText username, password, contact, confirmPassword;
    TextView loginTxt;
    Button signBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        contact = findViewById(R.id.contact);
        signBtn = findViewById(R.id.signBtn);
        loginTxt = findViewById(R.id.loginTxt);


        signBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get data from editText into String variables
                String user = username.getText().toString();
                String phone = contact.getText().toString();
                String pass = password.getText().toString();
                String repass = password.getText().toString();

                if (user.equals("") || phone.equals("") || pass.equals("") || repass.equals(""))
                    Toast.makeText(RegisterActivity.this, "Please enter all the fields",
                            Toast.LENGTH_SHORT).show();

                else if (!pass.equals(repass)) {
                        Toast.makeText(RegisterActivity.this, "Passwords not matching",
                                Toast.LENGTH_SHORT).show();

                }
                else {
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String a = snapshot.toString();
                            Log.e("Fire DB", a );
                            //check email is not registered before
                            if(snapshot.hasChild(phone)){
                                Toast.makeText(RegisterActivity.this, "Phone number already registered",
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //sending data to firebase realtime Database
                                //using email as the unique identity to every user
                                //all other details of user come under email
                                databaseReference.child("users").child(phone).child("username").setValue(user);
                                databaseReference.child("users").child(phone).child("password").setValue(pass);

                                //show a success message then finish activity
                                Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                              //  finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        loginTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
               // finish();
            }
        });
    }
}
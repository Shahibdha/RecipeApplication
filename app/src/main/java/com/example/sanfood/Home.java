package com.example.sanfood;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {

    private RelativeLayout searchBar;
    private FirebaseAuth firebaseAuth;
    private ImageView logoutbtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        firebaseAuth = FirebaseAuth.getInstance();

        searchBar=findViewById(R.id.searchBar);
        logoutbtn=findViewById(R.id.logout);
        //firebaseAuth.signOut();
        searchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Home.this, Search.class));
            }
            //Put shadhibda's search bar
        });


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();

            Intent intent = new Intent(Home.this, Intro.class);
            startActivity(intent);

            }
        });
    }

}
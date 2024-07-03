package com.example.sanfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {

    private TextView login;
    private EditText email,password,copassword ;
    private ImageView btnSignup;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Signup.this, Login.class));
            }
        });

      //  getSupportActionBar().hide();

        email=findViewById(R.id.eemail);
        password=findViewById(R.id.pass);
        copassword=findViewById(R.id.coPass);

        firebaseAuth=FirebaseAuth.getInstance();


        btnSignup = findViewById(R.id.btnSignup);

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString().trim();
                String passw= password.getText().toString().trim();
                String coPassw= copassword.getText().toString().trim();

                if(mail.isEmpty()|| passw.isEmpty()|| coPassw.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All Fields are Required",Toast.LENGTH_SHORT).show();
                }
                else if(passw.length()<8)
                {
                    Toast.makeText(getApplicationContext(),"Password should have more than 8 digits",Toast.LENGTH_SHORT).show();
                }
                else if(!passw.equals(coPassw))
                {
                    Toast.makeText(getApplicationContext(),"Password and Confirm password is not matching",Toast.LENGTH_SHORT).show();
                }
                else {
                    //Register the user to firebase
                    firebaseAuth.createUserWithEmailAndPassword(mail,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Registration Successful",Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(Signup.this, Login.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Failed to Register",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
            }
        });


    }

}
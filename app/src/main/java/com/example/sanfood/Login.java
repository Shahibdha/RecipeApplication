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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private TextView register;
    private EditText username ,password;
    private ImageView btnSignin;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(Login.this, Signup.class));
            }
        });

        username= findViewById(R.id.username);
        password=findViewById(R.id.pass);
        btnSignin=findViewById(R.id.btnSignin);

        firebaseAuth=FirebaseAuth.getInstance();

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=username.getText().toString().trim();
                String passw= password.getText().toString().trim();

                if(name.isEmpty()|| passw.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All Fields are Required",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Login the user using firebase

                    firebaseAuth.signInWithEmailAndPassword(name,passw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful())
                            {

                                Toast.makeText(getApplicationContext(),"Loging successful",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, Home.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Account Doesn't Exist",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }

            }
        });
    }



    public void tappedReg(View view) {
        startActivity(new Intent(Login.this, Signup.class));
    }
}
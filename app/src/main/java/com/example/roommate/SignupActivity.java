package com.example.roommate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignupActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore database;

    EditText emailBox,passwordBox,nameBox;
    Button loginBtn,signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();


        emailBox=findViewById(R.id.emailBox);
        nameBox=findViewById(R.id.nameBox);
        passwordBox=findViewById(R.id.passwordBox);
        loginBtn=findViewById(R.id.loginbtn);
        signupBtn=findViewById(R.id.createBtn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email,pass,name;
                email=emailBox.getText().toString();
                pass=passwordBox.getText().toString();
                name=nameBox.getText().toString();

                User user=new User(name,email,pass);

                auth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            database.collection("Users").document()
                                    .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                                    finish();
                                }
                            });

//                            Toast.makeText(SignupActivity.this,"Account Is Created",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignupActivity.this,task.getException().getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this,LoginActivity.class));
                finish();
            }
        });

    }
}
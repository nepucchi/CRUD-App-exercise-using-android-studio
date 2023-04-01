package com.example.simplecrudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText usernameEdit,passEdit;
    private Button submitButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private TextView RegisterTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEdit = findViewById(R.id.idEdtUsername);
        passEdit = findViewById(R.id.idEdtPassword);
        submitButton = findViewById(R.id.idBtnLogin);
        progressBar = findViewById(R.id.idPBLoading);
        RegisterTV = findViewById(R.id.idTVRegister);
        mAuth = FirebaseAuth.getInstance();

        RegisterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String username = usernameEdit.getText().toString();
                String password = passEdit.getText().toString();
                if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
                    Toast.makeText(LoginActivity.this, "Please fill in the form!", Toast.LENGTH_SHORT).show();
                }else {
                    mAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                //biar kalo udah login gabisa balik ke mnu login lagi pake finish()
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Failed to Login :c", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
            this.finish();
        }
    }
}
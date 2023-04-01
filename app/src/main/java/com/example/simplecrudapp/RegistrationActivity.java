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

public class RegistrationActivity extends AppCompatActivity {

    private TextInputEditText usernameEdit,passEdit,confEdit;
    private Button submitButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private TextView LoginTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        usernameEdit = findViewById(R.id.idEdtUsername);
        passEdit = findViewById(R.id.idEdtPassword);
        confEdit = findViewById(R.id.idCnfPassword);
        submitButton = findViewById(R.id.idBtnRegister);
        progressBar = findViewById(R.id.idPBLoading);
        LoginTV = findViewById(R.id.idTVLogin);
        mAuth = FirebaseAuth.getInstance();

        LoginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegistrationActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String username = usernameEdit.getText().toString();
                String password = passEdit.getText().toString();
                String confpass = confEdit.getText().toString();
                if(!password.equals(confpass)){
                    Toast.makeText(RegistrationActivity.this, "Password does not match!", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confpass)) {
                    Toast.makeText(RegistrationActivity.this, "Please fill in the form!", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, "User has been registered!", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(RegistrationActivity.this, LoginActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(RegistrationActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
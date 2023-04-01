package com.example.simplecrudapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCourseActivity extends AppCompatActivity {

    private TextInputEditText editname, editprice, editfor, editimg, editlink, editdesc;
    private String courseID;
    private Button submitbutton;
    private ProgressBar progressBar;
    private FirebaseDatabase fDB;
    private DatabaseReference fRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        editname = findViewById(R.id.idEdtCourseName);
        editprice = findViewById(R.id.idEdtCoursePrice);
        editfor = findViewById(R.id.idEdtCourseSuitedFor);
        editimg = findViewById(R.id.idEdtCourseImg);
        editlink = findViewById(R.id.idEdtCourseLink);
        editdesc = findViewById(R.id.idEdtCourseDesc);
        submitbutton = findViewById(R.id.idBtnSubmitCourse);
        progressBar = findViewById(R.id.idCourseBar);
        fDB = FirebaseDatabase.getInstance();
        fRef = fDB.getReference("Courses");

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String name = editname.getText().toString();
                String price = editprice.getText().toString();
                String forwho = editfor.getText().toString();
                String img = editimg.getText().toString();
                String link = editlink.getText().toString();
                String desc = editdesc.getText().toString();
                courseID = name;

                CourseRVModel model = new CourseRVModel(name,price,desc,forwho,img,link,courseID);
                fRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        //bikin entry ke database
                        fRef.child(courseID).setValue(model);
                        Toast.makeText(AddCourseActivity.this, "Course Added Successfully!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(AddCourseActivity.this, MainActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(AddCourseActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
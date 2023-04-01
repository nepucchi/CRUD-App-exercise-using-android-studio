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

import java.util.HashMap;
import java.util.Map;

public class EditCourseActivity extends AppCompatActivity {
    private TextInputEditText editname, editprice, editfor, editimg, editlink, editdesc;
    private String courseID;
    private Button editbutton;
    private Button deletebutton;
    private ProgressBar progressBar;
    private FirebaseDatabase fDB;
    private DatabaseReference fRef;
    private CourseRVModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);
        editname = findViewById(R.id.idEdtCourseName);
        editprice = findViewById(R.id.idEdtCoursePrice);
        editfor = findViewById(R.id.idEdtCourseSuitedFor);
        editimg = findViewById(R.id.idEdtCourseImg);
        editlink = findViewById(R.id.idEdtCourseLink);
        editdesc = findViewById(R.id.idEdtCourseDesc);
        editbutton = findViewById(R.id.idBtnEdtCourse);
        deletebutton = findViewById(R.id.idBtnDelCourse);
        progressBar = findViewById(R.id.idCourseBar);
        fDB = FirebaseDatabase.getInstance();

        //dapetin data dari intent di main
        model = getIntent().getParcelableExtra("course");
        if(model!=null){
            editname.setText(model.getCoursename());
            editprice.setText(model.getCourseprice());
            editfor.setText(model.getCoursefor());
            editimg.setText(model.getCourseimg());
            editlink.setText(model.getCourselink());
            editdesc.setText(model.getCoursedesc());
            courseID = model.getCourseid();
        }
        fRef = fDB.getReference("Courses").child(courseID);
        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String name = editname.getText().toString();
                String price = editprice.getText().toString();
                String forwho = editfor.getText().toString();
                String img = editimg.getText().toString();
                String link = editlink.getText().toString();
                String desc = editdesc.getText().toString();

                Map<String, Object> map = new HashMap<>();
                map.put("coursename", name);
                map.put("courseprice", price);
                map.put("coursedesc", desc);
                map.put("coursefor", forwho);
                map.put("courseimg", img);
                map.put("courselink", link);
                map.put("courseid", courseID);

                fRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        progressBar.setVisibility(View.GONE);
                        fRef.updateChildren(map);
                        Toast.makeText(EditCourseActivity.this, "Course Updated!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditCourseActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(EditCourseActivity.this, "Failed to Update!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCourse();
            }
        });
    }

    private void deleteCourse(){
        fRef.removeValue();
        Toast.makeText(this, "Data has been deleted!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditCourseActivity.this, MainActivity.class));
    }
}
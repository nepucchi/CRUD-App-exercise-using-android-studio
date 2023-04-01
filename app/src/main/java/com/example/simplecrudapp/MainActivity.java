package com.example.simplecrudapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements CouseRVAdapter.CourseClickInterface {
    private RecyclerView courseRV;
    private ProgressBar courseBar;
    private FloatingActionButton floatingActionButton;
    private FirebaseDatabase fDB;
    private DatabaseReference fRef;
    private ArrayList<CourseRVModel> modelList;
    private RelativeLayout relativeLayout;
    private CouseRVAdapter modelAdapter;
    private FirebaseAuth fAuth;
    private TextView no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        no = findViewById(R.id.isEmpty);
        courseRV = findViewById(R.id.idRVCourses);
        courseBar = findViewById(R.id.idRVBar);
        floatingActionButton = findViewById(R.id.idRVfab);
        fDB = FirebaseDatabase.getInstance();
        fRef = fDB.getReference("Courses");
        fAuth = FirebaseAuth.getInstance();
        modelList = new ArrayList<>();
        modelAdapter = new CouseRVAdapter(modelList,this,this);
        courseRV.setAdapter(modelAdapter);
        courseRV.setLayoutManager(new LinearLayoutManager(this));
        relativeLayout = findViewById(R.id.idRLhome);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AddCourseActivity.class));
            }
        });
        getAllCourses();

    }

    private void getAllCourses(){
        modelList.clear();
        fRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                courseBar.setVisibility(View.GONE);
                no.setVisibility(View.GONE);
                modelList.add(snapshot.getValue(CourseRVModel.class));
                modelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                courseBar.setVisibility(View.GONE);
                no.setVisibility(View.GONE);
                modelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                courseBar.setVisibility(View.GONE);
                no.setVisibility(View.GONE);
                modelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                courseBar.setVisibility(View.GONE);
                no.setVisibility(View.GONE);
                modelAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onCourseClick(int position) {
        displayBottomSheet(modelList.get(position));
    }

    private void displayBottomSheet(CourseRVModel model){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_dialogue,relativeLayout,false);
        bottomSheetDialog.setContentView(layout);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
        bottomSheetDialog.show();

        TextView coursenameTV = layout.findViewById(R.id.idTVCourseName);
        TextView courseprice = layout.findViewById(R.id.idTVCoursePrice);
        TextView coursefor = layout.findViewById(R.id.idTVCourseFor);
        TextView coursedesc = layout.findViewById(R.id.idTVCourseDesc);
        ImageView courseimg = layout.findViewById(R.id.idTVCourseImg);

        Button editbtn = layout.findViewById(R.id.idbtmEdit);
        Button Viewbtn = layout.findViewById(R.id.idbtmView);

        coursenameTV.setText(model.getCoursename());
        courseprice.setText(model.getCourseprice());
        coursefor.setText(model.getCoursefor());
        coursedesc.setText(model.getCoursedesc());
        Picasso.get().load(model.getCourseimg()).into(courseimg);

        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(android.view.View view) {
                Intent i = new Intent(MainActivity.this,EditCourseActivity.class);
                i.putExtra("course", model);
                startActivity(i);
            }
        });

        Viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(model.getCourselink()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.logout:
                Toast.makeText(this, "user logged out!", Toast.LENGTH_SHORT).show();
                fAuth.signOut();
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
                this.finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
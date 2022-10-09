package com.example.firebasevideostreaming;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasevideostreaming.Adapter.myViewAdapter;
import com.example.firebasevideostreaming.databinding.ActivityDashboardBinding;
import com.example.firebasevideostreaming.model.videoFileModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {

    private ActivityDashboardBinding dashboardBinding;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private MediaController mediaController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardBinding= ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(dashboardBinding.getRoot());
        dashboardBinding.recyclerLstView.setLayoutManager(new LinearLayoutManager(this));

        dashboardBinding.recyclerLstView.setHasFixedSize(true);
        dashboardBinding.recyclerLstView.setLayoutManager(new LinearLayoutManager(this));
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("myvideos");
        dashboardBinding.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Dashboard.this, addVideo.class);
                startActivity(intent);
                finish();
            }
        });

        dashboardBinding.btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                // successfully signed out
                Intent intent= new Intent(Dashboard.this,LoginActivity.class);
                startActivity(intent);
                Toast.makeText(Dashboard.this,   " Successfully Log Out  ", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        FirebaseRecyclerOptions<videoFileModel> options=
                new FirebaseRecyclerOptions.Builder<videoFileModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("myvideos"), videoFileModel.class)
                        .build();
        FirebaseRecyclerAdapter<videoFileModel,>;
    }


}

package com.example.firebasevideostreaming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Application;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasevideostreaming.Adapter.myViewAdapter;
import com.example.firebasevideostreaming.databinding.ActivityDashboardBinding;
import com.example.firebasevideostreaming.model.videoFileModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.StyledPlayerControlView;
import com.google.android.exoplayer2.ui.StyledPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {

    private ActivityDashboardBinding dashboardBinding;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StyledPlayerView playerView;
    private ExoPlayer exoPlayer;
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
        dashboardBinding.btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                FirebaseRecyclerOptions<videoFileModel> options =
//                        new FirebaseRecyclerOptions.Builder<videoFileModel>()
//                                .setQuery(FirebaseDatabase.getInstance().getReference().child("myvideos"),videoFileModel.class)
//                                .build();

                exoPlayer= new ExoPlayer.Builder(getApplicationContext()).build();
                playerView.setPlayer(exoPlayer);
                String videoUri="https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4";
                // Build the media item.
                MediaItem mediaItem = MediaItem.fromUri(videoUri);
// Set the media item to be played.
                exoPlayer.setMediaItem(mediaItem);
// Prepare the player.
                exoPlayer.prepare();
// Start the playback.
                exoPlayer.play();

//        * // Build the media items.
//                MediaItem firstItem = MediaItem.fromUri(firstVideoUri);
//                MediaItem secondItem = MediaItem.fromUri(secondVideoUri);
//// Add the media items to be played.
//                player.addMediaItem(firstItem);
//                player.addMediaItem(secondItem);
//// Prepare the player.
//                player.prepare();
//// Start the playback.
//                player.play();
//*
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        exoPlayer.release();
    }
}

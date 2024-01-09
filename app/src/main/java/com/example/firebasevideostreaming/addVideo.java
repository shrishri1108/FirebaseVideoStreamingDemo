package com.example.firebasevideostreaming;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.MediaController;
import android.widget.Toast;

import com.example.firebasevideostreaming.databinding.ActivityAddVideoBinding;
import com.example.firebasevideostreaming.model.videoFileModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class addVideo extends AppCompatActivity {

    private ActivityAddVideoBinding addVideoBinding;
    private Uri video_url;
    private StorageReference storageReference;
    private MediaController mediaController;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addVideoBinding= ActivityAddVideoBinding.inflate(getLayoutInflater());
        setContentView(addVideoBinding.getRoot());
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference("myvideos");
        mediaController= new MediaController(this);
        addVideoBinding.myVideoScreen.setMediaController(mediaController);
        addVideoBinding.btnBrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent=new Intent();
                                intent.setType("video/*");
                                intent.setAction(Intent.ACTION_GET_CONTENT );
                                startActivityForResult(intent, 101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        addVideoBinding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                processVideoUploading();
            }
        });
    }

    private String getExtension(){
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(video_url));
    }
    private void processVideoUploading() {
        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle(" Media Uploader ");
        progressDialog.show();
        StorageReference uploader=storageReference.child("videos/"+System.currentTimeMillis()+"."+ getExtension());
        uploader.putFile(video_url)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                addVideoBinding.progressBars.setVisibility(View.INVISIBLE);
                                videoFileModel fileModel=new videoFileModel(addVideoBinding.etVideoName.getText().toString(),uri.toString());
                                databaseReference.child(databaseReference.push().getKey()).setValue(fileModel)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(getApplicationContext(),"Successfully uploaded",Toast.LENGTH_LONG);
                                                    progressDialog.dismiss();
                                                }
                                            })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(addVideo.this, "Failed to upload ", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent= ( 100*snapshot.getBytesTransferred())/ snapshot.getTotalByteCount();
                        progressDialog.setMessage("  Uploading : "+ (int)percent+"%");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==101 && resultCode==RESULT_OK){
            video_url= data.getData();
            addVideoBinding.myVideoScreen.setVideoURI(video_url);
        }
    }
}
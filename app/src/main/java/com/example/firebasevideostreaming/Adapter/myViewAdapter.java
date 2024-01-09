package com.example.firebasevideostreaming.Adapter;


import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasevideostreaming.model.videoFileModel;

import java.util.ArrayList;

public class myViewAdapter  extends RecyclerView.Adapter<myViewAdapter.myViewHolder> {

    private  ArrayList<videoFileModel> mylist;
    public myViewAdapter(ArrayList<videoFileModel> mylist){
        this.mylist=mylist ;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mylist.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

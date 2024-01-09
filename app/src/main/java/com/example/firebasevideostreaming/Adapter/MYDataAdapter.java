package com.example.firebasevideostreaming.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasevideostreaming.model.videoFileModel;

import java.util.ArrayList;

public class MYDataAdapter  extends RecyclerView.Adapter<MYDataAdapter.myDataViews> {

    private ArrayList<videoFileModel> myLst ;

    public MYDataAdapter(ArrayList<videoFileModel>  mylist) {
        this.myLst=mylist;
    }

    @NonNull
    @Override
    public myDataViews onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull myDataViews holder, int position) {

    }

    @Override
    public int getItemCount() {
        return myLst.size();
    }

    class myDataViews extends RecyclerView.ViewHolder{

        public myDataViews(@NonNull View itemView) {
            super(itemView);
        }
    }
}

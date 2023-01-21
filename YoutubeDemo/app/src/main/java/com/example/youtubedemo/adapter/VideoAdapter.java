package com.example.youtubedemo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubedemo.R;
import com.example.youtubedemo.face.OnClickVideoItems;
import com.example.youtubedemo.model.Video;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {
    Context context;
    int videoLayout;
    List<Video> videoList;
    OnClickVideoItems onClickVideoItems;

    public VideoAdapter(Context context, int videoLayout, List<Video> videoList) {
        this.context = context;
        this.videoLayout = videoLayout;
        this.videoList = videoList;
    }

    public VideoAdapter(Context context, int videoLayout, List<Video> videoList, OnClickVideoItems onClickVideoItems) {
        this.context = context;
        this.videoLayout = videoLayout;
        this.videoList = videoList;
        this.onClickVideoItems = onClickVideoItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE); // là 1 component(thành phần) giúp chuyển file XML sang file Java
        View view = inflater.inflate(videoLayout,parent,false);
        return new VideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Video v = videoList.get(position);
        Picasso.get().load(v.getImage()).into(holder.imgVideo);
        holder.txtNameVideo.setText(v.getName());
        holder.txtAuthorVideo.setText(v.getNameAuthor());
        holder.llClickVideoItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickVideoItems.setOnClickVideoListener(v.getVideoId(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgVideo;
        TextView txtNameVideo, txtAuthorVideo;
        LinearLayout llClickVideoItems;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgVideo = itemView.findViewById(R.id.imgVideo);
            txtNameVideo = itemView.findViewById(R.id.txtNameVideo);
            txtAuthorVideo = itemView.findViewById(R.id.txtAuthorVideo);
            llClickVideoItems = itemView.findViewById(R.id.llClickVideoItems);

        }
    }
}

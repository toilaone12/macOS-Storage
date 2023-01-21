package com.example.fricashop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fricashop.R;
import com.example.fricashop.face.FeedBackInterface;
import com.example.fricashop.model.Contact;
import com.example.fricashop.model.Status;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.UserPresenter;
import com.example.fricashop.view.FeedBack;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedBackAdapter extends RecyclerView.Adapter<FeedBackAdapter.ViewHolder> {
    Context context;
    int feedBackLayout;
    List<Status> fbList;
    List<User> uList;
    FeedBackInterface feedBackInterface;

    public FeedBackAdapter(Context context, int feedBackLayout, List<Status> fbList, List<User> uList) {
        this.context = context;
        this.feedBackLayout = feedBackLayout;
        this.fbList = fbList;
        this.uList = uList;
    }

    public FeedBackAdapter(Context context, int feedBackLayout, List<Status> fbList, List<User> uList, FeedBackInterface feedBackInterface) {
        this.context = context;
        this.feedBackLayout = feedBackLayout;
        this.fbList = fbList;
        this.uList = uList;
        this.feedBackInterface = feedBackInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(feedBackLayout,parent,false);
        return new FeedBackAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Status fb = fbList.get(position);
        User u = uList.get(position);
        Picasso.get().load(u.getImage()).resize(200,200).centerCrop().into(holder.imgNameStatus);
        holder.txtNameStatus.setText(u.getName());
        holder.txtNumberComment.setText(fb.getNumberComment()+"");
        holder.txtNumberLike.setText(fb.getNumberLike()+"");
        holder.txtTitleStatus.setText(fb.getTitle());
        Picasso.get().load(fb.getImage()).resize(200,200).centerCrop().into(holder.imgStatus);
        holder.imgLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.imgLike.isChecked()){
                    holder.txtNumberLike.setText((fb.getNumberLike() + 1)+"");
                    fb.setNumberLike(fb.getNumberLike() + 1);
                }else {
                    holder.txtNumberLike.setText((fb.getNumberLike() - 1) + "");
                    fb.setNumberLike(fb.getNumberLike() - 1);
                }
                    feedBackInterface.setOnClickLikeListener(fb,position);
                }
//            }
        });
        holder.imgComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedBackInterface.setOnClickCommentListener(fb,u,position);
            }
        });
        if(UserPresenter.userList.get(0).getName().equals(u.getName())){
            holder.imgDots.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(feedBackInterface.getContextFeedBack(),holder.imgDots);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_delete,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()){
                                case R.id.itDeletePost:
//                                Toast.makeText(context, "Vào đây", Toast.LENGTH_SHORT).show();
                                    feedBackInterface.setOnClickDeleteListener(fb,u,position);
                                    break;
                                case R.id.itUpdatePost:
                                    feedBackInterface.setOnClickUpdateListener(fb,u,position);
                                    break;
                            }

                            return true;
                        }
                    });
                    popupMenu.show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return fbList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgNameStatus,imgComment,imgStatus,imgDots;
        ToggleButton imgLike;
        TextView txtNameStatus,txtNumberLike,txtNumberComment,txtTitleStatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNameStatus = itemView.findViewById(R.id.imgNameStatus);
            imgLike = itemView.findViewById(R.id.imgLike);
            imgComment = itemView.findViewById(R.id.imgComment);
            imgStatus = itemView.findViewById(R.id.imgStatus);
            imgDots = itemView.findViewById(R.id.imgDots);
            txtNameStatus = itemView.findViewById(R.id.txtNameStatus);
            txtNumberLike = itemView.findViewById(R.id.txtNumberLike);
            txtNumberComment = itemView.findViewById(R.id.txtNumberComment);
            txtTitleStatus = itemView.findViewById(R.id.txtTitleStatus);
        }
    }
}

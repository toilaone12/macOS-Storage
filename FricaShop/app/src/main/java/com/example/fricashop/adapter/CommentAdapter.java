package com.example.fricashop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fricashop.R;
import com.example.fricashop.face.OnClickComment;
import com.example.fricashop.model.Comment;
import com.example.fricashop.model.Status;
import com.example.fricashop.model.User;
import com.example.fricashop.presenter.UserPresenter;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    Context context;
    int cLayout;
    List<Comment> cList;
    OnClickComment onClickComment;
//    List<User> uList;


    public CommentAdapter(Context context, int cLayout, List<Comment> cList) {
        this.context = context;
        this.cLayout = cLayout;
        this.cList = cList;
    }

    public CommentAdapter(Context context, int cLayout, List<Comment> cList, OnClickComment onClickComment) {
        this.context = context;
        this.cLayout = cLayout;
        this.cList = cList;
        this.onClickComment = onClickComment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(cLayout,parent,false);
        return new CommentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Comment c = cList.get(position);
//        User u = uList.get(position);
        Picasso.get().load(c.getImg_blog()).resize(50,50).centerCrop().into(holder.imgGetComment);
        Log.d("AAA",c.getImg_blog());
        holder.txtGetNameComment.setText(c.getName_blog());
        holder.txtGetDescComment.setText(c.getDesc_blog());
        holder.txtTimeComment.setText(ConvertDateToReadableDate(c.getDate_blog()));
        holder.txtXoaComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickComment.onClickDelete(c, position);
            }
        });
        holder.txtSuaComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickComment.onClickEdit(c,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cList.size();
    }

    public String ConvertDateToReadableDate(String DateTime) {
                // the input should be in this format 2019-03-08 15:14:29
                //if not you have to change the pattern in SimpleDateFormat("yyyy-MM-dd hh:mm:ss")

                Date newDate;
                Date currentDate = new java.util.Date();
                int hour = 0, min = 0, sec = 0;
                int dayName = 0, dayNum = 0, monthName = 0, year = 0;
                long numOfMilliSecondPassed = 0;
                float milliSecond = 60000.0f; // 1 minute is 3600000 milliseconds
                int numOfMinutePass;

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    newDate = dateFormat.parse(DateTime); // convert String to date
                    numOfMilliSecondPassed = currentDate.getTime() - newDate.getTime(); //get the difference in date in millisecond
                    sec = (int)(numOfMilliSecondPassed / (1000));
                    min = (int)(numOfMilliSecondPassed / (1000*60));
                    hour = (int)(numOfMilliSecondPassed / (1000*3600));
                    dayNum = (int)(numOfMilliSecondPassed / (1000*86400));

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //Convert the milliseconds to days
                numOfMinutePass = (int)(numOfMilliSecondPassed / milliSecond);
//                numOfHourPass = ()
                Log.d("AAAAA",numOfMinutePass+" - "+numOfMilliSecondPassed+" - "+dayNum+"");
                if(numOfMinutePass < 1){
                    return sec + " giây";
                }
                else if(numOfMinutePass >= 1 && numOfMinutePass < 60){
                    return min + " phút";
                }
                else if ((numOfMinutePass >= 60) && (numOfMinutePass < 1440)) {
                    return hour +" giờ ";
                } else if ((numOfMinutePass >= 1440) && (numOfMinutePass < 10080)) {
                    return dayNum + " ngày";
                } else if ((numOfMinutePass >= 10080) && (numOfMinutePass < 43200)) {
                    int weeks = (int) numOfMinutePass / 10080;

                    if(weeks > 1) {
                        return weeks + " tuần";
                    }else{
                        return weeks + " tuần";
                    }
                }else if((numOfMinutePass >= 43200) && (numOfMinutePass < 525600) ){
                    int months = (int) numOfMinutePass/44640;

                    if(months > 1) {
                        return months + " tháng";
                    }else{
                        return months + " tháng";
                    }
                }else if((numOfMinutePass >= 525600)){
                    int years = (int) numOfMinutePass/518400;

                    if(years > 1) {
                        return years + " năm";
                    }else{
                        return years + " năm";
                    }
                }else{
                    return "";
                }

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgGetComment;
        TextView txtGetNameComment,txtGetDescComment,txtTimeComment,txtXoaComment,txtSuaComment;
        LinearLayout llClickComment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGetComment = itemView.findViewById(R.id.imgGetComment);
            txtGetNameComment = itemView.findViewById(R.id.txtGetNameComment);
            txtGetDescComment = itemView.findViewById(R.id.txtGetDescComment);
            txtTimeComment = itemView.findViewById(R.id.txtTimeComment);
            txtSuaComment = itemView.findViewById(R.id.txtSuaComment);
            txtXoaComment = itemView.findViewById(R.id.txtXoaComment);
            llClickComment = itemView.findViewById(R.id.llClickComment);
        }
    }
}

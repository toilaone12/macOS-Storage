package com.example.fricashop.adapter;

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

import com.example.fricashop.R;
import com.example.fricashop.face.onClickCateItems;
//import com.example.fricashop.face.onClickItems;
import com.example.fricashop.model.Category;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    Context context;
    int categoryLayout;
    List<Category> categoryList;
    onClickCateItems onClickItems;

    public CategoryAdapter(Context context, int categoryLayout, List<Category> categoryList) {
        this.context = context;
        this.categoryLayout = categoryLayout;
        this.categoryList = categoryList;
    }

    public CategoryAdapter(Context context, int categoryLayout, List<Category> categoryList, com.example.fricashop.face.onClickCateItems onClickItems) {
        this.context = context;
        this.categoryLayout = categoryLayout;
        this.categoryList = categoryList;
        this.onClickItems = onClickItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(categoryLayout,parent,false);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = categoryList.get(position);
        Picasso.get().load(category.getImage()).into(holder.imgCate);
        holder.txtNameCate.setText(category.getName());
        holder.llClickCate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItems.setOnClickCate(category,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout llClickCate;
        TextView txtNameCate;
        ImageView imgCate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameCate = itemView.findViewById(R.id.txtNameCate);
            imgCate = itemView.findViewById(R.id.imgCate);
            llClickCate = itemView.findViewById(R.id.llClickCate);
        }
    }
}

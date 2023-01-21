package com.example.shopapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.R;
import com.example.shopapp.model.OnClickListener;
import com.example.shopapp.model.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RcyHomePageAdapter extends RecyclerView.Adapter<RcyHomePageAdapter.ViewHolder>{
    Context context;
    int layout;
    List<Product> list;
    OnClickListener onClickListener;

    public RcyHomePageAdapter(Context context, int layout, List<Product> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    public RcyHomePageAdapter(Context context, int layout, List<Product> list, OnClickListener onClickListener) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product p = list.get(position);
        if (p.getImage() != null){
            Picasso.get().load(p.getImage()).error(R.drawable.laptop).into(holder.imgAnhSanPham);
        }else{
            Picasso.get().load(R.drawable.card).error(R.drawable.card).into(holder.imgAnhSanPham);
        }
        Locale locale = new Locale("vi","VN");
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        holder.imgNew.setImageResource(R.drawable.news);
        holder.txtTenSanPham.setText(p.getName());
        holder.txtGiaSanPham.setText("Giá: "+currency.format(p.getPrice()));
        holder.llClickHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onClickProductListener(position,p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgNew,imgAnhSanPham;
        TextView txtTenSanPham, txtGiaSanPham;
        LinearLayout llClickHP;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgAnhSanPham = itemView.findViewById(R.id.imgAnhSanPham);
            imgNew = itemView.findViewById(R.id.imgNew);
            txtTenSanPham = itemView.findViewById(R.id.txtTenSanPham);
            txtGiaSanPham = itemView.findViewById(R.id.txtGiaSanPham);
            llClickHP = itemView.findViewById(R.id.llClickHP);
        }
    }
}

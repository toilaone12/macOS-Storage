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
import com.example.fricashop.face.OnClickProductItems;
import com.example.fricashop.model.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    Context context;
    int myLayout;
    List<Product> pList = new ArrayList<>();
    Product p;
    OnClickProductItems onClickProductItems;

    public ProductAdapter(Context context, int myLayout, List<Product> productList) {
        this.context = context;
        this.myLayout = myLayout;
        this.pList = productList;
    }

    public ProductAdapter(Context context, int myLayout, List<Product> productList, OnClickProductItems onClickProductItems) {
        this.context = context;
        this.myLayout = myLayout;
        this.pList = productList;
        this.onClickProductItems = onClickProductItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(myLayout,parent,false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        p = pList.get(position);
        if(p.getImage() != null){
            Picasso.get().load(p.getImage()).into(holder.imgAnhSanPham);
        }else{
            Picasso.get().load(R.drawable.card).into(holder.imgAnhSanPham);
        }
        Locale locale = new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        String giaSp = numberFormat.format(p.getPrice());
        holder.txtGiaSanPham.setText(giaSp+"đ");
        holder.txtTenSanPham.setText(p.getName());
        holder.llClickHP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickProductItems.setOnClickProductItemsListener(p,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgAnhSanPham, imgNew;
        TextView txtTenSanPham,txtGiaSanPham;
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

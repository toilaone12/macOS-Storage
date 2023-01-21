package com.example.shopapp.adapter;

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

import com.example.shopapp.R;
import com.example.shopapp.model.OnClickListener;
import com.example.shopapp.model.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RcyProductAdapter extends RecyclerView.Adapter<RcyProductAdapter.ViewHolder> {
    Context context;
    int layout;
    List<Product> list;
    OnClickListener onClickListener;

    public RcyProductAdapter(Context context, int layout, List<Product> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    public RcyProductAdapter(Context context, int layout, List<Product> list, OnClickListener onClickListener) {
        this.context = context;
        this.layout = layout;
        this.list = list;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public RcyProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,parent,false);
        return new RcyProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcyProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product p = list.get(position);
        if (p.getImage() != null){
            Picasso.get().load(p.getImage()).error(R.drawable.laptop1).into(holder.imgProduct);
        }else{
            Picasso.get().load(R.drawable.card).error(R.drawable.card).into(holder.imgProduct);
        }
        Locale locale = new Locale("vi","VN");
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        holder.txtNameProduct.setText(p.getName());
        holder.txtPriceProduct.setText("Giá: "+currency.format(p.getPrice()));
        holder.txtDescProduct.setText(p.getDesc());
        holder.llClick.setOnClickListener(new View.OnClickListener() {
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
        ImageView imgProduct;
        TextView txtNameProduct, txtPriceProduct, txtDescProduct;
        LinearLayout llClick;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            txtNameProduct = itemView.findViewById(R.id.txtNameProduct);
            txtPriceProduct = itemView.findViewById(R.id.txtPriceProduct);
            txtDescProduct = itemView.findViewById(R.id.txtDescProduct);
            llClick = itemView.findViewById(R.id.llClick);
        }
    }
}

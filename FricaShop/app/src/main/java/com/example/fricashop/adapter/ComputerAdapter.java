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
import com.example.fricashop.face.onClickCateItems;
//import com.example.fricashop.face.onClickItems;
import com.example.fricashop.model.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ComputerAdapter extends RecyclerView.Adapter<ComputerAdapter.ViewHolder> {
    Context context;
    int computerLayout;
    List<Product> productList;
    OnClickProductItems onClickItems;

    public ComputerAdapter(Context context, int computerLayout, List<Product> productList, com.example.fricashop.face.OnClickProductItems onClickItems) {
        this.context = context;
        this.computerLayout = computerLayout;
        this.productList = productList;
        this.onClickItems = onClickItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(computerLayout,parent,false);
        return new ComputerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Product p = productList.get(position);
        if(p.getImage() != null){
            Picasso.get().load(p.getImage()).into(holder.imgComputer);
        }else{
            Picasso.get().load(R.drawable.dientu).into(holder.imgComputer);
        }
        holder.txtNameComputer.setText(p.getName());
        Locale locale = new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        String money = numberFormat.format(p.getPrice());
        holder.txtPriceComputer.setText(money+"đ");
        holder.llClickComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItems.setOnClickProductItemsListener(p,position);
            }
        });
    }
    public void filterList(List<Product> pList){
        productList = pList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgComputer;
        TextView txtNameComputer, txtPriceComputer;
        LinearLayout llClickComputer;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgComputer = itemView.findViewById(R.id.imgComputer);
            txtNameComputer = itemView.findViewById(R.id.txtNameComputer);
            txtPriceComputer = itemView.findViewById(R.id.txtPriceComputer);
            llClickComputer = itemView.findViewById(R.id.llClickComputer);
        }
    }
}

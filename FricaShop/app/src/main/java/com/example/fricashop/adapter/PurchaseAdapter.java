package com.example.fricashop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fricashop.R;
import com.example.fricashop.model.Cart;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.ViewHolder> {
    Context context;
    int purLayout;
    List<Cart> cList;

    public PurchaseAdapter(Context context, int purLayout, List<Cart> cList) {
        this.context = context;
        this.purLayout = purLayout;
        this.cList = cList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(purLayout,parent,false);
        return new PurchaseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart c = cList.get(position);
        holder.txtSTT.setText(position+1+"");
        holder.txtNamePurchase.setText(c.getNameCart());
        if (c.getImageCart().equals("")){
            holder.imgPurchase.setImageResource(R.drawable.person);
        }else{
            Picasso.get().load(c.getImageCart()).into(holder.imgPurchase);
        }
        holder.txtQuantityPurchase.setText(c.getQuantityCart()+"");
        Locale locale = new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        String gia = numberFormat.format(c.getPriceCart());
        holder.txtPricePurchase.setText(gia+"đ");
    }

    @Override
    public int getItemCount() {
        return cList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNamePurchase,txtSTT,txtQuantityPurchase, txtPricePurchase;
        ImageView imgPurchase;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPurchase = itemView.findViewById(R.id.imgPurchase);
            txtNamePurchase = itemView.findViewById(R.id.txtNamePurchase);
            txtSTT = itemView.findViewById(R.id.txtSTT);
            txtQuantityPurchase = itemView.findViewById(R.id.txtQuantityPurchase);
            txtPricePurchase = itemView.findViewById(R.id.txtPricePurchase);
        }
    }
}

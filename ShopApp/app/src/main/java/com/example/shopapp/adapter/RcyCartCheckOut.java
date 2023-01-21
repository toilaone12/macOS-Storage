package com.example.shopapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shopapp.CartCheckOut;
import com.example.shopapp.R;
import com.example.shopapp.model.Cart;
import com.example.shopapp.model.Product;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class RcyCartCheckOut extends RecyclerView.Adapter<RcyCartCheckOut.ViewHolder> {
    Context context;
    int layout;
    List<Cart> list;

    public RcyCartCheckOut(Context context, int layout, List<Cart> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }


    @NonNull
    @Override
    public RcyCartCheckOut.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,parent,false);
        return new RcyCartCheckOut.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RcyCartCheckOut.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart p = list.get(position);
        if (p.getImageCart() == null){
            Picasso.get().load(p.getImageCart()).error(R.drawable.card).into(holder.imgCart);
        }else{
            Picasso.get().load(R.drawable.card).error(R.drawable.card).into(holder.imgCart);
        }
        Locale locale = new Locale("vi","VN");
        NumberFormat currency = NumberFormat.getCurrencyInstance(locale);
        int sl = Integer.parseInt(holder.edtQuantity.getText().toString());
        if(sl <= 1){
            holder.imgMinus.setEnabled(false);
        }
        holder.edtQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityUpdate = Integer.parseInt(holder.edtQuantity.getText().toString());
//                Log.d("tien",quantityUpdate+"");
                if(quantityUpdate <= 15){
                    int slht = p.getQuantityCart();
                    int giaht = p.getPriceCart();
                    long giaMoiNhat = (giaht * quantityUpdate) / slht;
                    list.get(position).setQuantityCart(quantityUpdate);
                    list.get(position).setPriceCart((int) giaMoiNhat);
                    holder.txtPriceCart.setText("Giá: "+currency.format(giaMoiNhat));
                    holder.edtQuantity.setText(quantityUpdate+"");
                    CartCheckOut.takeAllMoney();
                }else{
                    Toast.makeText(context, "Không đủ tồn!", Toast.LENGTH_SHORT).show();
                    holder.edtQuantity.setText(1+"");
                    int giaht = p.getPriceCart();
                    int giaMoiNhat = (giaht * 1) / p.getQuantityCart();
                    list.get(position).setPriceCart(giaMoiNhat);
                    list.get(position).setQuantityCart(1);
                    holder.txtPriceCart.setText("Giá: "+currency.format(giaMoiNhat));
                    CartCheckOut.takeAllMoney();
                }
            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int minusUpdate = Integer.parseInt(holder.edtQuantity.getText().toString()) - 1;
//                System.out.println(plus);
                int slht = p.getQuantityCart();
                long giaht = p.getPriceCart();
                long giamoinhat = (giaht * minusUpdate) / slht;
                list.get(position).setQuantityCart(minusUpdate);
                list.get(position).setPriceCart((int) giamoinhat);
                holder.txtPriceCart.setText("Giá: "+currency.format(giamoinhat));
                holder.edtQuantity.setText(minusUpdate+"");
                CartCheckOut.takeAllMoney();
//                System.out.println(minusUpdate+" dòng 67");
                if(minusUpdate <= 1){
//                    System.out.println(minusUpdate+" dòng 69");
                    holder.imgMinus.setEnabled(false);
                }
            }
        });
        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int plusUpdate = Integer.parseInt(holder.edtQuantity.getText().toString()) + 1;
                int slht = p.getQuantityCart();
                long giaht = p.getPriceCart();
                list.get(position).setQuantityCart(plusUpdate);
                long giamoinhat = (giaht * plusUpdate) / slht;
                list.get(position).setPriceCart((int) giamoinhat);
                holder.txtPriceCart.setText("Giá: "+currency.format(giamoinhat));
                holder.edtQuantity.setText(plusUpdate+"");
                CartCheckOut.takeAllMoney();
//                System.out.println(sl+" dòng 85");
                if(plusUpdate > 1){
//                    System.out.println(plusUpdate+" dòng 87");
                    holder.imgMinus.setEnabled(true);
                }
            }
        });
        holder.edtQuantity.setText(p.getQuantityCart()+"");
        holder.txtNameCart.setText(p.getNameCart());
        holder.txtPriceCart.setText("Giá: "+currency.format(p.getPriceCart()));
        Picasso.get().load(list.get(position).getImageCart()).into(holder.imgCart);
        CartCheckOut.takeAllMoney();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCart,imgPlus,imgMinus;
        TextView txtNameCart, txtPriceCart;
        EditText edtQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCart = itemView.findViewById(R.id.imgCart);
            imgPlus = itemView.findViewById(R.id.imgPlus);
            imgMinus = itemView.findViewById(R.id.imgMinus);
            txtNameCart = itemView.findViewById(R.id.txtNameCart);
            txtPriceCart = itemView.findViewById(R.id.txtPriceCart);
            edtQuantity = itemView.findViewById(R.id.edtQuantity);
        }
    }
}

package com.example.fricashop.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fricashop.R;
import com.example.fricashop.model.Cart;
import com.example.fricashop.view.CartCheckOut;
import com.example.fricashop.view.DetailProduct;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Context context;
    int layout;
    List<Cart> list;

    public CartAdapter(Context context, int layout, List<Cart> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }


    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layout,parent,false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Cart cart = list.get(position);
        if (cart.getImageCart() == null){
            Picasso.get().load(cart.getImageCart()).error(R.drawable.card).into(holder.imgCart);
        }else{
            Picasso.get().load(R.drawable.card).error(R.drawable.card).into(holder.imgCart);
        }
        Locale locale = new Locale("vi","VN");
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        int sl = Integer.parseInt(holder.edtQuantity.getText().toString());
        if(sl == 1){
            holder.imgMinus.setEnabled(false);
        }
        holder.edtQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int quantityUpdate = Integer.parseInt(holder.edtQuantity.getText().toString());
//                Log.d("tien",quantityUpdate+"");
                if(quantityUpdate <= DetailProduct.p.getQuantity()){
                    int slht = cart.getQuantityCart();
                    int giaht = cart.getPriceCart();
                    long giaMoiNhat = (giaht * quantityUpdate) / slht;
                    String giaMN = numberFormat.format(giaMoiNhat);
                    list.get(position).setQuantityCart(quantityUpdate);
                    list.get(position).setPriceCart((int) giaMoiNhat);
                    holder.txtPriceCart.setText("Giá: "+giaMN+"đ");
                    holder.edtQuantity.setText(quantityUpdate+"");
                    CartCheckOut.takeAllMoney();
                }else{
                    Toast.makeText(context, "Không đủ tồn!", Toast.LENGTH_SHORT).show();
                    holder.edtQuantity.setText(1+"");
                    int giaht = cart.getPriceCart();
                    int giaMoiNhat = (giaht * 1) / cart.getQuantityCart();
                    String giaMN = numberFormat.format(giaMoiNhat);
                    list.get(position).setPriceCart(giaMoiNhat);
                    list.get(position).setQuantityCart(1);
                    holder.txtPriceCart.setText("Giá: "+giaMN+"đ");
                    CartCheckOut.takeAllMoney();
                }
            }
        });
        holder.imgMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int minusUpdate = Integer.parseInt(holder.edtQuantity.getText().toString()) - 1;
//                System.out.println(plus);
                int slht = cart.getQuantityCart();
                long giaht = cart.getPriceCart();
                long giaMoiNhat = (giaht * minusUpdate) / slht;
                String giaMN = numberFormat.format(giaMoiNhat);
                list.get(position).setQuantityCart(minusUpdate);
                list.get(position).setPriceCart((int) giaMoiNhat);
                holder.txtPriceCart.setText("Giá: "+giaMN+"đ");
                holder.edtQuantity.setText(minusUpdate+"");
                CartCheckOut.takeAllMoney();
//                System.out.println(minusUpdate+" dòng 67");
                if(minusUpdate == 1){
//                    System.out.println(minusUpdate+" dòng 69");
                    holder.imgMinus.setEnabled(false);
                }
            }
        });
        holder.imgPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int plusUpdate = Integer.parseInt(holder.edtQuantity.getText().toString()) + 1;

//                System.out.println(sl+" dòng 85");
                if(plusUpdate > 1){
                    if(plusUpdate > DetailProduct.p.getQuantity()){
                        Toast.makeText(context, "Chỉ còn tồn: "+DetailProduct.p.getQuantity()+" sản phẩm ", Toast.LENGTH_SHORT).show();
                    }else{
                        int slht = cart.getQuantityCart();
                        long giaht = cart.getPriceCart();
                        list.get(position).setQuantityCart(plusUpdate);
                        long giamoinhat = (giaht * plusUpdate) / slht;
                        String giaMN = numberFormat.format(giamoinhat);
                        list.get(position).setPriceCart((int) giamoinhat);
                        holder.txtPriceCart.setText("Giá: "+giaMN+"đ");
                        holder.edtQuantity.setText(plusUpdate+"");
                        CartCheckOut.takeAllMoney();
                        holder.imgMinus.setEnabled(true);
                    }
//                    System.out.println(plusUpdate+" dòng 87");

                }
            }
        });
        holder.edtQuantity.setText(cart.getQuantityCart()+"");
        holder.txtNameCart.setText(cart.getNameCart());
        String giaMoiNhat = numberFormat.format(cart.getPriceCart());
        holder.txtPriceCart.setText("Giá: "+giaMoiNhat+"đ");
        Picasso.get().load(list.get(position).getImageCart()).into(holder.imgCart);
        CartCheckOut.takeAllMoney();
        holder.llClickCart.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder b = new AlertDialog.Builder(context);
                b.setTitle("Yêu cầu xóa sản phẩm");
                b.setMessage("Bạn có muốn xóa sản phẩm "+cart.getNameCart());
                b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.remove(position);
                        CartCheckOut.takeAllMoney();
                        notifyDataSetChanged();
                    }
                });
                b.show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgCart,imgPlus,imgMinus;
        TextView txtNameCart, txtPriceCart;
        EditText edtQuantity;
        LinearLayout llClickCart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCart = itemView.findViewById(R.id.imgCart);
            imgPlus = itemView.findViewById(R.id.imgPlus);
            imgMinus = itemView.findViewById(R.id.imgMinus);
            txtNameCart = itemView.findViewById(R.id.txtNameCart);
            txtPriceCart = itemView.findViewById(R.id.txtPriceCart);
            edtQuantity = itemView.findViewById(R.id.edtQuantity);
            llClickCart = itemView.findViewById(R.id.llClickCart);
        }
    }
}

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
import com.example.fricashop.face.CartInterface;
import com.example.fricashop.model.Payment;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {
    List<Payment> paymentList;
    Context context;
    int paymentLayout;
    CartInterface cartInterface;

    public PaymentAdapter(List<Payment> paymentList, Context context, int paymentLayout) {
        this.paymentList = paymentList;
        this.context = context;
        this.paymentLayout = paymentLayout;
    }

    public PaymentAdapter(List<Payment> paymentList, Context context, int paymentLayout, CartInterface cartInterface) {
        this.paymentList = paymentList;
        this.context = context;
        this.paymentLayout = paymentLayout;
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(paymentLayout,parent,false);
        return new PaymentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Payment p = paymentList.get(position);
        holder.imgPayment.setImageResource(p.getImage());
        holder.txtNamePayment.setText(p.getName());
        holder.txtDescPayment.setText(p.getDesc());
        holder.llClickPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartInterface.onClickPaymentItems(p,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtNamePayment,txtDescPayment;
        ImageView imgPayment;
        LinearLayout llClickPayment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPayment = itemView.findViewById(R.id.imgPayment);
            txtNamePayment = itemView.findViewById(R.id.txtNamePayment);
            txtDescPayment = itemView.findViewById(R.id.txtDescPayment);
            llClickPayment = itemView.findViewById(R.id.llClickPayment);
//        txtCreditCart = findViewById(R.id.txtCreditCart);

        }
    }
}

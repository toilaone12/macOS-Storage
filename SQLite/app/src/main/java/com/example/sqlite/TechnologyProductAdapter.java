package com.example.sqlite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TechnologyProductAdapter extends BaseAdapter {
    List<Books> productList = new ArrayList<>();
    Context context;
    int layout;
    OnDeleteItemsClickListener clickItem;
    OnUpdateItemsClickListener updateItem;

    public TechnologyProductAdapter(List<Books> productList, Context context, int layout, OnDeleteItemsClickListener clickItem, OnUpdateItemsClickListener updateItem) {
        this.productList = productList;
        this.context = context;
        this.layout = layout;
        this.clickItem = clickItem;
        this.updateItem = updateItem;
    }

    public TechnologyProductAdapter(List<Books> productList, Context context, int layout, OnDeleteItemsClickListener clickItem) {
        this.productList = productList;
        this.context = context;
        this.layout = layout;
        this.clickItem = clickItem;
    }

    public void filterList(List<Books> list){
        productList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return productList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.txtNameProduct = view.findViewById(R.id.txtNameProduct);
            viewHolder.txtDescProduct = view.findViewById(R.id.txtDescProduct);
            viewHolder.txtPageProduct = view.findViewById(R.id.txtPageProduct);
            viewHolder.txtPriceProduct = view.findViewById(R.id.txtPriceProduct);
            viewHolder.imgImage = view.findViewById(R.id.imgImage);
            viewHolder.imgEdit = view.findViewById(R.id.imgEdit);
            viewHolder.imgDelete = view.findViewById(R.id.imgDelete);


            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Books b = productList.get(i);
        viewHolder.txtNameProduct.setText(b.getName());
        viewHolder.txtDescProduct.setText(b.getDesc());
        viewHolder.txtPageProduct.setText("Số trang: "+b.getPage()+" trang");
        viewHolder.txtPriceProduct.setText("Giá: "+b.getPrice()+" VND");
//            viewHolder.imgImage.set(b.getImage());
        if(b.getImage() != null){
            BitmapFactory.Options options = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeByteArray(b.getImage(),0,b.getImage().length);
            viewHolder.imgImage.setImageBitmap(bitmap);
        }else{
            viewHolder.imgImage.setImageResource(R.drawable.ic_launcher_background);
        }

        viewHolder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItem.onUpdateItemsClickListener(i, b);
            }
        });
        viewHolder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItem.OnDeleteItemsClickListener(i, b);
                System.out.println("Danh sách "+b);
                notifyDataSetChanged();
            }
        });
        return view;
    }

    class ViewHolder {
        //        ImageView imgTechnology;
        TextView txtNameProduct, txtDescProduct, txtPriceProduct, txtPageProduct;
        ImageView imgImage, imgEdit, imgDelete;
    }
}

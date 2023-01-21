package com.example.shopapp.adapter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shopapp.R;
import com.example.shopapp.model.Menu;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuAdapter extends BaseAdapter {
    List<Menu> list ;
    Context context;
    int layout;

    public MenuAdapter(List<Menu> list, Context context, int layout) {
        this.list = list;
        this.context = context;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view =layoutInflater.inflate(layout,viewGroup,false);
        TextView txtNameMenu = view.findViewById(R.id.txtNameMenu);
        ImageView imgMenu = view.findViewById(R.id.imgMenu);
        txtNameMenu.setText(list.get(i).getNameMenu());
        Picasso.get().load(list.get(i).getImgMenu()).into(imgMenu);
        return view;
    }
}
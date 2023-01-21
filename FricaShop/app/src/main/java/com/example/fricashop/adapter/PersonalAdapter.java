package com.example.fricashop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fricashop.R;
import com.example.fricashop.model.User;

import java.util.List;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.viewHolder> {
    List<User> userList;
    Context context;
    int myLayout;

    public PersonalAdapter(List<User> userList, Context context, int myLayout) {
        this.userList = userList;
        this.context = context;
        this.myLayout = myLayout;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(myLayout,parent,false);
        return new PersonalAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        User u = userList.get(position);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle,txtNamePerson;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
//            txtTitle = itemView.findViewById(R.id.txtT);
            txtNamePerson = itemView.findViewById(R.id.txtNamePerson);
        }
    }
}

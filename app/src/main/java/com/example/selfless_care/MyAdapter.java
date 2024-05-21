package com.example.selfless_care;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private Context context;
    private ArrayList name,email,mobile_number,title;

    public MyAdapter(Context context, ArrayList name, ArrayList email, ArrayList mobile_number, ArrayList title) {
        this.context = context;
        this.name = name;
        this.email = email;
        this.mobile_number = mobile_number;
        this.title = title;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.cards,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.name.setText(String.valueOf(name.get(position)));
        holder.title.setText(String.valueOf(title.get(position)));
        holder.email.setText(String.valueOf(email.get(position)));
        holder.mobile_number.setText(String.valueOf(mobile_number.get(position)));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,mobile_number,title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.textname);
            title=itemView.findViewById(R.id.texttitle);
            email=itemView.findViewById(R.id.textemail);
            mobile_number=itemView.findViewById(R.id.textmn);
        }
    }
}

package com.example.selfless_care;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyServicesAdapter extends RecyclerView.Adapter<MyServicesAdapter.MyViewHolder> {
    private Context context;
    private ArrayList servicename,location, mobile_number;

    public MyServicesAdapter(Context context, ArrayList servicename, ArrayList location, ArrayList mobile_number) {
        this.context = context;
        this.servicename = servicename;
        this.location = location;
        this.mobile_number = mobile_number;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.bb_cards,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.servicename.setText(String.valueOf(servicename.get(position)));
        holder.location.setText(String.valueOf(location.get(position)));
        holder.mobile_number.setText(String.valueOf(mobile_number.get(position)));
    }

    @Override
    public int getItemCount() {
        return servicename.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView servicename,location,mobile_number;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            servicename=itemView.findViewById(R.id.textservicename);
            location=itemView.findViewById(R.id.textlocation);
            mobile_number=itemView.findViewById(R.id.textmn);
        }
    }
}

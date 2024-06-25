package com.example.projecteliaandyona;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Car> list;

    public MyAdapter(Context context, ArrayList<Car> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Car car = list.get(position);
        holder.carName.setText(car.getCarName());
        holder.year.setText(car.getYear());
        holder.price.setText(car.getPrice());
        holder.phone.setText(car.getPhoneNumber());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView carName, year, price, phone;
        ImageView pic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            carName = itemView.findViewById(R.id.carName);
            year = itemView.findViewById(R.id.carYear);
            price = itemView.findViewById(R.id.carPrice);
            phone = itemView.findViewById(R.id.carPhone);
        }
    }

}

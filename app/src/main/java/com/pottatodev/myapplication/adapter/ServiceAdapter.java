package com.pottatodev.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.model.ProductModel;
import com.pottatodev.myapplication.model.ServiceModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>  {

    List<ServiceModel> services;

    public ServiceAdapter(List<ServiceModel> services){
        this.services = services;
    }

    @NonNull
    @NotNull
    @Override
    public ServiceAdapter.ServiceViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_item, parent, false);

        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ServiceAdapter.ServiceViewHolder holder, int position) {
        holder.tvServiceTitle.setText(services.get(position).getName());
        holder.tvServicePrice.setText("Rp. " + services.get(position).getPrice());
        holder.tvServiceDuration.setText(services.get(position).getDuration() + " minutes");

        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);
        Glide.with(holder.itemView).load(services.get(position).getImageUrl()).apply(options).into(holder.imgService);
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceTitle, tvServicePrice, tvServiceDuration;
        ImageView imgService;

        public ServiceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvServiceTitle = itemView.findViewById(R.id.tvServiceTitle);
            tvServicePrice = itemView.findViewById(R.id.tvServicePrice);
            tvServiceDuration = itemView.findViewById(R.id.tvServiceDuration);
            imgService = itemView.findViewById(R.id.imgService);
        }
    }
}

package com.pottatodev.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {
        public ServiceViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
        }
    }
}

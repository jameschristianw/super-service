package com.pottatodev.myapplication.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.model.ConsultationModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ConsultationAdapter extends RecyclerView.Adapter<ConsultationAdapter.ConsultationViewHolder> {
    List<ConsultationModel> consultations;

    public ConsultationAdapter(List<ConsultationModel> consultations){
        this.consultations = consultations;
    }
    
    @NonNull
    @NotNull
    @Override
    public ConsultationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.consultation_item, parent, false);

        return new ConsultationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ConsultationAdapter.ConsultationViewHolder holder, int position) {
        holder.consultItemTitle.setText(consultations.get(position).getTitle());
        holder.consultItemVehicle.setText(consultations.get(position).getVehicle());
    }

    @Override
    public int getItemCount() {
        return consultations.size();
    }

    public static class ConsultationViewHolder extends RecyclerView.ViewHolder {
        TextView consultItemTitle, consultItemVehicle;

        public ConsultationViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            consultItemTitle = itemView.findViewById(R.id.consultItemTitle);
            consultItemVehicle = itemView.findViewById(R.id.consultItemVehicle);
        }
    }
}

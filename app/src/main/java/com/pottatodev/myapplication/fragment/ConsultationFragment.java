package com.pottatodev.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.adapter.ConsultationAdapter;
import com.pottatodev.myapplication.adapter.ProductAdapter;
import com.pottatodev.myapplication.model.ConsultationModel;
import com.pottatodev.myapplication.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ConsultationFragment extends Fragment {

    List<ConsultationModel> consultations;

    RecyclerView recyclerView;
    ConsultationAdapter adapter;

    boolean hasData = false;

    TextView tvNoConsultation;

    public ConsultationFragment(List<ConsultationModel> consultations){
        this.consultations = consultations;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_consultation, container, false);

        recyclerView = view.findViewById(R.id.rvConsultationList);
        tvNoConsultation = view.findViewById(R.id.tvNoConsultation);

        if (hasData){
            tvNoConsultation.setVisibility(View.GONE);

            recyclerView = view.findViewById(R.id.rvConsultationList);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            adapter = new ConsultationAdapter(consultations);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.GONE);
        }

        return view;
    }
}
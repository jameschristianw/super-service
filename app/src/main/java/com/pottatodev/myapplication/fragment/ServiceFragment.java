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
import com.pottatodev.myapplication.adapter.ServiceAdapter;
import com.pottatodev.myapplication.model.ServiceModel;

import java.util.List;

public class ServiceFragment extends Fragment {

    List<ServiceModel> services;
    RecyclerView recyclerView;
    ServiceAdapter adapter;

    boolean hasData = false;

    TextView tvNoService;

    public ServiceFragment(List<ServiceModel> services) {
        this.services = services;

        if (services.size() > 0){
            hasData = true;
        }
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_service, container, false);

        tvNoService = view.findViewById(R.id.tvNoService);
        recyclerView = view.findViewById(R.id.rvServiceList);

        if (hasData){
            tvNoService.setVisibility(View.GONE);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            adapter = new ServiceAdapter(services);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.GONE);
        }

        return view;
    }
}
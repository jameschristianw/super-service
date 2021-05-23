package com.pottatodev.myapplication.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.adapter.ProductAdapter;
import com.pottatodev.myapplication.model.ProductModel;

import java.util.List;

public class ProductFragment extends Fragment {

    List<ProductModel> products;
    RecyclerView recyclerView;
    ProductAdapter adapter;
    Context context;

    boolean hasData = false;

    TextView tvNoData;

    public ProductFragment(List<ProductModel> products, Context context) {
        this.products = products;
        this.context = context;
        // Required empty public constructor

        if (products.size() > 0){
            hasData = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product, container, false);

        tvNoData = view.findViewById(R.id.tvNoData);
        recyclerView = view.findViewById(R.id.rvProductList);

        if (hasData){
            tvNoData.setVisibility(View.GONE);

            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
            adapter = new ProductAdapter(products, context);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.GONE);
        }

        return view;
    }
}
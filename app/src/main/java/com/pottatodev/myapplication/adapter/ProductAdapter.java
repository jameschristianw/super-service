package com.pottatodev.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.activity.MainActivity;
import com.pottatodev.myapplication.activity.ProductDetailActivity;
import com.pottatodev.myapplication.model.ProductModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    List<ProductModel> products;
    Context context;
    int mode; // 0 user; 1 admin;

    public ProductAdapter(List<ProductModel> products, Context context, int mode){
        this.products = products;
        this.context = context;
        this.mode = mode;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull ProductAdapter.ProductViewHolder holder, int position) {
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);

        Glide.with(holder.itemView).load(products.get(position).getImageUrl()).apply(options).into(holder.imgProductImage);
        holder.tvProductName.setText(products.get(position).getName());
        holder.tvProductPrice.setText("Rp. " + String.valueOf(products.get(position).getPrice()));
        holder.tvProductStock.setText(String.valueOf(products.get(position).getStock()));

        holder.cvProduct.setOnClickListener( (v) -> {
            Intent intent = new Intent(context, ProductDetailActivity.class);
            intent.putExtra(ProductModel.PRODUCT_ID, products.get(position).getId());
            ((MainActivity) context).startActivityForResult(intent, 201);
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductImage;
        TextView tvProductName, tvProductPrice, tvProductStock;
        CardView cvProduct;

        public ProductViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            cvProduct = itemView.findViewById(R.id.cvProduct);

            imgProductImage = itemView.findViewById(R.id.imgProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductStock = itemView.findViewById(R.id.tvProductStock);
        }
    }
}

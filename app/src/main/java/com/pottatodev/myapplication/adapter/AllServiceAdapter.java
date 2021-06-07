package com.pottatodev.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.activity.admin.AdminAddEditProductActivity;
import com.pottatodev.myapplication.activity.admin.AdminAllProductsActivity;
import com.pottatodev.myapplication.helper.Config;
import com.pottatodev.myapplication.helper.H;
import com.pottatodev.myapplication.model.ProductModel;
import com.pottatodev.myapplication.model.ServiceModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllServiceAdapter extends RecyclerView.Adapter<AllServiceAdapter.ProductViewHolder> {

    List<ServiceModel> services;
    Context context;
    int mode; // 0 user; 1 admin;
    H apiInterface;

    public AllServiceAdapter(List<ServiceModel> products, Context context, int mode) {
        this.services = products;
        this.context = context;
        this.mode = mode;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_product_item, parent, false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull AllServiceAdapter.ProductViewHolder holder, int position) {
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);

        Glide.with(holder.itemView).load(services.get(position).getImageUrl()).apply(options).into(holder.imgService);
        holder.tvServiceTitle.setText(services.get(position).getName());
        holder.tvServicePrice.setText("Rp. " + services.get(position).getPrice());
        holder.tvServiceDuration.setText(services.get(position).getDuration());

        holder.imgDeleteButton.setOnClickListener((v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Product")
                    .setMessage("Are you sure you want to delete this product?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.imgDeleteButton.setVisibility(View.GONE);
                            apiInterface = Config.getClient().create(H.class);
                            apiInterface.deleteProductsById(services.get(position).getId()).enqueue(new Callback<ProductModel>() {
                                @Override
                                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                                    services.remove(position);
                                    Toast.makeText(context, "Product is deleted", Toast.LENGTH_SHORT).show();
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onFailure(Call<ProductModel> call, Throwable t) {
                                }
                            });
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();


        }));

        holder.imgEditButton.setOnClickListener((v -> {
            Intent addEditIntent = new Intent(context, AdminAddEditProductActivity.class);
            addEditIntent.putExtra(ProductModel.PRODUCT_ID, services.get(position).getId());

            ((AdminAllProductsActivity) context).startActivityForResult(addEditIntent, 200);
        }));
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceTitle, tvServicePrice, tvServiceDuration;
        ImageView imgService;
        ImageView imgDeleteButton, imgEditButton;

        public ProductViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            tvServiceTitle = itemView.findViewById(R.id.tvServiceTitle);
            tvServicePrice = itemView.findViewById(R.id.tvServicePrice);
            tvServiceDuration = itemView.findViewById(R.id.tvServiceDuration);
            imgService = itemView.findViewById(R.id.imgService);
            imgDeleteButton = itemView.findViewById(R.id.imgDeleteBtn);
            imgEditButton = itemView.findViewById(R.id.imgEditButton);
        }
    }
}

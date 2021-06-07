package com.pottatodev.myapplication.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.pottatodev.myapplication.R;
import com.pottatodev.myapplication.activity.ProductDetailActivity;
import com.pottatodev.myapplication.activity.admin.AdminAddEditProductActivity;
import com.pottatodev.myapplication.activity.admin.AdminAllProductsActivity;
import com.pottatodev.myapplication.helper.Config;
import com.pottatodev.myapplication.helper.H;
import com.pottatodev.myapplication.model.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.ProductViewHolder> {

    List<ProductModel> products;
    Context context;
    int mode; // 0 user; 1 admin;
    H apiInterface;

    public AllProductAdapter(List<ProductModel> products, Context context, int mode) {
        this.products = products;
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
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull AllProductAdapter.ProductViewHolder holder, int position) {
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);

        Glide.with(holder.itemView).load(products.get(position).getImageUrl()).apply(options).into(holder.imgProductImage);
        holder.tvProductName.setText(products.get(position).getName());
        holder.tvProductPrice.setText("Rp. " + products.get(position).getPrice());
        holder.tvProductStock.setText(String.valueOf(products.get(position).getStock()));

        holder.imgDeleteButton.setOnClickListener((v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Delete Product")
                    .setMessage("Are you sure you want to delete this product?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            holder.imgDeleteButton.setVisibility(View.GONE);
                            apiInterface = Config.getClient().create(H.class);
                            apiInterface.deleteProductsById(products.get(position).getId()).enqueue(new Callback<ProductModel>() {
                                @Override
                                public void onResponse(Call<ProductModel> call, Response<ProductModel> response) {
                                    products.remove(position);
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
            addEditIntent.putExtra(ProductModel.PRODUCT_ID, products.get(position).getId());

            ((AdminAllProductsActivity) context).startActivityForResult(addEditIntent, 200);
        }));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductImage;
        TextView tvProductName, tvProductPrice, tvProductStock;
        CardView cvProduct;
        ImageView imgDeleteButton, imgEditButton;

        public ProductViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);

            cvProduct = itemView.findViewById(R.id.cvProduct);

            imgProductImage = itemView.findViewById(R.id.imgProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductStock = itemView.findViewById(R.id.tvProductStock);
            imgDeleteButton = itemView.findViewById(R.id.imgDeleteBtn);
            imgEditButton = itemView.findViewById(R.id.imgEditButton);
        }
    }
}

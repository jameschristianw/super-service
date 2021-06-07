package com.pottatodev.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.pottatodev.myapplication.activity.ProductDetailActivity;
import com.pottatodev.myapplication.model.ProductModel;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    List<ProductModel> transactions;
    Context context;

    public TransactionAdapter(List<ProductModel> transactions, Context context){
        this.transactions = transactions;
        this.context = context;
    }
    public TransactionAdapter(Context context){
        this.context = context;
    }

    public void setTransactions( List<ProductModel> transactions){
        this.transactions = transactions;
    }

    @NonNull
    @NotNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transaction_item, parent, false);

        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TransactionAdapter.TransactionViewHolder holder, int position) {
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.mipmap.ic_launcher_round).error(R.mipmap.ic_launcher_round);

        Glide.with(holder.itemView).load(transactions.get(position).getImageUrl()).apply(options).into(holder.imgProductImage);
        holder.tvProductName.setText(transactions.get(position).getName());
        holder.tvProductPrice.setText("Rp. " + String.valueOf(transactions.get(position).getPrice()));
        holder.tvProductStock.setText(String.valueOf(transactions.get(position).getStock()));

//        SimpleDateFormat format = new SimpleDateFormat("D M dd HH:mm:ss");
        String datePurchased = "Purchased at " + transactions.get(position).getCreated_at();

        holder.tvProductDatePurchase.setText(datePurchased);
    }

    @Override
    public int getItemCount() {
        return transactions == null ? 0 : transactions.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        ImageView imgProductImage;
        TextView tvProductName, tvProductPrice, tvProductStock, tvProductDatePurchase;

        public TransactionViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imgProductImage = itemView.findViewById(R.id.imgProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvProductStock = itemView.findViewById(R.id.tvProductStock);
            tvProductDatePurchase = itemView.findViewById(R.id.tvProductDatePurchase);
        }
    }
}

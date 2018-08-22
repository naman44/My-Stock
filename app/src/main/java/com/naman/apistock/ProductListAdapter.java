package com.naman.apistock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.naman.apistock.Model.Product;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListViewHolder> {

    private Context mContext;
    private List mList;
    class ProductListViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        ProductListViewHolder(View itemView){
            super(itemView);
            tv = itemView.findViewById(R.id.home_text);
        }
    }

    public ProductListAdapter(Context context, List list){
        mContext = context;
        mList = list;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_main, parent, false);
        return new ProductListViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        Product product = (Product) mList.get(position);
        holder.tv.setText(product.getName());
    }
}
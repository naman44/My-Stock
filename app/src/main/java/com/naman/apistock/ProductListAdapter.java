package com.naman.apistock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.naman.apistock.Model.Product;
import com.naman.apistock.Model.RawMaterial;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListViewHolder> {

    private Context mContext;
    private List mList;
    private ProductListViewHolder.ItemClickListener listener;
    public ProductListAdapter(Context context, List list, ProductListViewHolder.ItemClickListener listener){
        mContext = context;
        mList = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_list, parent, false);
        return new ProductListViewHolder(v, mContext, listener);
    }

    public void updateList(List newList){
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new ProductListCallback(newList, mList));
        mList.clear();
        mList.addAll(newList);
        result.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        if(mList.get(position) instanceof  Product){
            Product product = (Product) mList.get(position);
            holder.setProductObj(product);
        }
        else{
            RawMaterial raw = (RawMaterial) mList.get(position);
            holder.setRawObj(raw);
        }
    }
}

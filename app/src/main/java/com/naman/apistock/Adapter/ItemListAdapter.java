package com.naman.apistock.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.naman.apistock.DiffUtils.ItemListCallBack;
import com.naman.apistock.Model.ItemList;
import com.naman.apistock.R;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {

    private Context mContext;
    private List<ItemList> mList;

    class ItemListViewHolder extends RecyclerView.ViewHolder{

        private TextView iName, iVal1, iVal2, imgText;
        ItemListViewHolder(View itemView){
            super(itemView);
            iName = itemView.findViewById(R.id.item_name);
            iVal1 = itemView.findViewById(R.id.item_value1);
            iVal2 = itemView.findViewById(R.id.item_value2);
            imgText = itemView.findViewById(R.id.text_product_first);
            ImageButton btnEdit = itemView.findViewById(R.id.btn_edit_item);
            ImageButton btnDelete = itemView.findViewById(R.id.btn_del_item);
            //btnDelete.setOnClickListener((View v)-> listener.onDeleteClicked(obj));
            //btnEdit.setOnClickListener((View v) -> listener.onEditClicked(obj));
        }
    }

    public ItemListAdapter(Context context, List<ItemList> list){
        this.mContext = context;
        this.mList = list;
    }

    public void updateList(List<ItemList> newList){
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new ItemListCallBack(newList, mList));
        mList.clear();
        mList.addAll(newList);
        result.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item_list, parent, false);
        return new ItemListViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        holder.iName.setText(mList.get(position).getItem());
        holder.iVal1.setText(mContext.getString(R.string.display_quantity_unit,
                mList.get(position).getQuantity(), mList.get(position).getUnit()));
        holder.iVal2.setText(mContext.getString(R.string.display_price, mList.get(position).getPrice()));
        holder.imgText.setText(mList.get(position).getItem().substring(0,1));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}

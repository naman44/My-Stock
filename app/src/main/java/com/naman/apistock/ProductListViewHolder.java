package com.naman.apistock;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.naman.apistock.Model.Product;
import com.naman.apistock.Model.RawMaterial;

import androidx.recyclerview.widget.RecyclerView;

public class ProductListViewHolder extends RecyclerView.ViewHolder {

    public interface ItemClickListener{
        void onEditClicked(Object obj);
        void onDeleteClicked(Object obj);
    }

    TextView iName, iVal1, iVal2;
    ImageButton btnEdit, btnDelete;
    Context mContext;
    Object obj;
    ProductListViewHolder(View itemView, Context context, ItemClickListener listener){
        super(itemView);
        mContext = context;
        iName = itemView.findViewById(R.id.item_name);
        iVal1 = itemView.findViewById(R.id.item_value1);
        iVal2 = itemView.findViewById(R.id.item_value2);
        btnEdit = itemView.findViewById(R.id.btn_edit_item);
        btnDelete = itemView.findViewById(R.id.btn_del_item);
        btnDelete.setOnClickListener((View v)-> listener.onDeleteClicked(obj));
        btnEdit.setOnClickListener((View v) -> listener.onEditClicked(obj));
    }

    void setProductObj(Product p){
        this.obj = p;
        iName.setText(p.getName());
        iVal1.setText(mContext.getString(R.string.display_weight, p.getWeight()));
        iVal2.setText(mContext.getString(R.string.display_neck, p.getNeckSize()));
    }

    void setRawObj(RawMaterial r){
        this.obj = r;
        iName.setText(r.getName());
        iVal1.setText(r.getGrade());
        iVal2.setText(r.getManufacturer());
    }


}

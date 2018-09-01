package com.naman.apistock.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.naman.apistock.Model.Item;
import com.naman.apistock.Model.Partner;
import com.naman.apistock.R;

import androidx.recyclerview.widget.RecyclerView;

public class ProductListViewHolder extends RecyclerView.ViewHolder {

    public interface ItemClickListener{
        void onEditClicked(Object obj);
        void onDeleteClicked(Object obj);
    }

    private TextView iName, iVal1, iVal2, imgText;
    private Context mContext;
    private Object obj;
    ProductListViewHolder(View itemView, Context context, ItemClickListener listener){
        super(itemView);
        mContext = context;
        iName = itemView.findViewById(R.id.item_name);
        iVal1 = itemView.findViewById(R.id.item_value1);
        iVal2 = itemView.findViewById(R.id.item_value2);
        imgText = itemView.findViewById(R.id.text_product_first);
        ImageButton btnEdit = itemView.findViewById(R.id.btn_edit_item);
        ImageButton btnDelete = itemView.findViewById(R.id.btn_del_item);
        btnDelete.setOnClickListener((View v)-> listener.onDeleteClicked(obj));
        btnEdit.setOnClickListener((View v) -> listener.onEditClicked(obj));
    }

    void setObj(Object o){
        if(o instanceof Item){
            Item item = (Item) o;
            this.obj = item;
            iName.setText(item.getName());
            imgText.setText(item.getName().substring(0,1));
            if(item.getItemType().equalsIgnoreCase(mContext.getString(R.string.item_type_product))){
                iVal1.setText(mContext.getString(R.string.display_weight, item.getWeight()));
                iVal2.setText(mContext.getString(R.string.display_neck, item.getNeckSize()));
            }
            else{
                iVal1.setText(item.getGrade());
                iVal2.setText(item.getManufacturer());
            }
        }
        else if(o instanceof Partner){
            Partner p = (Partner) o;
            this.obj = p;
            iName.setText(p.getName());
            imgText.setText(p.getName().substring(0,1));
            iVal1.setText(p.getOwner());
            iVal2.setText(mContext.getString(R.string.display_phone, p.getContactNo()));
        }
    }
}

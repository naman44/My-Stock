package com.naman.apistock.DiffUtils;

import com.naman.apistock.Model.ItemList;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class ItemListCallBack extends DiffUtil.Callback {

    private List<ItemList> oldList;
    private List<ItemList> newList;

    public ItemListCallBack(List<ItemList> newList, List<ItemList> oldList){
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getItem().equalsIgnoreCase(newList.get(newItemPosition).getItem());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).getItem().equalsIgnoreCase(oldList.get(oldItemPosition).getItem()) &&
                newList.get(newItemPosition).getQuantity() == oldList.get(oldItemPosition).getQuantity() &&
                newList.get(newItemPosition).getPrice() == oldList.get(oldItemPosition).getPrice() &&
                newList.get(newItemPosition).getGstRate() == oldList.get(oldItemPosition).getGstRate();
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

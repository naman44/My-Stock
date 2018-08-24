package com.naman.apistock;

import com.naman.apistock.Model.Product;
import com.naman.apistock.Model.RawMaterial;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class ProductListCallback extends DiffUtil.Callback {

    private List oldList;
    private List newList;

    public ProductListCallback(List newList, List oldList){
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
        Object objOld = oldList.get(oldItemPosition);
        Object objNew = newList.get(newItemPosition);
        if(objNew instanceof Product && objOld instanceof Product){
            return ((Product)objOld).getName().equalsIgnoreCase(((Product)objNew).getName());
        }
        else if(objOld instanceof RawMaterial && objNew instanceof RawMaterial){
            return ((RawMaterial)objOld).getName().equalsIgnoreCase(((RawMaterial)objNew).getName());
        }
        else if(objOld instanceof RawMaterial && objNew instanceof Product){
            return ((RawMaterial)objOld).getName().equalsIgnoreCase(((Product)objNew).getName());
        }
        else if(objOld instanceof Product && objNew instanceof RawMaterial){
            return ((Product)objOld).getName().equalsIgnoreCase(((RawMaterial)objNew).getName());
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Object objOld = oldList.get(oldItemPosition);
        Object objNew = newList.get(newItemPosition);
        if(objNew instanceof Product && objOld instanceof Product){
            return ((Product)objNew).compareObjects((Product) objNew, (Product) objOld);
        }
        else if(objOld instanceof RawMaterial && objNew instanceof RawMaterial){
            ((RawMaterial)objNew).compareObjects((RawMaterial) objNew, (RawMaterial) objOld);
        }
        else if(objOld instanceof RawMaterial && objNew instanceof Product){
            return false;
        }
        else if(objOld instanceof Product && objNew instanceof RawMaterial){
            return false;
        }
        return false;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

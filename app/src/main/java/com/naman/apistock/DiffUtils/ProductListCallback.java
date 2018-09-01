package com.naman.apistock.DiffUtils;

import com.naman.apistock.Model.Item;

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
        Item objOld = (Item) oldList.get(oldItemPosition);
        Item objNew = (Item) newList.get(newItemPosition);
        return objOld.getItemId() == objNew.getItemId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Item objOld = (Item) oldList.get(oldItemPosition);
        Item objNew = (Item) newList.get(newItemPosition);
        if(!objNew.getItemType().equalsIgnoreCase(objOld.getItemType())){
            return false;
        }
        else if(objNew.getItemType().equalsIgnoreCase("Product")){
            return objNew.getName().equalsIgnoreCase(objOld.getName()) &&
                    objNew.getItemType().equalsIgnoreCase(objOld.getItemType()) &&
                    objNew.getBrandName().equalsIgnoreCase(objOld.getBrandName()) &&
                    objNew.getColor().equalsIgnoreCase(objOld.getColor()) &&
                    objNew.getWeight() == objOld.getWeight() &&
                    objNew.getNeckSize() == objOld.getNeckSize() &&
                    objNew.getPlasticType().equalsIgnoreCase(objOld.getPlasticType());
        }
        else{
            return objNew.getName().equalsIgnoreCase(objOld.getName()) &&
                    objNew.getItemType().equalsIgnoreCase(objOld.getItemType()) &&
                    objNew.getColor().equalsIgnoreCase(objOld.getColor()) &&
                    objNew.getGrade().equalsIgnoreCase(objOld.getGrade()) &&
                    objNew.getPlasticType().equalsIgnoreCase(objOld.getPlasticType()) &&
                    objNew.getManufacturer().equalsIgnoreCase(objOld.getManufacturer());
        }
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

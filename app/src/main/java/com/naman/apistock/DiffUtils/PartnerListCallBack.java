package com.naman.apistock.DiffUtils;

import com.naman.apistock.Model.Partner;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class PartnerListCallBack extends DiffUtil.Callback {

    private List oldList;
    private List newList;

    public PartnerListCallBack(List newList, List oldList){
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Partner objOld = (Partner) oldList.get(oldItemPosition);
        Partner objNew = (Partner) newList.get(newItemPosition);
        return objNew.getId() == objOld.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Partner objOld = (Partner) oldList.get(oldItemPosition);
        Partner objNew = (Partner) newList.get(newItemPosition);
        if(!objNew.getType().equalsIgnoreCase(objOld.getType())){
            return false;
        }
        else{
            return objNew.getName().equalsIgnoreCase(objOld.getName()) &&
                    objNew.getOwner().equalsIgnoreCase(objOld.getOwner()) &&
                    objNew.getAddress().equalsIgnoreCase(objOld.getAddress()) &&
                    objNew.getGstNo().equalsIgnoreCase(objOld.getGstNo()) &&
                    objNew.getContactNo() == objOld.getContactNo();
        }
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}

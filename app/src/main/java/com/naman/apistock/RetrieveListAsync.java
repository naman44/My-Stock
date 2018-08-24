package com.naman.apistock;

import android.content.Context;
import android.os.AsyncTask;

import com.naman.apistock.DatabaseAdapter;
import com.naman.apistock.Model.Product;
import com.naman.apistock.R;

import java.util.List;

public class RetrieveListAsync extends AsyncTask<Void, Void, List> {

    private Context mContext;
    private String callType;
    public RetrieveListAsync(Context context, String type){
        callType = type;
        mContext = context;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List doInBackground(Void... voids) {
        DatabaseAdapter dbAdapter = DatabaseAdapter.getInstance(mContext);
        if(callType.equalsIgnoreCase(mContext.getString(R.string.item_type_product))){
            return dbAdapter.productDao().fetchAllProducts();
        }
        else if(callType.equalsIgnoreCase(mContext.getString(R.string.item_type_raw))){
            return dbAdapter.rawDao().fetchAllRawData();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        mContext = null;
    }
}

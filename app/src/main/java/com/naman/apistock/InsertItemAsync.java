package com.naman.apistock;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import com.naman.apistock.Model.Product;
import com.naman.apistock.Model.RawMaterial;

public class InsertItemAsync extends AsyncTask<Void, Void, Long> {

    private Context mContext;
    private Object obj;
    public InsertItemAsync(Context context, Object obj){
        mContext = context;
        this.obj = obj;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Long doInBackground(Void... voids) {
        long result = -1;
        if(obj instanceof Product){
            Product product = (Product) obj;
            result = DatabaseAdapter.getInstance(mContext).productDao().insertProduct(product);
        }
        else if(obj instanceof RawMaterial){
                RawMaterial raw = (RawMaterial) obj;
                result = DatabaseAdapter.getInstance(mContext).rawDao().insertRawData(raw);
            }
        return result;
    }

    @Override
    protected void onPostExecute(Long aLong) {
        super.onPostExecute(aLong);
        mContext = null;
    }
}

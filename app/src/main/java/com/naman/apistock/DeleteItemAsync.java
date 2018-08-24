package com.naman.apistock;

import android.content.Context;
import android.os.AsyncTask;

import com.naman.apistock.Model.Product;
import com.naman.apistock.Model.RawMaterial;

public class DeleteItemAsync extends AsyncTask<Void, Void, Integer> {

    private Context mContext;
    private Object obj;
    public DeleteItemAsync(Context context, Object obj){
        mContext = context;
        this.obj = obj;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        int result = 0;
        if(obj instanceof Product){
            result = DatabaseAdapter.getInstance(mContext).productDao().deleteProduct(((Product) obj).getProductId());
        }
        else if(obj instanceof RawMaterial){
            result = DatabaseAdapter.getInstance(mContext).rawDao().deleteRaw(((RawMaterial) obj).getRawId());
        }
        return result;
    }

    @Override
    protected void onPostExecute(Integer aLong) {
        super.onPostExecute(aLong);
        mContext = null;
    }
}

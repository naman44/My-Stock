package com.naman.apistock;

import android.content.Context;
import android.os.AsyncTask;

import com.naman.apistock.Model.Product;
import com.naman.apistock.Model.RawMaterial;

import java.util.List;

public class ItemAsync extends AsyncTask<Void, Void, List> {

    private Context ctx;
    private Object obj;
    private String process;
    public ItemAsync(Context context, Object obj, String process){
        this.ctx = context;
        this.obj = obj;
        this.process = process;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List doInBackground(Void... voids) {
        if(process.equalsIgnoreCase(StringUtils.INSERT_DATA)){
            if(obj instanceof Product){
                DatabaseAdapter.getInstance(ctx).productDao().insertProduct((Product) obj);
            }
            else if(obj instanceof RawMaterial){
                DatabaseAdapter.getInstance(ctx).rawDao().insertRawData((RawMaterial) obj);
            }
            return null;
        }
        else if(process.equalsIgnoreCase(StringUtils.UPDATE_DATA)){
            if(obj instanceof Product){
                DatabaseAdapter.getInstance(ctx).productDao().updateProduct((Product) obj);
            }
            else if(obj instanceof RawMaterial){
                DatabaseAdapter.getInstance(ctx).rawDao().updateRawData((RawMaterial) obj);
            }
            return null;
        }
        else if(process.equalsIgnoreCase(StringUtils.DELETE_DATA)){
            if(obj instanceof Product){
                DatabaseAdapter.getInstance(ctx).productDao().deleteProduct(((Product) obj).getProductId());
            }
            else if(obj instanceof RawMaterial){
                DatabaseAdapter.getInstance(ctx).rawDao().deleteRaw(((RawMaterial) obj).getRawId());
            }
            return null;
        }
        else if(process.equalsIgnoreCase(StringUtils.FETCH_DATA)){
            if(obj instanceof Product){
                return DatabaseAdapter.getInstance(ctx).productDao().fetchAllProducts();
            }
            else if(obj instanceof RawMaterial){
                return DatabaseAdapter.getInstance(ctx).rawDao().fetchAllRawData();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        ctx = null;
    }
}

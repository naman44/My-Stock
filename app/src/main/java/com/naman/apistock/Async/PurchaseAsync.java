package com.naman.apistock.Async;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.naman.apistock.DatabaseAdapter;
import com.naman.apistock.Model.Purchase;
import com.naman.apistock.Screens.PurchaseActivity;

import java.lang.ref.WeakReference;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class PurchaseAsync extends AsyncTask<Void, Void, List> {

    private Purchase purchase;
    private Context mContext;
    private boolean result;

    public PurchaseAsync(Context context, Purchase purchase){
        this.purchase = purchase;
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List doInBackground(Void... voids) {
        long x = DatabaseAdapter.getInstance(mContext).purchaseDao().insertPurchase(purchase);
        if(x > 0)
            result = true;
        return null;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        if(result)
            Toast.makeText(mContext, "Success!", Toast.LENGTH_SHORT).show();
    }
}

package com.naman.apistock.Async;

import android.os.AsyncTask;

import com.naman.apistock.DatabaseAdapter;
import com.naman.apistock.Model.Item;
import com.naman.apistock.Utils.StringUtils;

import java.util.List;

public class ItemAsync extends AsyncTask<Void, Void, List> {

    private DatabaseAdapter db;
    private Item item;
    private String process;
    public ItemAsync(DatabaseAdapter db, Item item, String process){
        this.db = db;
        this.item = item;
        this.process = process;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected List doInBackground(Void... voids) {
        if(process.equalsIgnoreCase(StringUtils.INSERT_DATA)){
            db.itemDao().insertItem(item);
            return null;
        }
        else if(process.equalsIgnoreCase(StringUtils.UPDATE_DATA)){
            db.itemDao().updateItem(item);
            return null;
        }
        else if(process.equalsIgnoreCase(StringUtils.DELETE_DATA)){
            db.itemDao().deleteItems(item.getItemId());
            return null;
        }
        else if(process.equalsIgnoreCase(StringUtils.FETCH_DATA)){
            return db.itemDao().fetchItemsByType(item.getItemType());
        }
        else if(process.equalsIgnoreCase(StringUtils.FETCH_NAME)){
            return db.itemDao().fetchItemsByType(item.getItemType());
        }
        return null;
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
    }
}

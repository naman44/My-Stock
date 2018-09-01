package com.naman.apistock;

import android.content.Context;

import com.naman.apistock.DAO.InventoryDao;
import com.naman.apistock.DAO.ItemDao;
import com.naman.apistock.DAO.PartnerDao;
import com.naman.apistock.DAO.PurchaseDao;
import com.naman.apistock.Model.Inventory;
import com.naman.apistock.Model.Item;
import com.naman.apistock.Model.Partner;
import com.naman.apistock.Model.Purchase;
import com.naman.apistock.Utils.DateConverter;
import com.naman.apistock.Utils.ItemListConverter;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Item.class, Partner.class, Inventory.class, Purchase.class}, version = 1)
@TypeConverters({ItemListConverter.class})
public abstract class DatabaseAdapter extends RoomDatabase {

    private static DatabaseAdapter dbAdapter;
    public abstract ItemDao itemDao();
    public abstract InventoryDao inventoryDao();
    public abstract PartnerDao partnerDao();
    public abstract PurchaseDao purchaseDao();

    public static DatabaseAdapter getInstance(Context context){
        if(dbAdapter == null){
            synchronized (DatabaseAdapter.class){
                dbAdapter = Room.databaseBuilder(context,
                        DatabaseAdapter.class, context.getString(R.string.db_title)).fallbackToDestructiveMigration().build();
            }
        }
        return dbAdapter;
    }
}

package com.naman.apistock;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.naman.apistock.Model.Inventory;
import com.naman.apistock.Model.Product;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

public class HomeFragment extends Fragment {

    private DatabaseAdapter dbAdapter;
    private Button btn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        dbAdapter = Room.databaseBuilder(getContext(),
                DatabaseAdapter.class, "sample-db").build();
        btn = v.findViewById(R.id.click);
        btn.setOnClickListener((View vi)->{
            new DbAsync().execute();
        });

        return v;
    }

    private class DbAsync extends AsyncTask<Void, Void, Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Void... voids) {
//            Product product = new Product();
//            product.setName("Imida");
//            long id = dbAdapter.productDao().insertProduct(product);
//            Inventory inv = new Inventory();
//
//            inv.setProductId((int)id);
//            inv.setQuantity(2000);
//            dbAdapter.inventoryDao().insertInventory(inv);
            Inventory inv = dbAdapter.inventoryDao().fetchProductInventory();
            return inv.getQuantity();
        }

        @Override
        protected void onPostExecute(Integer aVoid) {
            super.onPostExecute(aVoid);
            btn.setText(aVoid+"");
        }
    }
}

package com.naman.apistock.Screens;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputEditText;
import com.naman.apistock.Model.Product;
import com.naman.apistock.ProductListAdapter;
import com.naman.apistock.R;
import com.naman.apistock.RetrieveListAsync;

import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    Spinner typeSpin;
    View productInclude, rawInclude;
    TextInputEditText name, brandName, weight, neck, color, grade, maker;
    RecyclerView itemListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productInclude = findViewById(R.id.include_product);
        rawInclude = findViewById(R.id.include_raw);
        itemListView = findViewById(R.id.recycler_add_item);
        typeSpin = findViewById(R.id.spinner_item_type);
        typeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType = parent.getItemAtPosition(position).toString();
                if(selectedType.equalsIgnoreCase(getString(R.string.item_type_product))){
                    productInclude.setVisibility(View.VISIBLE);
                    rawInclude.setVisibility(View.GONE);
                    initProductLayout();
                }
                else{
                    rawInclude.setVisibility(View.VISIBLE);
                    productInclude.setVisibility(View.GONE);
                    initRawLayout();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener((View view) -> {
                //TODO: save data
        });

        LinearLayoutManager layoutRv = new LinearLayoutManager(this);
        layoutRv.setOrientation(RecyclerView.VERTICAL);
        itemListView.setLayoutManager(layoutRv);
        itemListView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
    }

    @Override
    protected void onResume() {
        super.onResume();
        RetrieveListAsync ret = new RetrieveListAsync(this, getString(R.string.item_type_product));
        try{
            List productList = ret.execute().get();
            ProductListAdapter adapter = new ProductListAdapter(this, productList);
            itemListView.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initProductLayout(){
        brandName = findViewById(R.id.add_item_brand_text);
        weight = findViewById(R.id.add_item_weight_text);
        neck = findViewById(R.id.add_item_neck_text);
        color = findViewById(R.id.add_item_color_text);
    }

    private void initRawLayout(){
        grade = findViewById(R.id.add_item_grade_text);
        maker = findViewById(R.id.add_item_maker_text);
    }

}

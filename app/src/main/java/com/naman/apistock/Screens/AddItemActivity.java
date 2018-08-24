package com.naman.apistock.Screens;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.naman.apistock.InsertItemAsync;
import com.naman.apistock.Model.Product;
import com.naman.apistock.Model.RawMaterial;
import com.naman.apistock.ProductListAdapter;
import com.naman.apistock.R;
import com.naman.apistock.RetrieveListAsync;
import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity {

    Spinner typeSpin, materialSpin;
    View productInclude, rawInclude;
    TextInputEditText name, brandName, weight, neck, color, grade, maker;
    RecyclerView itemListView;
    ProductListAdapter adapter;
    List productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        name = findViewById(R.id.add_item_name_text);
        productInclude = findViewById(R.id.include_product);
        rawInclude = findViewById(R.id.include_raw);
        itemListView = findViewById(R.id.recycler_add_item);
        materialSpin = findViewById(R.id.spinner_product_type);
        typeSpin = findViewById(R.id.spinner_item_type);
        typeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                retrieveListAndDisplay();
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
            // insert product
            if(typeSpin.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.item_type_product))){
                insertProduct();
            }
            else{
                insertRaw();
            }
        });

        LinearLayoutManager layoutRv = new LinearLayoutManager(this);
        layoutRv.setOrientation(RecyclerView.VERTICAL);
        itemListView.setLayoutManager(layoutRv);
        itemListView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        productList = new ArrayList();
        adapter = new ProductListAdapter(this, productList);
        itemListView.setAdapter(adapter);
    }

    private void insertRaw(){
        RawMaterial raw = new RawMaterial();
        raw.setName(name.getText().toString());
        raw.setType(materialSpin.getSelectedItem().toString());
        raw.setGrade(grade.getText().toString());
        raw.setManufacturer(maker.getText().toString());
        try{
            long result = new InsertItemAsync(this, raw).execute().get();
            if(result > 0)
                Toast.makeText(this, "Value inserted!", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Issue in Insert!", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertProduct(){
        Product product = new Product();
        product.setName(name.getText().toString());
        if(!weight.getText().toString().isEmpty())
            product.setWeight(Double.parseDouble(weight.getText().toString()));
        if(!neck.getText().toString().isEmpty())
            product.setNeckSize(Double.parseDouble(neck.getText().toString()));
        product.setColor(color.getText().toString());
        product.setType(materialSpin.getSelectedItem().toString());
        product.setBrandName(brandName.getText().toString());
        try{
            long result = new InsertItemAsync(this, product).execute().get();
            if(result > 0)
                Toast.makeText(this, "Value inserted!", Toast.LENGTH_SHORT).show();
            retrieveListAndDisplay();
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "Issue in Insert!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        retrieveListAndDisplay();
    }

    private void retrieveListAndDisplay(){
        RetrieveListAsync ret;
        if(typeSpin.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.item_type_product))){
            ret = new RetrieveListAsync(this, getString(R.string.item_type_product));
        }
        else{
            ret = new RetrieveListAsync(this, getString(R.string.item_type_raw));
        }
        try{
            productList = ret.execute().get();
            adapter.updateList(productList);
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

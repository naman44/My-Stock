package com.naman.apistock.Screens;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.transition.TransitionManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.android.material.textfield.TextInputEditText;
import com.naman.apistock.DeleteItemAsync;
import com.naman.apistock.InsertItemAsync;
import com.naman.apistock.ItemAsync;
import com.naman.apistock.Model.Product;
import com.naman.apistock.Model.RawMaterial;
import com.naman.apistock.ProductListAdapter;
import com.naman.apistock.ProductListViewHolder;
import com.naman.apistock.R;
import com.naman.apistock.RetrieveListAsync;
import com.naman.apistock.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class AddItemActivity extends AppCompatActivity implements ProductListViewHolder.ItemClickListener {

    Spinner typeSpin, materialSpin;
    View productInclude, rawInclude;
    TextInputEditText name, brandName, weight, neck, color, grade, maker;
    RecyclerView itemListView;
    ProductListAdapter adapter;
    List productList;
    LinearLayout layout;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initLayout();
        initRecyclerAdapter();
        initListeners();
    }

    /*
     * contains listeners for touch and selection
     */
    private void initListeners(){
        setFabReset();
        typeSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TransitionManager.beginDelayedTransition(layout);
                performDbTask(null, StringUtils.FETCH_DATA);
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

    /*
     * initialise and set empty list to adapter which is updated later
     */
    private void initRecyclerAdapter(){
        LinearLayoutManager layoutRv = new LinearLayoutManager(this);
        layoutRv.setOrientation(RecyclerView.VERTICAL);
        itemListView.setLayoutManager(layoutRv);
        itemListView.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));
        productList = new ArrayList();
        adapter = new ProductListAdapter(this, productList, this);
        itemListView.setAdapter(adapter);
    }

    /*
     * initialise layout views for global settings
     */
    private void initLayout(){
        fab = findViewById(R.id.fab);
        name = findViewById(R.id.add_item_name_text);
        productInclude = findViewById(R.id.include_product);
        rawInclude = findViewById(R.id.include_raw);
        itemListView = findViewById(R.id.recycler_add_item);
        materialSpin = findViewById(R.id.spinner_product_type);
        layout = findViewById(R.id.layout_head_add_item);
        typeSpin = findViewById(R.id.spinner_item_type);
    }

    private RawMaterial returnRawFromPage(int id){
        RawMaterial raw = new RawMaterial();
        if(id != 0){
            raw.setRawId(id);
        }
        if(name.getText() != null)
            raw.setName(name.getText().toString());
        raw.setType(materialSpin.getSelectedItem().toString());
        if(grade.getText() != null)
            raw.setGrade(grade.getText().toString());
        if(maker.getText() != null)
            raw.setManufacturer(maker.getText().toString());
        return raw;
    }

    private Product returnProductFromPage(int id){
        Product product = new Product();
        if(id != 0){
            product.setProductId(id);
        }
        if(name.getText() != null)
            product.setName(name.getText().toString());
        if(weight.getText() != null && !weight.getText().toString().isEmpty())
            product.setWeight(Double.parseDouble(weight.getText().toString()));
        if(neck.getText() != null && !neck.getText().toString().isEmpty())
            product.setNeckSize(Double.parseDouble(neck.getText().toString()));
        if(color.getText() != null)
            product.setColor(color.getText().toString());
        product.setType(materialSpin.getSelectedItem().toString());
        if(brandName.getText() != null)
            product.setBrandName(brandName.getText().toString());
        return product;
    }

    @Override
    protected void onResume() {
        super.onResume();
        performDbTask(null, StringUtils.FETCH_DATA);
    }

    private void performDbTask(Object obj, String process) {
        ItemAsync asyncObj;
        asyncObj = new ItemAsync(this, obj, process);
        try{
            if(process.equalsIgnoreCase(StringUtils.FETCH_DATA)){
                if(typeSpin.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.item_type_product))){
                    asyncObj = new ItemAsync(this, new Product(), process);
                }
                else{
                    asyncObj = new ItemAsync(this, new RawMaterial(), process);
                }
                productList = asyncObj.execute().get();
                adapter.updateList(productList);
            }
            else{
                asyncObj.execute();
                performDbTask(null, StringUtils.FETCH_DATA);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onEditClicked(Object obj) {
        if(obj instanceof Product){
            fillValuesToLayout((Product) obj);
            fab.setOnClickListener((View v)->{
                performDbTask(returnProductFromPage(((Product) obj).getProductId()),StringUtils.UPDATE_DATA);
                resetLayout();
            });
        }
        else if(obj instanceof RawMaterial){
            fillValuesToLayout((RawMaterial) obj);
            fab.setOnClickListener((View v)-> {
                performDbTask(returnRawFromPage(((RawMaterial) obj).getRawId()) ,StringUtils.UPDATE_DATA);
                resetLayout();
            });
        }
    }

    @Override
    public void onDeleteClicked(Object obj) {
        performDbTask(obj, StringUtils.DELETE_DATA);
    }

    private void fillValuesToLayout(Product p){
        name.setText(p.getName());
        brandName.setText(p.getBrandName());
        materialSpin.setSelection(getIndex(materialSpin, p.getType()));
        weight.setText(getString(R.string.double_string, p.getWeight()));
        neck.setText(getString(R.string.double_string, p.getNeckSize()));
        color.setText(p.getColor());
    }

    private void fillValuesToLayout(RawMaterial r){
        name.setText(r.getName());
        materialSpin.setSelection(getIndex(materialSpin, r.getType()));
        grade.setText(r.getGrade());
        maker.setText(r.getManufacturer());
    }

    private int getIndex(Spinner spin, String value){
        for(int i=0; i<spin.getCount(); i++){
            if(spin.getItemAtPosition(i).toString().equalsIgnoreCase(value)){
                return i;
            }
        }
        return -1;
    }

    private void resetLayout(){
        name.setText("");
        materialSpin.setSelection(0);
        setFabReset();
        if(typeSpin.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.item_type_product))){
            brandName.setText("");
            weight.setText("");
            neck.setText("");
            color.setText("");
        }
        else{
            grade.setText("");
            maker.setText("");
        }
    }

    private void setFabReset(){
        fab.setOnClickListener((View view) -> {
            if(typeSpin.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.item_type_product))){
                performDbTask(returnProductFromPage(0), StringUtils.INSERT_DATA);
            }
            else{
                performDbTask(returnRawFromPage(0), StringUtils.INSERT_DATA);
            }
            resetLayout();
        });
    }
}

package com.naman.apistock.Screens;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import com.google.android.material.textfield.TextInputEditText;
import com.naman.apistock.Utils.AppUtil;
import com.naman.apistock.DatabaseAdapter;
import com.naman.apistock.Async.ItemAsync;
import com.naman.apistock.Model.Item;
import com.naman.apistock.Adapter.ProductListAdapter;
import com.naman.apistock.Adapter.ProductListViewHolder;
import com.naman.apistock.R;
import com.naman.apistock.Utils.StringUtils;

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
        color = findViewById(R.id.add_item_color_text);
    }

    private Item createItemFromPage(int id){
        Item item = new Item();
        if(id != 0)
            item.setItemId(id);
        if(name.getText() != null)
            item.setName(name.getText().toString());
        if(color.getText() != null)
            item.setColor(color.getText().toString());
        item.setItemType(typeSpin.getSelectedItem().toString());
        item.setPlasticType(materialSpin.getSelectedItem().toString());

        // product - raw
        if(item.getItemType().equalsIgnoreCase(getString(R.string.item_type_product))){
            if(brandName.getText() != null)
                item.setBrandName(brandName.getText().toString());
            if(weight.getText() != null && !weight.getText().toString().isEmpty())
                item.setWeight(Double.parseDouble(weight.getText().toString()));
            if(neck.getText() != null && !neck.getText().toString().isEmpty())
                item.setNeckSize(Double.parseDouble(neck.getText().toString()));
        }
        else{
            if(grade.getText() != null)
                item.setGrade(grade.getText().toString());
            if(maker.getText() != null)
                item.setManufacturer(maker.getText().toString());
        }
        return item;
    }

    @Override
    protected void onResume() {
        super.onResume();
        performDbTask(null, StringUtils.FETCH_DATA);
    }

    private void performDbTask(Item obj, String process) {
        ItemAsync asyncObj;
        DatabaseAdapter db = DatabaseAdapter.getInstance(this);
        asyncObj = new ItemAsync(db, obj, process);
        try{
            if(process.equalsIgnoreCase(StringUtils.FETCH_DATA)){
                Item item = new Item(typeSpin.getSelectedItem().toString());
                asyncObj = new ItemAsync(db, item, process);
                productList = asyncObj.execute().get();
                adapter.updateItemList(productList);
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
    public void onEditClicked(Object item) {
            fillValuesToLayout((Item) item);
            fab.setOnClickListener((View v)->{
                performDbTask(createItemFromPage(((Item)item).getItemId()),StringUtils.UPDATE_DATA);
                resetLayout();
            });
    }

    @Override
    public void onDeleteClicked(Object obj) {
        performDbTask((Item) obj, StringUtils.DELETE_DATA);
    }

    private void fillValuesToLayout(Item i){
        name.setText(i.getName());
        materialSpin.setSelection(AppUtil.getSpinIndex(materialSpin, i.getItemType()));
        color.setText(i.getColor());
        if(typeSpin.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.item_type_product))){
            brandName.setText(i.getBrandName());
            weight.setText(getString(R.string.double_string, i.getWeight()));
            neck.setText(getString(R.string.double_string, i.getNeckSize()));
        }
        else{
            grade.setText(i.getGrade());
            maker.setText(i.getManufacturer());
        }
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
        InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        fab.requestFocusFromTouch();
    }

    private void setFabReset(){
        fab.setOnClickListener((View view) -> {
            performDbTask(createItemFromPage(0), StringUtils.INSERT_DATA);
            resetLayout();
        });
    }
}

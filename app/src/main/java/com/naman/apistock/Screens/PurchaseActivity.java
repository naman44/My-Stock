package com.naman.apistock.Screens;

import android.app.DatePickerDialog;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.naman.apistock.Adapter.ItemListAdapter;
import com.naman.apistock.Async.ItemAsync;
import com.naman.apistock.Async.PartnerAsync;
import com.naman.apistock.Async.PurchaseAsync;
import com.naman.apistock.DatabaseAdapter;
import com.naman.apistock.Model.Item;
import com.naman.apistock.Model.ItemList;
import com.naman.apistock.Model.Partner;
import com.naman.apistock.Model.Purchase;
import com.naman.apistock.R;
import com.naman.apistock.Utils.AppUtil;
import com.naman.apistock.Utils.StringUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PurchaseActivity extends AppCompatActivity {

    AutoCompleteTextView partName, itemName;
    CollapsingToolbarLayout collapsibleLayout;
    List<String> partnerList, itemList;
    TextView dateText, priceWoGst, gstAmount, totalAmount;
    TextInputEditText quantity, price, invoiceNo, gstRate;
    Spinner unitType;
    FloatingActionButton fab;
    Button addProduct;
    RecyclerView rv;
    List<ItemList> listOfItems;
    ItemListAdapter adapter;
    LinearLayout layoutProduct;
    GridLayout priceLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initLayout();
        initAutoCompleteTextView();
        initRecycler();
        handlingButtonClick();
        collapsibleLayout.setTitleEnabled(false);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initLayout(){
        dateText = findViewById(R.id.purchase_date);
        quantity = findViewById(R.id.purchase_quantity_text);
        price = findViewById(R.id.purchase_price_text);
        invoiceNo = findViewById(R.id.purchase_invoice_text);
        unitType = findViewById(R.id.purchase_quantity_unit);
        partName = findViewById(R.id.purchase_partner_name);
        itemName = findViewById(R.id.purchase_item_name);
        fab = findViewById(R.id.fab);
        collapsibleLayout = findViewById(R.id.toolbar_layout);
        addProduct = findViewById(R.id.purchase_add_product_btn);
        rv = findViewById(R.id.purchase_product_recycler);
        layoutProduct = findViewById(R.id.purchase_add_product_layout);
        gstRate = findViewById(R.id.purchase_gst_text);
        partName.requestFocus();
        priceLayout = findViewById(R.id.purchase_price_layout);
        priceWoGst = findViewById(R.id.price_wo_gst);
        gstAmount = findViewById(R.id.gst_amount);
        totalAmount = findViewById(R.id.total_amount);
    }

    @SuppressWarnings("unchecked")
    private void initAutoCompleteTextView(){
        try{
            partnerList = new PartnerAsync(DatabaseAdapter.getInstance(this), new Partner(getString(R.string.partner_type_supplier)),
                    StringUtils.FETCH_NAME).execute().get();
            itemList = new ItemAsync(DatabaseAdapter.getInstance(this), new Item(getString(R.string.item_type_raw)),
                    StringUtils.FETCH_NAME).execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, partnerList);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, itemList);
        partName.setAdapter(adapter);
        itemName.setAdapter(adapter1);
    }

    private void initRecycler(){
        LinearLayoutManager layoutRv = new LinearLayoutManager(this);
        layoutRv.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(layoutRv);
        listOfItems = new ArrayList<>();
        adapter = new ItemListAdapter(this, listOfItems);
        rv.setAdapter(adapter);
    }

    private void handlingButtonClick(){
        fab.setOnClickListener((View v) ->{
            if(partName.getVisibility() == View.VISIBLE){
                if(partName.getText() != null && !partName.getText().toString().isEmpty()){
                    setTitle(getString(R.string.title_purchase, partName.getText()));
                    partName.setVisibility(View.GONE);
                    layoutProduct.setVisibility(View.VISIBLE);
                }
            }
            else{
                Purchase p = new Purchase();
                p.setDate(dateText.getText().toString());
                p.setInvoiceNo(invoiceNo.getText().toString());
                p.setPartner(partName.getText().toString());
                p.setItemList(listOfItems);
                try{
                    new PurchaseAsync(this, p).execute();
                }catch (Exception e){
                    e.printStackTrace();
                }
                finish();
            }
            InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            fab.requestFocusFromTouch();
        });
        addProduct.setOnClickListener((View v)->{
            if(!itemName.getText().toString().isEmpty()){
                if(!initErrorsOnText()){
                    TransitionManager.beginDelayedTransition(layoutProduct);
                    listOfItems = createProductList();
                    adapter.updateList(listOfItems);
                    updatePriceLayout();
                    resetLayout();
                }
            }
            InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            addProduct.requestFocusFromTouch();
        });
        dateText.setOnClickListener((View v)->{
            Calendar cal = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (DatePicker datePicker, int year, int month, int day)->{
                cal.set(year, month,day);
                dateText.setText(AppUtil.formatDate(cal.getTime()));
            },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });

    }

    private void updatePriceLayout(){
        if(priceLayout.getVisibility() != View.VISIBLE){
            priceLayout.setVisibility(View.VISIBLE);
        }
        double gstAmount = 0;
        double price = 0;
        for(ItemList i : listOfItems){
            double itemcost = (i.getPrice()*i.getQuantity());
            double gstApplied = (itemcost*i.getGstRate())/100;
           price += itemcost;
           gstAmount += gstApplied;
        }
        double total = price + gstAmount;
        priceWoGst.setText(getString(R.string.display_price, price));
        this.gstAmount.setText(getString(R.string.display_price, gstAmount));
        totalAmount.setText(getString(R.string.display_price, total));
    }

    private boolean initErrorsOnText(){
        if((quantity.getText() != null && quantity.getText().toString().isEmpty())) {
            quantity.setError("Mandatory");
            return true;
        } else
            quantity.setError(null);
        if(price.getText() != null && price.getText().toString().isEmpty()){
            price.setError("Mandatory");
            quantity.setError(null);
            return true;
        }else price.setError(null);
        return false;
    }

    private List<ItemList> createProductList(){
        ItemList item = new ItemList();
        List<ItemList> list = new ArrayList<>(listOfItems);
        item.setItem(itemName.getText().toString());
        if(quantity.getText() != null && !quantity.getText().toString().isEmpty())
            item.setQuantity(Integer.parseInt(quantity.getText().toString()));
        item.setUnit(unitType.getSelectedItem().toString());
        if(price.getText() != null && !price.getText().toString().isEmpty())
            item.setPrice(Double.parseDouble(price.getText().toString()));
        if(gstRate.getText() != null && !gstRate.getText().toString().isEmpty())
            item.setGstRate(Double.parseDouble(gstRate.getText().toString()));
        list.add(item);
        return list;
    }

    private void resetLayout(){
        quantity.setText("");
        price.setText("");
        invoiceNo.setText("");
        itemName.setText("");
    }
}

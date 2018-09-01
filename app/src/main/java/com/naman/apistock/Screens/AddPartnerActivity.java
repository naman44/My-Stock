package com.naman.apistock.Screens;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.naman.apistock.Utils.AppUtil;
import com.naman.apistock.DatabaseAdapter;
import com.naman.apistock.Model.Partner;
import com.naman.apistock.Async.PartnerAsync;
import com.naman.apistock.Adapter.ProductListAdapter;
import com.naman.apistock.Adapter.ProductListViewHolder;
import com.naman.apistock.R;
import com.naman.apistock.Utils.StringUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;

public class AddPartnerActivity extends AppCompatActivity implements ProductListViewHolder.ItemClickListener {

    TextInputEditText name, owner, address, gst, phone;
    Spinner typeSpin;
    RecyclerView rv;
    ProductListAdapter adapter;
    List partnerList;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_partner);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initLayout();
        initRecycler();
        initFabButton();
    }

    private void initLayout(){
        name = findViewById(R.id.add_partner_name_text);
        owner = findViewById(R.id.add_partner_owner_text);
        address = findViewById(R.id.add_partner_address_text);
        gst = findViewById(R.id.add_partner_gst_text);
        phone = findViewById(R.id.add_partner_contact_text);
        typeSpin = findViewById(R.id.spinner_partner_type);
        rv = findViewById(R.id.recycler_add_partner);
    }

    private void initRecycler(){
        LinearLayoutManager layoutRv = new LinearLayoutManager(this);
        layoutRv.setOrientation(RecyclerView.VERTICAL);
        rv.setLayoutManager(layoutRv);
        partnerList = new ArrayList();
        adapter = new ProductListAdapter(this, partnerList, this);
        rv.setAdapter(adapter);
    }

    private void initFabButton(){
        fab.setOnClickListener((View view) -> performDbTask(createPartnerFromScreen(0), StringUtils.INSERT_DATA));
    }

    @Override
    protected void onResume() {
        super.onResume();
        performDbTask(null, StringUtils.FETCH_DATA);
    }

    @Override
    public void onEditClicked(Object obj) {
        if(obj instanceof Partner){
            Partner p = (Partner) obj;
            fillValuesToPage(p);
            fab.setOnClickListener((View v) -> performDbTask(createPartnerFromScreen(p.getId()), StringUtils.UPDATE_DATA));
        }
    }

    @Override
    public void onDeleteClicked(Object obj) {
        if(obj instanceof Partner){
            Partner p = (Partner) obj;
            performDbTask(p, StringUtils.DELETE_DATA);
        }
    }

    private void performDbTask(Partner obj, String process) {
        PartnerAsync asyncObj;
        DatabaseAdapter db = DatabaseAdapter.getInstance(this);
        asyncObj = new PartnerAsync(db, obj, process);
        try{
            if(process.equalsIgnoreCase(StringUtils.FETCH_DATA)){
                Partner partner = new Partner(typeSpin.getSelectedItem().toString());
                asyncObj = new PartnerAsync(db, partner, process);
                partnerList = asyncObj.execute().get();
                adapter.updatePartnerList(partnerList);
            }
            else{
                asyncObj.execute();
                performDbTask(null, StringUtils.FETCH_DATA);
                resetPage();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Partner createPartnerFromScreen(int id){
        Partner partner = new Partner(typeSpin.getSelectedItem().toString());
        if(id != 0){
            partner.setId(id);
        }
        if(name.getText() != null)
            partner.setName(name.getText().toString());
        if(owner.getText() != null)
            partner.setOwner(owner.getText().toString());
        if(address.getText() != null)
            partner.setAddress(address.getText().toString());
        if(gst.getText() != null)
            partner.setGstNo(gst.getText().toString());
        if(phone.getText() != null && !phone.getText().toString().isEmpty())
            partner.setContactNo(Long.parseLong(phone.getText().toString()));
        return partner;
    }

    private void fillValuesToPage(Partner p){
        name.setText(p.getName());
        owner.setText(p.getOwner());
        typeSpin.setSelection(AppUtil.getSpinIndex(typeSpin, p.getType()));
        address.setText(p.getAddress());
        gst.setText(p.getGstNo());
        phone.setText(getString(R.string.display_phone, p.getContactNo()));
    }

    private void resetPage(){
        name.setText("");
        owner.setText("");
        address.setText("");
        gst.setText("");
        phone.setText("");
        initFabButton();
        InputMethodManager im = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        fab.requestFocusFromTouch();
    }

}

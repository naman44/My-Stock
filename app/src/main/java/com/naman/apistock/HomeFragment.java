package com.naman.apistock;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.naman.apistock.Screens.AddPartnerActivity;
import com.naman.apistock.Screens.PurchaseActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private DatabaseAdapter dbAdapter;
    private Button partner, purchase;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        partner = v.findViewById(R.id.partner);
        purchase = v.findViewById(R.id.purchase);
        partner.setOnClickListener((View vi)->{
            startActivity(new Intent(getActivity(), AddPartnerActivity.class));
        });
        purchase.setOnClickListener((View vi)->{
            startActivity(new Intent(getActivity(), PurchaseActivity.class));
        });
        return v;
    }
}

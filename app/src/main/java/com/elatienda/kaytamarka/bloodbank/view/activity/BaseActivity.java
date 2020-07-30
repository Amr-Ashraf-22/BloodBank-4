package com.elatienda.kaytamarka.bloodbank.view.activity;


import androidx.appcompat.app.AppCompatActivity;

import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;

public class BaseActivity extends AppCompatActivity {

    public BaseFragment baseFragment;
    
    public void superBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        baseFragment.onBack();
    }
}

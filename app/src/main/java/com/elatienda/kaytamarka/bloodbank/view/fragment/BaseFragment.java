package com.elatienda.kaytamarka.bloodbank.view.fragment;


import androidx.fragment.app.Fragment;

import com.elatienda.kaytamarka.bloodbank.view.activity.BaseActivity;

public class BaseFragment extends Fragment {

    public BaseFragment(){
    }

    public BaseActivity baseActivity;

    public void setUpActivity() {
        baseActivity = (BaseActivity) getActivity();
        baseActivity.baseFragment = this;
    }

    public void onBack() {
        baseActivity.superBackPressed();
    }

}

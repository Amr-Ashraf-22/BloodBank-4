package com.elatienda.kaytamarka.bloodbank.view.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elatienda.kaytamarka.bloodbank.R;

import butterknife.ButterKnife;

public class BlankFragment extends BaseFragment {

    public BlankFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        setUpActivity();
        ButterKnife.bind(this,view);




        return view;
    }
}

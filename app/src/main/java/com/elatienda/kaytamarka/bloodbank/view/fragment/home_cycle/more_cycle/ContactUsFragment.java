package com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.more_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactUsFragment extends BaseFragment {


    @BindView(R.id.fragment_contact_us_toolbar)
    Toolbar fragmentContactUsToolbar;

    public ContactUsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(fragmentContactUsToolbar);
        fragmentContactUsToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        fragmentContactUsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.GONE);


        return view;
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}

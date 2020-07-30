package com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.more_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.util.HelperMethod;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutAppFragment extends BaseFragment {

    @BindView(R.id.fragment_about_app_toolbar)
    Toolbar fragmentAboutAppToolbar;

    public AboutAppFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_app, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(fragmentAboutAppToolbar);
        fragmentAboutAppToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        fragmentAboutAppToolbar.setNavigationOnClickListener(new View.OnClickListener() {
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

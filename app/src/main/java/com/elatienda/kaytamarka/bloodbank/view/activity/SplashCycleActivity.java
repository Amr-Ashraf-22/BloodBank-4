package com.elatienda.kaytamarka.bloodbank.view.activity;

import android.os.Bundle;
import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.util.HelperMethod;
import com.elatienda.kaytamarka.bloodbank.view.fragment.splash_cycle.SplashFragment;

public class SplashCycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_cycle);

        SplashFragment splashFragment = new SplashFragment();
        HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.activity_splash_cycle_fl_frame, splashFragment);

    }

}

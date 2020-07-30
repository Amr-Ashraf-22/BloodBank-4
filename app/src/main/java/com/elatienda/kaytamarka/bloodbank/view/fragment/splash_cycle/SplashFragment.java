package com.elatienda.kaytamarka.bloodbank.view.fragment.splash_cycle;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.annotation.NonNull;
import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.util.HelperMethod;
import com.elatienda.kaytamarka.bloodbank.view.activity.UserCycleActivity;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.user_cycle.LoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaeger.library.StatusBarUtil;

import java.util.Objects;

import butterknife.ButterKnife;

public class SplashFragment extends BaseFragment {

    private static final int SPLASH_DISPLAY_LENGTH = 3000;
    private static final String FIRST_RUN_FILE = "get_first_run_file";
    private static final String FIRST_START_VALUE = "get_status_of_entries";
    private boolean firstStart;

    public SplashFragment(){
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(getActivity()!=null){
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_splash, container, false);
        setUpActivity();
        StatusBarUtil.setColor(getActivity(), getResources().getColor(R.color.frag_splash_status_bar_color), 0);
        ButterKnife.bind(this, view);

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.GONE);

        SliderFragment sliderFragment = new SliderFragment();
        LoginFragment loginFragment = new LoginFragment();

        if(getActivity()!=null){
            SharedPreferences firstRunPreferences = getActivity().getSharedPreferences(FIRST_RUN_FILE, Context.MODE_PRIVATE);
            firstStart = firstRunPreferences.getBoolean(FIRST_START_VALUE,true);
        }

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                if(firstStart){
                    showSliderForOnce();
                    if(getActivity()!=null){
                        HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_splash_cycle_fl_frame, sliderFragment);
                    }
                }else{
                    startActivity(new Intent(getActivity(), UserCycleActivity.class));
                }
            }
        }, SPLASH_DISPLAY_LENGTH);

        return view;
    }

    private void showSliderForOnce(){
        if (getActivity()!=null){
            getActivity().getSharedPreferences(FIRST_RUN_FILE, Context.MODE_PRIVATE)
                    .edit()
                    .putBoolean(FIRST_START_VALUE,false)
                    .apply();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    @Override
    public void onBack() {
        if(getActivity()!=null){
            getActivity().moveTaskToBack(true);
            getActivity().finish();
        }
    }
}

package com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.more_cycle;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.util.HelperMethod;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.HomeFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.notification_cycle.NotificationSettingsFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.post_cycle.FavoriteFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.user_cycle.LoginFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MoreFragment extends BaseFragment {

    @BindView(R.id.fragment_more_toolbar)
    Toolbar fragmentMoreToolbar;

    public MoreFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(fragmentMoreToolbar);

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.VISIBLE);

        return view;
    }

    @OnClick({R.id.fragment_more_ll_favorite, R.id.fragment_more_ll_contact_us, R.id.fragment_more_ll_about_us, R.id.fragment_more_ll_app_evaluation, R.id.fragment_more_ll_notification_settings, R.id.fragment_more_ll_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_more_ll_favorite:
                if (getActivity() != null) {
                    FavoriteFragment favoriteFragment = new FavoriteFragment();
                    HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_home_cycle_fl_container, favoriteFragment);
                }
                break;
            case R.id.fragment_more_ll_contact_us:
                if (getActivity() != null) {
                    ContactUsFragment contactUsFragment = new ContactUsFragment();
                    HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_home_cycle_fl_container, contactUsFragment);
                }
                break;
            case R.id.fragment_more_ll_about_us:
                if (getActivity() != null) {
                    AboutAppFragment aboutAppFragment = new AboutAppFragment();
                    HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_home_cycle_fl_container, aboutAppFragment);
                }
                break;
            case R.id.fragment_more_ll_app_evaluation:
                break;
            case R.id.fragment_more_ll_notification_settings:
                if (getActivity() != null) {
                    NotificationSettingsFragment notificationSettingsFragment = new NotificationSettingsFragment();
                    HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_home_cycle_fl_container, notificationSettingsFragment);
                }
                break;
            case R.id.fragment_more_ll_logout:
                if (getActivity() != null) {
                    LoginFragment loginFragment = new LoginFragment();
                    HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_home_cycle_fl_container, loginFragment);
                }
                break;
        }
    }

    @Override
    public void onBack() {
        if (getActivity() != null) {
            HomeFragment homeFragment = new HomeFragment();
            HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_home_cycle_fl_container, homeFragment);
        }
    }
}

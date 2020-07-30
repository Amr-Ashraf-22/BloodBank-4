package com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.adapter.TabLayoutAdapter;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.donation_cycle.DonationRequestsFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.post_cycle.PostFragment;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends BaseFragment {

    @BindView(R.id.fragment_home_tl_tabs)
    TabLayout fragmentHomeTlTabs;
    @BindView(R.id.fragment_home_vp_pager)
    ViewPager fragmentHomeVpPager;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        addPages();

        fragmentHomeVpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Toast.makeText(getActivity(), "onPageScrolled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position) {
                //Toast.makeText(getActivity(), "onPageSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Toast.makeText(getActivity(), "onPageScrollStateChanged", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }

    private void addPages(){
        if (getActivity()!=null){                       //getActivity().getSupportFragmentManager()
            TabLayoutAdapter adapter = new TabLayoutAdapter(getChildFragmentManager());
            adapter.addPager(new DonationRequestsFragment(),getResources().getString(R.string.frag_home_tl_donations));
            adapter.addPager(new PostFragment(),getResources().getString(R.string.frag_home_tl_articles));
            fragmentHomeVpPager.setAdapter(adapter);
            fragmentHomeVpPager.setCurrentItem(2);
            fragmentHomeTlTabs.setupWithViewPager(fragmentHomeVpPager);
        }
    }

//    @Override
//    public void onBack() {
//        // empty
//    }

}

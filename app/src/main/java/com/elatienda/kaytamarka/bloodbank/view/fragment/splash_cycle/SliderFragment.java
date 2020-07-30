package com.elatienda.kaytamarka.bloodbank.view.fragment.splash_cycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;
import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.adapter.SliderAdapter;
import com.elatienda.kaytamarka.bloodbank.view.activity.UserCycleActivity;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaeger.library.StatusBarUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SliderFragment extends BaseFragment {

    @BindView(R.id.fragment_slider_vp_slider)
    ViewPager fragmentSliderVpSlider;
    @BindView(R.id.fragment_i_v_slider_circle1)
    ImageView fragmentIVSliderCircle1;
    @BindView(R.id.fragment_i_v_slider_circle2)
    ImageView fragmentIVSliderCircle2;
    @BindView(R.id.fragment_i_v_slider_circle3)
    ImageView fragmentIVSliderCircle3;
    @BindView(R.id.fragment_slider_i_btn_slider)
    ImageButton fragmentSliderIBtnSlider;

    public SliderFragment(){
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(getActivity()!=null){
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        setUpActivity();
        StatusBarUtil.setColorNoTranslucent(getActivity(),getResources().getColor(R.color.frag_slider_status_bar_color));
        ButterKnife.bind(this, view);

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.GONE);

        if(getActivity()!=null){
            SliderAdapter sliderAdapter = new SliderAdapter(getActivity());
            sliderAdapter.addPage(R.drawable.ic_slider_img_1, getString(R.string.item_slider_tv_text));
            sliderAdapter.addPage(R.drawable.ic_slider_img_2, getString(R.string.item_slider_tv_text));
            sliderAdapter.addPage(R.drawable.ic_slider_img_1, getString(R.string.item_slider_tv_text));
            fragmentSliderVpSlider.setAdapter(sliderAdapter);
        }

        fragmentSliderVpSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                switch (fragmentSliderVpSlider.getCurrentItem()) {
                    case 0:
                        fragmentIVSliderCircle1.setImageResource(R.drawable.ic_red_circle);
                        fragmentIVSliderCircle2.setImageResource(R.drawable.ic_orange_circle);
                        fragmentIVSliderCircle3.setImageResource(R.drawable.ic_orange_circle);
                        fragmentSliderIBtnSlider.setImageResource(R.drawable.ic_slider_arrow_btn);
                        break;
                    case 1:
                        fragmentIVSliderCircle1.setImageResource(R.drawable.ic_red_circle);
                        fragmentIVSliderCircle2.setImageResource(R.drawable.ic_red_circle);
                        fragmentIVSliderCircle3.setImageResource(R.drawable.ic_orange_circle);
                        fragmentSliderIBtnSlider.setImageResource(R.drawable.ic_slider_arrow_btn);
                        break;
                    case 2:
                        fragmentIVSliderCircle1.setImageResource(R.drawable.ic_red_circle);
                        fragmentIVSliderCircle2.setImageResource(R.drawable.ic_red_circle);
                        fragmentIVSliderCircle3.setImageResource(R.drawable.ic_red_circle);
                        fragmentSliderIBtnSlider.setImageResource(R.drawable.ic_slider_ok_btn);
                        break;
                    default:
                        //
                }
            }
        });

        fragmentSliderIBtnSlider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (fragmentSliderVpSlider.getCurrentItem()) {
                    case 0:
                        fragmentSliderVpSlider.setCurrentItem(1);
                        break;
                    case 1:
                        fragmentSliderVpSlider.setCurrentItem(2);
                        break;
                    case 2:
                        // Go To Next Cycle // Login Page
                        startActivity(new Intent(getActivity(), UserCycleActivity.class));
                        break;
                    default:
                        //
                }
            }
        });

        return view;
    }

    @Override
    public void onBack() {
        if(getActivity()!=null){
            getActivity().moveTaskToBack(true);
            getActivity().finish();
        }
    }

}

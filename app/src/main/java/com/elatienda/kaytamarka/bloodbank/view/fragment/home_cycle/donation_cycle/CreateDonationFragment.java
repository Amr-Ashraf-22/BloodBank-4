package com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.donation_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.adapter.SpinnerAdapter;
import com.elatienda.kaytamarka.bloodbank.util.HelperMethod;
import com.elatienda.kaytamarka.bloodbank.view.activity.BaseActivity;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.elatienda.kaytamarka.bloodbank.data.api.ApiClient.getClient;
import static com.elatienda.kaytamarka.bloodbank.util.GeneralRequest.getSpinnerData;

public class CreateDonationFragment extends BaseFragment {

    @BindView(R.id.fragment_create_donation_et_name)
    EditText fragmentCreateDonationEtName;
    @BindView(R.id.fragment_create_donation_et_age)
    EditText fragmentCreateDonationEtAge;
    @BindView(R.id.fragment_create_donation_sp_blood_type)
    Spinner fragmentCreateDonationSpBloodType;
    @BindView(R.id.fragment_create_donation_et_Num_of_bags)
    EditText fragmentCreateDonationEtNumOfBags;
    @BindView(R.id.fragment_create_donation_tv_address)
    TextView fragmentCreateDonationTvAddress;
    @BindView(R.id.fragment_create_donation_sp_governorate)
    Spinner fragmentCreateDonationSpGovernorate;
    @BindView(R.id.fragment_create_donation_sp_city)
    Spinner fragmentCreateDonationSpCity;
    @BindView(R.id.fragment_create_donation_et_phone)
    EditText fragmentCreateDonationEtPhone;
    @BindView(R.id.fragment_create_donation_et_notes)
    EditText fragmentCreateDonationEtNotes;
    @BindView(R.id.fragment_create_donation_toolbar)
    Toolbar fragmentCreateDonationToolbar;

    SpinnerAdapter bloodTypesAdater, governorateAdater, cityAdater;


    public CreateDonationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_donation, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(fragmentCreateDonationToolbar);
        fragmentCreateDonationToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        fragmentCreateDonationToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity()!=null){
                    getActivity().onBackPressed();
                }
            }
        });

        BottomNavigationView navBar = getActivity().findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.GONE);

        bloodTypesAdater = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), fragmentCreateDonationSpBloodType, bloodTypesAdater,
                getResources().getString(R.string.frag_register_sp_blood_type), getClient().getBloodTypes());

        governorateAdater = new SpinnerAdapter(getActivity());
        cityAdater = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), fragmentCreateDonationSpGovernorate, governorateAdater,
                getResources().getString(R.string.frag_register_sp_governorate), getClient().getGovernorates(),
                fragmentCreateDonationSpCity, cityAdater, getResources().getString(R.string.frag_register_sp_city),
                getClient().getCities(governorateAdater.selectedId));

        return view;
    }

    @OnClick({R.id.fragment_create_donation_img_v_location, R.id.fragment_create_donation_btn_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_create_donation_img_v_location:
                if(getActivity()!=null){
                    DonationLocationFragment donationLocationFragment = new DonationLocationFragment();
                    HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(),R.id.activity_home_cycle_fl_container,donationLocationFragment);
                }
                break;
            case R.id.fragment_create_donation_btn_send:
                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }

}

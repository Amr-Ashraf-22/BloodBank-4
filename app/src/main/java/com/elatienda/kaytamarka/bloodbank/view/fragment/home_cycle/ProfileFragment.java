package com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.adapter.SpinnerAdapter;
import com.elatienda.kaytamarka.bloodbank.data.model.login.Login;
import com.elatienda.kaytamarka.bloodbank.util.HelperMethod;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elatienda.kaytamarka.bloodbank.data.api.ApiClient.getClient;
import static com.elatienda.kaytamarka.bloodbank.util.GeneralRequest.getSpinnerData;

public class ProfileFragment extends BaseFragment {

    @BindView(R.id.fragment_profile_et_name)
    EditText fragmentProfileEtName;
    @BindView(R.id.fragment_profile_et_email)
    EditText fragmentProfileEtEmail;
    @BindView(R.id.fragment_profile_et_birth_date)
    EditText fragmentProfileEtBirthDate;
    @BindView(R.id.fragment_profile_sp_blood_type)
    Spinner fragmentProfileSpBloodType;
    @BindView(R.id.fragment_profile_et_last_donation)
    EditText fragmentProfileEtLastDonation;
    @BindView(R.id.fragment_profile_sp_governorate)
    Spinner fragmentProfileSpGovernorate;
    @BindView(R.id.fragment_profile_sp_city)
    Spinner fragmentProfileSpCity;
    @BindView(R.id.fragment_profile_et_phone)
    EditText fragmentProfileEtPhone;
    @BindView(R.id.fragment_profile_et_pass)
    EditText fragmentProfileEtPass;
    @BindView(R.id.fragment_profile_et_confirm_pass)
    EditText fragmentProfileEtConfirmPass;
    @BindView(R.id.fragment_profile_toolbar)
    Toolbar fragmentProfileToolbar;

    SpinnerAdapter bloodTypesAdater, governoratesAdapter, citiesAdapter;

    int city = 0;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(fragmentProfileToolbar);

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.VISIBLE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bloodTypesAdater = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), fragmentProfileSpBloodType, bloodTypesAdater, getString(R.string.frag_register_sp_blood_type), getClient().getBloodTypes());

        governoratesAdapter = new SpinnerAdapter(getActivity());
        citiesAdapter = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), fragmentProfileSpGovernorate, governoratesAdapter, getString(R.string.frag_register_sp_governorate), getClient().getGovernorates(),
                fragmentProfileSpCity, citiesAdapter, getString(R.string.frag_register_sp_city), getClient().getCities(governoratesAdapter.selectedId));

        initData();

    }

    private void initData() {

        getClient().getProfileData("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl")
                .enqueue(new Callback<Login>() {
                    @Override
                    public void onResponse(Call<Login> call, Response<Login> response) {
                        try {
                            if (response.body().getStatus() == 1) {

                                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
                                Date birthDate = format1.parse(response.body().getData().getClient().getBirthDate());
                                Date lastDonationDate = format1.parse(response.body().getData().getClient().getDonationLastDate());

                                fragmentProfileEtName.setText(response.body().getData().getClient().getName());
                                fragmentProfileEtEmail.setText(response.body().getData().getClient().getEmail());
                                fragmentProfileEtBirthDate.setText(format2.format(birthDate));
                                fragmentProfileSpBloodType.setSelection(response.body().getData().getClient().getBloodType().getId());
                                fragmentProfileEtLastDonation.setText(format2.format(lastDonationDate));
                                fragmentProfileSpGovernorate.setSelection(response.body().getData().getClient().getCity().getGovernorate().getId());
                                fragmentProfileSpCity.setSelection(response.body().getData().getClient().getCity().getId());
                                fragmentProfileEtPhone.setText(response.body().getData().getClient().getPhone());
                                //fragmentProfileEtPass.setText(response.body().getData().getClient().);
                                //fragmentProfileEtConfirmPass.setText(response.body().getData().getClient().getPhone());

                            } else {
                                Toast.makeText(getActivity(), "onResponse - Status 0 : \n" + response.body().getMsg(), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Login> call, Throwable t) {
                        Toast.makeText(getActivity(), "onFailure : \n" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    public void onBack() {
        if (getActivity() != null) {
            HomeFragment homeFragment = new HomeFragment();
            HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_home_cycle_fl_container, homeFragment);
        }
    }

    @OnClick(R.id.fragment_create_donation_btn_send)
    public void onViewClicked() {
    }
}

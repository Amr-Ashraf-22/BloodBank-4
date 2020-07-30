package com.elatienda.kaytamarka.bloodbank.view.fragment.user_cycle;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.adapter.SpinnerAdapter;
import com.elatienda.kaytamarka.bloodbank.data.model.login.Login;
import com.elatienda.kaytamarka.bloodbank.view.activity.HomeCycleActivity;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elatienda.kaytamarka.bloodbank.data.api.ApiClient.getClient;
import static com.elatienda.kaytamarka.bloodbank.util.GeneralRequest.getSpinnerData;
import static com.elatienda.kaytamarka.bloodbank.util.HelperMethod.disappearKeypad;

public class RegisterFragment extends BaseFragment {

    @BindView(R.id.fragment_register_et_name)
    EditText fragmentRegisterEtName;
    @BindView(R.id.fragment_register_et_email)
    EditText fragmentRegisterEtEmail;
    @BindView(R.id.fragment_register_et_birth_date)
    EditText fragmentRegisterEtBirthDate;
    @BindView(R.id.fragment_register_sp_blood_type)
    Spinner fragmentRegisterSpBloodType;
    @BindView(R.id.fragment_register_et_last_donation)
    EditText fragmentRegisterEtLastDonation;
    @BindView(R.id.fragment_register_sp_governorate)
    Spinner fragmentRegisterSpGovernorate;
    @BindView(R.id.fragment_register_sp_city)
    Spinner fragmentRegisterSpCity;
    @BindView(R.id.fragment_register_et_phone)
    EditText fragmentRegisterEtPhone;
    @BindView(R.id.fragment_register_et_password)
    EditText fragmentRegisterEtPassword;
    @BindView(R.id.fragment_register_et_confirm_password)
    EditText fragmentRegisterEtConfirmPassword;
    @BindView(R.id.fragment_register_toolbar)
    Toolbar fragmentRegisterToolbar;
    private DatePickerDialog.OnDateSetListener mPickBirthDate, mPickLastDonationDate;
    private SpinnerAdapter bloodTypesAdater, governoratesAdapter, citiesAdapter;

    public RegisterFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(fragmentRegisterToolbar);

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.GONE);

        fragmentRegisterSpCity.setEnabled(false);

        bloodTypesAdater = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), fragmentRegisterSpBloodType, bloodTypesAdater, getString(R.string.frag_register_sp_blood_type), getClient().getBloodTypes());


        governoratesAdapter = new SpinnerAdapter(getActivity());
        citiesAdapter = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), fragmentRegisterSpGovernorate, governoratesAdapter, getString(R.string.frag_register_sp_governorate), getClient().getGovernorates(),
                fragmentRegisterSpCity, citiesAdapter, getString(R.string.frag_register_sp_city), getClient().getCities(governoratesAdapter.selectedId));

        mPickBirthDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String monthString = String.valueOf(month);
                String dayString = String.valueOf(dayOfMonth);
                if (monthString.length() == 1) {
                    monthString = "0" + monthString;
                }
                if (dayString.length() == 1) {
                    dayString = "0" + dayString;
                }
                String date = year + "-" + monthString + "-" + dayString;
                fragmentRegisterEtBirthDate.setText(date);
            }
        };

        mPickLastDonationDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String monthString = String.valueOf(month);
                String dayString = String.valueOf(dayOfMonth);
                if (monthString.length() == 1) {
                    monthString = "0" + monthString;
                }
                if (dayString.length() == 1) {
                    dayString = "0" + dayString;
                }
                String date = year + "-" + monthString + "-" + dayString;
                fragmentRegisterEtLastDonation.setText(date);
            }
        };


        return view;
    }

    @OnClick({R.id.fragment_register_et_birth_date, R.id.fragment_register_et_last_donation, R.id.fragment_register_btn_register})
    public void onViewClicked(View view) {

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        switch (view.getId()) {
            case R.id.fragment_register_et_birth_date:
                if (getActivity() != null) {
                    DatePickerDialog birthDateDialog = new DatePickerDialog(
                            getActivity(),
                            AlertDialog.THEME_HOLO_LIGHT,
                            mPickBirthDate,
                            year, month, day);
                    Objects.requireNonNull(birthDateDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    DatePicker birthDatePicker = birthDateDialog.getDatePicker();
                    birthDatePicker.setMaxDate(calendar.getTimeInMillis());//set the current day as the max date
                    birthDateDialog.show();
                }
                break;
            case R.id.fragment_register_et_last_donation:
                if (getActivity() != null) {
                    DatePickerDialog lastDonationDialog = new DatePickerDialog(
                            getActivity(),
                            AlertDialog.THEME_HOLO_LIGHT,
                            mPickLastDonationDate,
                            year, month, day);
                    Objects.requireNonNull(lastDonationDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    DatePicker donationDatePicker = lastDonationDialog.getDatePicker();
                    donationDatePicker.setMaxDate(calendar.getTimeInMillis());//set the current day as the max date
                    lastDonationDialog.show();
                }
                break;
            case R.id.fragment_register_btn_register:

                disappearKeypad(getActivity(), view);

                // Check if any field is empty
                if (fragmentRegisterEtName.getText().toString().isEmpty() ||
                        fragmentRegisterEtEmail.getText().toString().isEmpty() ||
                        fragmentRegisterEtBirthDate.getText().toString().isEmpty() ||
                        (fragmentRegisterSpBloodType.getSelectedItemPosition() == 0) ||
                        fragmentRegisterEtLastDonation.getText().toString().isEmpty() ||
                        (fragmentRegisterSpCity.getSelectedItemPosition() == 0) ||
                        fragmentRegisterEtPhone.getText().toString().isEmpty() ||
                        fragmentRegisterEtPassword.getText().toString().isEmpty() ||
                        fragmentRegisterEtConfirmPassword.getText().toString().isEmpty()) {
                    // Clear LoginData from all Fields
                    clearEditTextFields();
                    // Set Error For every Field
                    if (fragmentRegisterEtName.getText().toString().isEmpty()) {
                        fragmentRegisterEtName.setError(getString(R.string.frag_register_et_empty_name));
                    }
                    if (fragmentRegisterEtEmail.getText().toString().isEmpty()) {
                        fragmentRegisterEtEmail.setError(getString(R.string.frag_register_et_empty_email));
                    }
                    if (fragmentRegisterEtBirthDate.getText().toString().isEmpty()) {
                        fragmentRegisterEtBirthDate.setError(getString(R.string.frag_register_et_empty_birth_date));
                    }
                    if ((fragmentRegisterSpBloodType.getSelectedItemPosition() == 0)) {
                        Toast.makeText(getActivity(), getString(R.string.frag_register_sp_empty_blood_type), Toast.LENGTH_SHORT).show();
                    }
                    if (fragmentRegisterEtLastDonation.getText().toString().isEmpty()) {
                        fragmentRegisterEtLastDonation.setError(getString(R.string.frag_register_et_empty_last_donation));
                    }
                    if ((fragmentRegisterSpCity.getSelectedItemPosition() == 0)) {
                        Toast.makeText(getActivity(), getString(R.string.frag_register_sp_empty_city), Toast.LENGTH_SHORT).show();
                    }
                    if (fragmentRegisterEtPhone.getText().toString().isEmpty()) {
                        fragmentRegisterEtPhone.setError(getString(R.string.frag_register_et_empty_phone));
                    }
                    if (fragmentRegisterEtPassword.getText().toString().isEmpty()) {
                        fragmentRegisterEtPassword.setError(getString(R.string.frag_register_et_empty_password));
                    }
                    if (fragmentRegisterEtConfirmPassword.getText().toString().isEmpty()) {
                        fragmentRegisterEtConfirmPassword.setError(getString(R.string.frag_register_et_empty_confirm_password));
                    }

                } else {
                    // Check if phone number equal 11 number
                    if (!(fragmentRegisterEtPhone.getText().toString().length() == 11)) {
                        fragmentRegisterEtPhone.setError(getString(R.string.frag_register_phone_not_valid));
                    } else {
                        // Check if passwords are equal or not
                        if (!fragmentRegisterEtPassword.getText().toString().equalsIgnoreCase(fragmentRegisterEtConfirmPassword.getText().toString())) {
                            // Clear Password Fields
                            fragmentRegisterEtPassword.setText("");
                            fragmentRegisterEtConfirmPassword.setText("");
                            // Set Error To Password Fields
                            fragmentRegisterEtPassword.setError(getString(R.string.frag_register_password_not_match));
                            fragmentRegisterEtConfirmPassword.setError(getString(R.string.frag_register_password_not_match));
                        } else {
                            // HERE Code For Save RegisterData And Go To Home
                            setRegisterData();
                        }
                    }
                }
                break;
        }
    }

    // Push ResetPasswordData
    private void setRegisterData() {
        String registerName = fragmentRegisterEtName.getText().toString().trim();
        String registerEmail = fragmentRegisterEtEmail.getText().toString().trim();
        String registerBirthDate = fragmentRegisterEtBirthDate.getText().toString().trim();
        int registerCityId = (int) fragmentRegisterSpCity.getSelectedItemPosition();
        String registerPhone = fragmentRegisterEtPhone.getText().toString().trim();
        String registerDonationLastDate = fragmentRegisterEtLastDonation.getText().toString().trim();
        String registerPassword = fragmentRegisterEtPassword.getText().toString().trim();
        String registerPasswordConfirmation = fragmentRegisterEtConfirmPassword.getText().toString().trim();
        int registerBloodTypeId = (int) fragmentRegisterSpBloodType.getSelectedItemPosition();
        getClient().onRegister(registerName, registerEmail, registerBirthDate, registerCityId, registerPhone,
                registerDonationLastDate, registerPassword, registerPasswordConfirmation, registerBloodTypeId).enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                assert response.body() != null;
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), "Registered Successfully", Toast.LENGTH_LONG).show();
                        // Go To Home Page
                        startActivity(new Intent(getActivity(), HomeCycleActivity.class));
                    } else {
                        Toast.makeText(getActivity(), "onResponse - Status 0 : \n" + response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Exception : \n" + e.getMessage() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "onFailure : \n" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // Clear Fields
    private void clearEditTextFields() {
        fragmentRegisterEtName.setText("");
        fragmentRegisterEtEmail.setText("");
        fragmentRegisterEtBirthDate.setText("");
        fragmentRegisterSpBloodType.setSelection(0);
        fragmentRegisterEtLastDonation.setText("");
        fragmentRegisterSpGovernorate.setSelection(0);
        fragmentRegisterSpCity.setSelection(0);
        fragmentRegisterEtPhone.setText("");
        fragmentRegisterEtPassword.setText("");
        fragmentRegisterEtConfirmPassword.setText("");
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}

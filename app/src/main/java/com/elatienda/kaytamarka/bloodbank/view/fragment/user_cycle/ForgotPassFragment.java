package com.elatienda.kaytamarka.bloodbank.view.fragment.user_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.data.model.reset_password.ResetPassword;
import com.elatienda.kaytamarka.bloodbank.util.HelperMethod;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.elatienda.kaytamarka.bloodbank.data.api.ApiClient.getClient;
import static com.elatienda.kaytamarka.bloodbank.util.HelperMethod.disappearKeypad;

public class ForgotPassFragment extends BaseFragment {

    @BindView(R.id.fragment_forgot_pass_et_phone)
    EditText fragmentForgotPassEtPhone;

    static String phone;
    public static int pubCode;
    private String userEmail ;

    public ForgotPassFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_pass, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.GONE);

        return view;
    }

    @OnClick(R.id.fragment_forgot_pass_btn_login)
    public void onViewClicked(View view) {
        disappearKeypad(getActivity(),view);
        // check if phone Field is empty
        if(!fragmentForgotPassEtPhone.getText().toString().isEmpty()){
            // Check if phone number equal 11 number
            if((fragmentForgotPassEtPhone.getText().toString().length() == 11)){
                resetPassword();
            }else{
                fragmentForgotPassEtPhone.setError(getString(R.string.frag_register_phone_not_valid));
            }
        }else{
            fragmentForgotPassEtPhone.setError(getString(R.string.frag_forgot_pass_et_empty_phone));
        }
    }

    private void resetPassword() {
        phone = fragmentForgotPassEtPhone.getText().toString().trim();
        getClient().onResetPassword(phone).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(@NonNull Call<ResetPassword> call, @NonNull Response<ResetPassword> response) {
                assert response.body() != null;
                if(response.body().getStatus() == 1){
                    //response.body();
                    userEmail = response.body().getData().getEmail();
                    pubCode = response.body().getData().getPinCodeForTest();
                    ResetPassFragment resetPassFragment = new ResetPassFragment();
                    if(getActivity()!=null){
                        HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, resetPassFragment);
                    }
                    Toast.makeText(getActivity(), "Please Check your Email Address", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getActivity(), "onResponse - Status 0 : \n" + response.body().getMsg() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResetPassword> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "onFailure : \n" + t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}

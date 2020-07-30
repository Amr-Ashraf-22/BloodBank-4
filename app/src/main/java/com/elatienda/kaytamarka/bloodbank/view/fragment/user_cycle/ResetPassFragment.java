package com.elatienda.kaytamarka.bloodbank.view.fragment.user_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.data.model.new_password.NewPassword;
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
import static com.elatienda.kaytamarka.bloodbank.view.fragment.user_cycle.ForgotPassFragment.phone;

public class ResetPassFragment extends BaseFragment {

    @BindView(R.id.fragment_reset_pass_et_code)
    EditText fragmentResetPassEtCode;
    @BindView(R.id.fragment_reset_pass_et_new_password)
    EditText fragmentResetPassEtNewPassword;
    @BindView(R.id.fragment_reset_pass_et_confirm_new_password)
    EditText fragmentResetPassEtConfirmNewPassword;

    public ResetPassFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reset_pass, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.GONE);


        return view;
    }

    @OnClick(R.id.fragment_forgot_pass_btn_login)
    public void onViewClicked(View view) {
        disappearKeypad(getActivity(),view);
        // check if Fields is Empty or not
        if(fragmentResetPassEtCode.getText().toString().isEmpty() ||
                fragmentResetPassEtNewPassword.getText().toString().isEmpty() ||
                fragmentResetPassEtConfirmNewPassword.getText().toString().isEmpty() ){

            if(fragmentResetPassEtCode.getText().toString().isEmpty()){
                fragmentResetPassEtCode.setError(getString(R.string.frag_reset_pass_et_empty_code));
            }

            if(fragmentResetPassEtNewPassword.getText().toString().isEmpty()){
                fragmentResetPassEtNewPassword.setError(getString(R.string.frag_reset_pass_et_empty_new_pass));
            }

            if(fragmentResetPassEtConfirmNewPassword.getText().toString().isEmpty()){
                fragmentResetPassEtConfirmNewPassword.setError(getString(R.string.frag_reset_pass_et_empty_new_confirm_pass));
            }

        }else{
            // check if the passwords are equal
            if(!fragmentResetPassEtNewPassword.getText().toString().equalsIgnoreCase(fragmentResetPassEtConfirmNewPassword.getText().toString())){
                fragmentResetPassEtNewPassword.setError(getString(R.string.frag_reset_pass_password_not_match));
                fragmentResetPassEtConfirmNewPassword.setError(getString(R.string.frag_reset_pass_password_not_match));
            }else{
                fragmentResetPassEtNewPassword.setError(null);
                fragmentResetPassEtConfirmNewPassword.setError(null);
                int code = Integer.parseInt(fragmentResetPassEtCode.getText().toString().trim());
                String newPassword = fragmentResetPassEtNewPassword.getText().toString().trim();
                String confirmNewPassword = fragmentResetPassEtConfirmNewPassword.getText().toString().trim();
                getClient().setNewPassword(newPassword,confirmNewPassword,code,phone).enqueue(new Callback<NewPassword>() {
                    @Override
                    public void onResponse(@NonNull Call<NewPassword> call, @NonNull Response<NewPassword> response) {
                        assert response.body() != null;
                        if(response.body().getStatus() == 1){
                            // check if the code is correct
                            if(!fragmentResetPassEtCode.getText().toString().equalsIgnoreCase(String.valueOf(ForgotPassFragment.pubCode))){
                                fragmentResetPassEtCode.setError(getString(R.string.frag_reset_pass_code_not_valid));
                            }else {
                                // CODE FOR RESET OR CHANGE PASSWORDS HERE
                                Toast.makeText(getActivity(), "Password Changed Successfully", Toast.LENGTH_LONG).show();
                                LoginFragment loginFragment = new LoginFragment();
                                if(getActivity()!=null){
                                    HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, loginFragment);
                                }
                            }
                        }else{
                            Toast.makeText(getActivity(), "onResponse - Status 0 : \n" + response.body().getMsg() , Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NewPassword> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), "onFailure : \n" + t.getMessage() , Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}

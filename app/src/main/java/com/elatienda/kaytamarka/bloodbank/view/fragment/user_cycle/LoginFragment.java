package com.elatienda.kaytamarka.bloodbank.view.fragment.user_cycle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.data.model.login.Login;
import com.elatienda.kaytamarka.bloodbank.util.HelperMethod;
import com.elatienda.kaytamarka.bloodbank.view.activity.HomeCycleActivity;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.jaeger.library.StatusBarUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.elatienda.kaytamarka.bloodbank.data.api.ApiClient.getClient;
import static com.elatienda.kaytamarka.bloodbank.util.HelperMethod.disappearKeypad;

public class LoginFragment extends BaseFragment {

    @BindView(R.id.fragment_login_et_phone)
    EditText fragmentLoginEtPhone;
    @BindView(R.id.fragment_login_et_password)
    EditText fragmentLoginEtPassword;
    @BindView(R.id.fragment_login_tv_forget_pass)
    TextView fragmentLoginTvForgetPass;
    @BindView(R.id.fragment_login_c_bx_remember_me)
    CheckBox fragmentLoginCBxRememberMe;
    @BindView(R.id.fragment_login_btn_login)
    Button fragmentLoginBtnLogin;
    @BindView(R.id.fragment_login_tv_register)
    TextView fragmentLoginTvRegister;

    public LoginFragment(){
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(getActivity()!=null){
            Window w = getActivity().getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);
        StatusBarUtil.setTransparent(getActivity());

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.GONE);

        return view;
    }

    private void getLoginData() {
        String loginPhone = fragmentLoginEtPhone.getText().toString().trim();
        String loginPassword = fragmentLoginEtPassword.getText().toString().trim();
        getClient().onLogin(loginPhone,loginPassword).clone().enqueue(new Callback<Login>() {
            @Override
            public void onResponse(@NonNull Call<Login> call, @NonNull Response<Login> response) {
                try {
                    assert response.body() != null;
                    if(response.body().getStatus() == 1){
                        //Toast.makeText(getActivity(),"Go To Home Page",Toast.LENGTH_LONG).show();
                        // Go To Home Page
                        startActivity(new Intent(getActivity(), HomeCycleActivity.class));
                    }else if(response.body().getStatus() == 0){
                        Toast.makeText(getActivity(), "onResponse - Status 0 : \n" + response.body().getMsg() ,Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), "Exception : \n" + e.getMessage() ,Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Login> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "onFailure : \n" + t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick({R.id.fragment_login_tv_forget_pass, R.id.fragment_login_btn_login, R.id.fragment_login_tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_login_tv_forget_pass:
                    disappearKeypad(getActivity(),view);
                    ForgotPassFragment forgotPassFragment = new ForgotPassFragment();
                    if(getActivity()!=null){
                        HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, forgotPassFragment);
                    }
                break;
            case R.id.fragment_login_btn_login:
                disappearKeypad(getActivity(),view);
                if(fragmentLoginEtPhone.getText().toString().isEmpty() ||
                        fragmentLoginEtPassword.getText().toString().isEmpty()){

                    if(fragmentLoginEtPhone.getText().toString().isEmpty()){
                        fragmentLoginEtPhone.setError(getString(R.string.frag_login_et_empty_phone));
                    }

                    if(fragmentLoginEtPassword.getText().toString().isEmpty()){
                        fragmentLoginEtPassword.setError(getString(R.string.frag_login_et_empty_password));
                    }

                }else{
                    // Login Code here
                    getLoginData();
                    //startActivity(new Intent(getActivity(), HomeCycleActivity.class));
                }
                break;
            case R.id.fragment_login_tv_register:
                // Go To Register Page
                disappearKeypad(getActivity(),view);
                RegisterFragment registerFragment = new RegisterFragment();
                if(getActivity()!=null){
                    HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, registerFragment);
                }
                break;
        }
    }

    @Override
    public void onBack() {
        if(getActivity()!=null){
            getActivity().moveTaskToBack(true);
            getActivity().finish();
        }
    }

}

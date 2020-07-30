package com.elatienda.kaytamarka.bloodbank.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.view.fragment.user_cycle.LoginFragment;
import static com.elatienda.kaytamarka.bloodbank.util.HelperMethod.replaceFragment;

public class UserCycleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_cycle);

        LoginFragment loginFragment = new LoginFragment();
        replaceFragment(getSupportFragmentManager(), R.id.activity_user_cycle_fl_frame, loginFragment);

    }
}

package com.elatienda.kaytamarka.bloodbank.view.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.data.model.notifications_count.NotificationsCount;
import com.elatienda.kaytamarka.bloodbank.util.HelperMethod;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.HomeFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.donation_cycle.CreateDonationFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.donation_cycle.DonationLocationFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.more_cycle.MoreFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.notification_cycle.NotificationFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elatienda.kaytamarka.bloodbank.data.api.ApiClient.getClient;

public class HomeCycleActivity extends BaseActivity {

    @BindView(R.id.activity_home_cycle_bn_nav)
    public BottomNavigationView activityHomeCycleBnNav;

    private TextView notification_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_cycle);
        ButterKnife.bind(this);


        HelperMethod.replaceFragment(getSupportFragmentManager(), R.id.activity_home_cycle_fl_container, new HomeFragment());
        activityHomeCycleBnNav.setSelectedItemId(R.id.nav_home);

        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) activityHomeCycleBnNav.getChildAt(0);

        View view = mbottomNavigationMenuView.getChildAt(1);
        BottomNavigationItemView itemView = (BottomNavigationItemView) view;

        View cart_badge = LayoutInflater.from(this)
                .inflate(R.layout.item_custom_notification_alert,
                        mbottomNavigationMenuView, false);

        notification_number = cart_badge.findViewById(R.id.item_custom_notification_alert_tv);
        getNotificationsCount();

        itemView.addView(cart_badge);

        activityHomeCycleBnNav.setOnNavigationItemSelectedListener(navListener);

    }

    private void getNotificationsCount() {
        getClient().getNotificationsCount("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl")
                .enqueue(new Callback<NotificationsCount>() {
                    @Override
                    public void onResponse(@NonNull Call<NotificationsCount> call, @NonNull Response<NotificationsCount> response) {
                        try {
                            if (response.body().getStatus() == 1){
                                notification_number.setText(String.valueOf(response.body().getData().getNotificationsCount()));
                            }else {
                                Toast.makeText(HomeCycleActivity.this, "onResponse status-0: \n" + response.body().getMsg(), Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(HomeCycleActivity.this, "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NotificationsCount> call, @NonNull Throwable t) {
                        Toast.makeText(HomeCycleActivity.this, "onFailure : \n" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            switch (menuItem.getItemId()){
                case R.id.nav_home:
                    HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new HomeFragment());
                    break;
                case R.id.nav_profile:
                    HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new ProfileFragment());
                    break;
                case R.id.nav_notification:
                    HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new NotificationFragment());
                    break;
                case R.id.nav_more:
                    HelperMethod.replaceFragment(getSupportFragmentManager() , R.id.activity_home_cycle_fl_container ,new MoreFragment());
                    break;
                default:
                    return false;
            }
            return true;
        }
    };

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.bottom_navigation,menu);
//
//        MenuItem item = menu.findItem(R.id.nav_notification);
//        View v = item.getActionView();
//        TextView textView = v.findViewById(R.id.item_custom_notification_alert_tv);
//
//        textView.setText("4");
//
//        return true;
//    }

}
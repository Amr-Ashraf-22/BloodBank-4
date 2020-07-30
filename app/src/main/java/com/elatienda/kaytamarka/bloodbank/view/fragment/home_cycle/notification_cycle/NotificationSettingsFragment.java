package com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.notification_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.adapter.NotificationSettingsAdapter;
import com.elatienda.kaytamarka.bloodbank.data.model.general_response.GeneralResponse;
import com.elatienda.kaytamarka.bloodbank.data.model.notification_settings.NotificationSettings;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.elatienda.kaytamarka.bloodbank.data.api.ApiClient.getClient;

public class NotificationSettingsFragment extends BaseFragment {

    @BindView(R.id.fragment_notification_settings_toolbar)
    Toolbar fragmentNotificationSettingsToolbar;
    @BindView(R.id.fragment_notification_settings_rv_blood_types)
    RecyclerView fragmentNotificationSettingsRvBloodTypes;
    @BindView(R.id.fragment_notification_settings_rv_governorates)
    RecyclerView fragmentNotificationSettingsRvGovernorates;
    @BindView(R.id.fragment_notification_settings_rl_blood_rv)
    RelativeLayout fragmentNotificationSettingsRlBloodRv;
    @BindView(R.id.fragment_notification_settings_rl_governorate_rv)
    RelativeLayout fragmentNotificationSettingsRlGovernorateRv;
    @BindView(R.id.fragment_notification_settings_img_v_blood_types)
    ImageView fragmentNotificationSettingsImgVBloodTypes;
    @BindView(R.id.fragment_notification_settings_img_v_governorates)
    ImageView fragmentNotificationSettingsImgVGovernorates;


    private List<String> bloodTypes = new ArrayList<>();
    private List<String> governorates = new ArrayList<>();
    private NotificationSettingsAdapter bloodAdapter, governorateAdapter;

    public NotificationSettingsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_settings, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(fragmentNotificationSettingsToolbar);
        fragmentNotificationSettingsToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        fragmentNotificationSettingsToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.GONE);

        initRecyclerView();
        getNotificationSetting();

        return view;
    }

    private void initRecyclerView() {
        fragmentNotificationSettingsRvBloodTypes.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        fragmentNotificationSettingsRvGovernorates.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        // Pagination
    }

    private void getNotificationSetting() {
        getClient().getNotificationSettings("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl")
                .enqueue(new Callback<NotificationSettings>() {
                    @Override
                    public void onResponse(@NonNull Call<NotificationSettings> call, @NonNull Response<NotificationSettings> response) {
                        try {
                            assert response.body() != null;
                            if (response.body().getStatus() == 1) {
                                bloodTypes = response.body().getData().getBloodTypes();
                                governorates = response.body().getData().getGovernorates();
                                getBloodTypes();
                                getGovernorates();
                            } else {
                                Toast.makeText(getActivity(), "onResponse status-0: \n" + response.body().getMsg(), Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NotificationSettings> call, @NonNull Throwable t) {
                        Toast.makeText(getActivity(), "onFailure : \n" + t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void getBloodTypes() {

        getClient().getBloodTypes().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {
                try {
                    bloodAdapter = new NotificationSettingsAdapter(getActivity(),
                            response.body().getData(), bloodTypes);
                    fragmentNotificationSettingsRvBloodTypes.setAdapter(bloodAdapter);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "onFailure : \n" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getGovernorates() {

        getClient().getGovernorates().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {
                try {
                    governorateAdapter = new NotificationSettingsAdapter(getActivity(),
                            response.body().getData(), governorates);
                    fragmentNotificationSettingsRvGovernorates.setAdapter(governorateAdapter);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "onFailure : \n" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onCall() {
        getClient().changeNotificationSettings("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl",
                governorateAdapter.newIds, bloodAdapter.newIds).enqueue(new Callback<NotificationSettings>() {
            @Override
            public void onResponse(@NonNull Call<NotificationSettings> call, Response<NotificationSettings> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), "onResponse status-1: \n" + response.body().getMsg(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "onResponse status-0: \n" + response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NotificationSettings> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "onFailure : \n" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @OnClick({R.id.fragment_notification_settings_img_v_blood_types, R.id.fragment_notification_settings_img_v_governorates})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_notification_settings_img_v_blood_types:
                if (fragmentNotificationSettingsRlBloodRv.getVisibility() == View.GONE) {
                    fragmentNotificationSettingsRlBloodRv.setVisibility(View.VISIBLE);
                    fragmentNotificationSettingsImgVBloodTypes.setImageResource(R.drawable.ic_white_minus);
                } else {
                    fragmentNotificationSettingsRlBloodRv.setVisibility(View.GONE);
                    fragmentNotificationSettingsImgVBloodTypes.setImageResource(R.drawable.ic_white_plus);
                }
                break;
            case R.id.fragment_notification_settings_img_v_governorates:
                if (fragmentNotificationSettingsRlGovernorateRv.getVisibility() == View.GONE) {
                    fragmentNotificationSettingsRlGovernorateRv.setVisibility(View.VISIBLE);
                    fragmentNotificationSettingsImgVGovernorates.setImageResource(R.drawable.ic_white_minus);
                } else {
                    fragmentNotificationSettingsRlGovernorateRv.setVisibility(View.GONE);
                    fragmentNotificationSettingsImgVGovernorates.setImageResource(R.drawable.ic_white_plus);
                }
                break;
            case R.id.fragment_notification_settings_btn_save:
                onCall();
                break;
        }
    }

    @Override
    public void onBack() {
        super.onBack();
    }

}

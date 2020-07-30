package com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.notification_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.adapter.NotificationsAdapter;
import com.elatienda.kaytamarka.bloodbank.adapter.PostAdapter;
import com.elatienda.kaytamarka.bloodbank.adapter.SpinnerAdapter;
import com.elatienda.kaytamarka.bloodbank.data.model.notifications.Notifications;
import com.elatienda.kaytamarka.bloodbank.data.model.notifications.NotificationsData;
import com.elatienda.kaytamarka.bloodbank.data.model.post.Post;
import com.elatienda.kaytamarka.bloodbank.data.model.post.PostData;
import com.elatienda.kaytamarka.bloodbank.util.HelperMethod;
import com.elatienda.kaytamarka.bloodbank.util.OnEndLess;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.HomeFragment;
import com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.donation_cycle.CreateDonationFragment;
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

public class NotificationFragment extends BaseFragment {

    @BindView(R.id.fragment_notification_toolbar)
    Toolbar fragmentNotificationToolbar;
    @BindView(R.id.fragment_notification_ll_empty_notifications)
    LinearLayout fragmentNotificationLlEmptyNotifications;
    @BindView(R.id.fragment_notification_rv_notifications)
    RecyclerView fragmentNotificationRvNotifications;

    private NotificationsAdapter notificationsAdapter;
    private List<NotificationsData> notificationsDataList = new ArrayList<>();
    private Integer maxPage = 0;
    private OnEndLess onEndLess;

    public NotificationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(fragmentNotificationToolbar);


        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.VISIBLE);


        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentNotificationRvNotifications.setHasFixedSize(true);
        fragmentNotificationRvNotifications.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;
                        getNotification(current_page);
                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }
            }
        };
        fragmentNotificationRvNotifications.addOnScrollListener(onEndLess);

        notificationsAdapter = new NotificationsAdapter(getContext(), notificationsDataList);
        fragmentNotificationRvNotifications.setAdapter(notificationsAdapter);
        getNotification(1);
    }

    private void getNotification(int page) {
        Call<Notifications> call = getClient().getAllNotifications("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl", page);
        startCall(call, page);
    }

    private void startCall(Call<Notifications> call, int page) {
        call.enqueue(new Callback<Notifications>() {
            @Override
            public void onResponse(@NonNull Call<Notifications> call, @NonNull Response<Notifications> response) {
                try {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        notificationsDataList.addAll(response.body().getData().getData());
                        notificationsAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "onResponse status-0: \n" + response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Notifications> call, @NonNull Throwable t) {
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

    @OnClick(R.id.fragment_notification_btn_empty_notifications)
    public void onViewClicked() {
        if (getActivity() != null) {
            CreateDonationFragment createDonationFragment = new CreateDonationFragment();
            HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(), R.id.activity_home_cycle_fl_container, createDonationFragment);
        }
    }
}

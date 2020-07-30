package com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.donation_cycle;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.adapter.DonationAdapter;
import com.elatienda.kaytamarka.bloodbank.adapter.SpinnerAdapter;
import com.elatienda.kaytamarka.bloodbank.data.model.donation_requests.DonationRequests;
import com.elatienda.kaytamarka.bloodbank.data.model.donation_requests.DonationRequestsData;
import com.elatienda.kaytamarka.bloodbank.util.HelperMethod;
import com.elatienda.kaytamarka.bloodbank.util.OnEndLess;
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
import static com.elatienda.kaytamarka.bloodbank.util.GeneralRequest.getSpinnerData;

public class DonationRequestsFragment extends BaseFragment {

    @BindView(R.id.fragment_donation_sp_governorate)
    Spinner fragmentDonationSpGovernorate;
    @BindView(R.id.fragment_donation_sp_blood_type)
    Spinner fragmentDonationSpBloodType;
    @BindView(R.id.fragment_donation_rv_donations)
    RecyclerView fragmentDonationRvDonations;

    private SpinnerAdapter bloodTypesAdater, governoratesAdapter;
    private List<DonationRequestsData> donationDataList = new ArrayList<>();
    private DonationAdapter donationAdapter;
    private Integer maxPage = 0;
    private OnEndLess onEndLess;
    private boolean Filter = false;

    public DonationRequestsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation_requests, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.VISIBLE);

        bloodTypesAdater = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), fragmentDonationSpBloodType, bloodTypesAdater,
                getString(R.string.frag_register_sp_blood_type), getClient().getBloodTypes());

        governoratesAdapter = new SpinnerAdapter(getActivity());
        getSpinnerData(getActivity(), fragmentDonationSpGovernorate, governoratesAdapter,
                getString(R.string.frag_register_sp_governorate), getClient().getGovernorates());

        initRecyclerView();

        return view;
    }

    private void initRecyclerView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        fragmentDonationRvDonations.setHasFixedSize(true);
        fragmentDonationRvDonations.setLayoutManager(layoutManager);

        onEndLess = new OnEndLess(layoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {
                if (current_page <= maxPage) {
                    if (maxPage != 0 && current_page != 1) {
                        onEndLess.previous_page = current_page;

                        if (Filter){
                            onFilter(current_page);
                        }else {
                            getDonations(current_page);
                        }

                    } else {
                        onEndLess.current_page = onEndLess.previous_page;
                    }
                } else {
                    onEndLess.current_page = onEndLess.previous_page;
                }

            }
        };
        fragmentDonationRvDonations.addOnScrollListener(onEndLess);

        donationAdapter = new DonationAdapter(getActivity(), donationDataList);
        fragmentDonationRvDonations.setAdapter(donationAdapter);

        getDonations(1);
    }

    private void getDonations(int page) {
        Call<DonationRequests> call = getClient().getAllDonationRequests("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl", page);
        startCall(call, page);
    }

    @OnClick({R.id.fragment_donation_img_btn_filter, R.id.fragment_post_fab_request_donation})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_donation_img_btn_filter:
                    onFilter(1);
                break;
            case R.id.fragment_post_fab_request_donation:
                if(getActivity()!=null){
                    CreateDonationFragment createDonationFragment = new CreateDonationFragment();
                    HelperMethod.replaceFragment(getActivity().getSupportFragmentManager(),R.id.activity_home_cycle_fl_container,createDonationFragment);
                }
                break;
        }
    }

    private void onFilter(int page) {
        Filter = true;
        
        onEndLess.previousTotal = 0;
        onEndLess.current_page = 1;
        onEndLess.previous_page = 1;
        donationDataList = new ArrayList<>();

        donationAdapter = new DonationAdapter(getActivity(), donationDataList);
        fragmentDonationRvDonations.setAdapter(donationAdapter);

        Call<DonationRequests> call = getClient().getAllDonationRequestsFilter("W4mx3VMIWetLcvEcyF554CfxjZHwdtQldbdlCl2XAaBTDIpNjKO1f7CHuwKl",
                bloodTypesAdater.selectedId , governoratesAdapter.selectedId ,page);
        startCall(call, page);
    }

    private void startCall(Call<DonationRequests> call, int page) {
        call.enqueue(new Callback<DonationRequests>() {
            @Override
            public void onResponse(@NonNull Call<DonationRequests> call, @NonNull Response<DonationRequests> response) {
                try {
                    assert response.body() != null;
                    if (response.body().getStatus() == 1) {
                        maxPage = response.body().getData().getLastPage();
                        donationDataList.addAll(response.body().getData().getData());
                        donationAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getActivity(), "onResponse status-0: \n" + response.body().getMsg(), Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Exception : \n" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<DonationRequests> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "onFailure : \n" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBack() {
        if(getActivity()!=null){
            getActivity().moveTaskToBack(true);
            getActivity().finish();
        }
    }
}

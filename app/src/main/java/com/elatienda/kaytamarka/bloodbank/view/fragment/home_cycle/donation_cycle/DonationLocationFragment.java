package com.elatienda.kaytamarka.bloodbank.view.fragment.home_cycle.donation_cycle;

import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.view.fragment.BaseFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class DonationLocationFragment extends BaseFragment implements OnMapReadyCallback {

    @BindView(R.id.fragment_donation_location_toolbar)
    Toolbar fragmentDonationLocationToolbar;
    private GoogleMap mMap;
    private String addressLine = null;

    public DonationLocationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donation_location, container, false);
        setUpActivity();
        ButterKnife.bind(this, view);


        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(fragmentDonationLocationToolbar);
        fragmentDonationLocationToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        fragmentDonationLocationToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
            }
        });

        BottomNavigationView navBar = Objects.requireNonNull(getActivity()).findViewById(R.id.activity_home_cycle_bn_nav);
        navBar.setVisibility(View.GONE);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        //getActivity().getSupportFragmentManager()
        //getSupportFragmentManager()
        //getFragmentManager()
        //getChildFragmentManager()
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_donation_location_map_container);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Geocoder gc = new Geocoder(getActivity());
                List<Address> list = null;
                try {
                    list = gc.getFromLocation(latLng.latitude,latLng.longitude,1);
                    Address address = list.get(0);
                    addressLine = address.getAddressLine(0);

                    //Toast.makeText(getActivity(), address.getFeatureName() , Toast.LENGTH_SHORT).show();

                    MarkerOptions markerOptions = new MarkerOptions()
                            .title(address.getFeatureName())
                            .position(new LatLng(latLng.latitude,latLng.longitude));
                    mMap.addMarker(markerOptions);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }



    @OnClick(R.id.fragment_donation_location_btn)
    public void onViewClicked() {
//        CreateDonationFragment createDonationFragment = new CreateDonationFragment();
//        if(addressLine != null){
//            Bundle bundle = new Bundle();
//            bundle.putString("hospital_address", addressLine ); // Put anything what you want
//            createDonationFragment.setArguments(bundle);
//            onBack();
//        }else {
//            Toast.makeText(getActivity(),"addressLine = null", Toast.LENGTH_SHORT).show();
//        }
        //addressLine
          //dataSendListener.onDataSend(addressLine);
    }

    @Override
    public void onBack() {
        super.onBack();
    }
}

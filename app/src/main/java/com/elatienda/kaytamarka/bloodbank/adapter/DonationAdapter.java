package com.elatienda.kaytamarka.bloodbank.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.data.model.donation_requests.DonationRequestsData;
import com.elatienda.kaytamarka.bloodbank.view.activity.BaseActivity;
import java.util.ArrayList;
import java.util.List;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DataViewHolder> {

    private Context mContext;
    private BaseActivity activity;
    private List<DonationRequestsData> donationDataList = new ArrayList<>();

    public DonationAdapter(Context context, List<DonationRequestsData> donationData){
        this.mContext = context;
        //this.activity = (BaseActivity) activity;
        this.donationDataList = donationData;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_custom_donation,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        setData(holder,position);
    }

    public void setData(DataViewHolder holder, int position) {
        DonationRequestsData donationRequestsData = donationDataList.get(position);
            holder.mBloodType.setText(donationRequestsData.getBloodType().getName());
            holder.mName.setText("Patient Name: " + donationRequestsData.getPatientName());
            holder.mHospital.setText("Hospital: " + donationRequestsData.getHospitalName());
            holder.mCity.setText("City: " + donationRequestsData.getCity().getName());
    }

    public void setAction(DataViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return donationDataList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        TextView mBloodType;
        TextView mName;
        TextView mHospital;
        TextView mCity;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            mBloodType = itemView.findViewById(R.id.item_custom_donation_tv_blood_type);
            mName = itemView.findViewById(R.id.item_custom_donation_tv_name);
            mHospital = itemView.findViewById(R.id.item_custom_donation_tv_hospital);
            mCity = itemView.findViewById(R.id.item_custom_donation_tv_city);
        }
    }

}

package com.elatienda.kaytamarka.bloodbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.data.model.notifications.NotificationsData;

import java.util.ArrayList;
import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.DataViewHolder> {

    private Context mContext;
    private List<NotificationsData> notificationsDataList = new ArrayList<>();

    public NotificationsAdapter(Context mContext, List<NotificationsData> notificationsDataList) {
        this.mContext = mContext;
        this.notificationsDataList = notificationsDataList;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_custom_notification,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        setData(holder,position);
    }

    public void setData(DataViewHolder holder, int position) {
        NotificationsData notificationsData = notificationsDataList.get(position);
        holder.notificationText.setText(notificationsData.getCreatedAt());







    }

    public void setAction(DataViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return notificationsDataList.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        TextView notificationText ;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationText = itemView.findViewById(R.id.item_custom_notification_tv_date);
        }
    }
}

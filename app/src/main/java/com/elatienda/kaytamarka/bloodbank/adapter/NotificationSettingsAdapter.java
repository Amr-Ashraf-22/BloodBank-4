package com.elatienda.kaytamarka.bloodbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.data.model.general_response.GeneralResponseData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationSettingsAdapter extends RecyclerView.Adapter<NotificationSettingsAdapter.DataViewHolder> {

    private Context context;
    private List<GeneralResponseData> generalResponseData = new ArrayList<>();
    private List<String> oldIds = new ArrayList<>();
    public List<Integer> newIds = new ArrayList<>();

    public NotificationSettingsAdapter(Context context, List<GeneralResponseData> generalResponseData, List<String> oldIds) {
        this.context = context;
        this.generalResponseData = generalResponseData;
        this.oldIds = oldIds;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_custom_check_box, parent, false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(DataViewHolder holder, int position) {
        holder.itemCustomCheckBoxItem.setText(generalResponseData.get(position).getName());

        if (oldIds.contains(String.valueOf(generalResponseData.get(position).getId()))) {
            holder.itemCustomCheckBoxItem.setChecked(true);
            newIds.add(generalResponseData.get(position).getId());
        }else {
            holder.itemCustomCheckBoxItem.setChecked(false);
        }
    }

    private void setAction(DataViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return generalResponseData.size();
    }

    class DataViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_custom_check_box_item)
        CheckBox itemCustomCheckBoxItem;
        private View view;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this,view);
        }

        @OnClick(R.id.item_custom_check_box_item)
        public void onViewClicked() {
            //oldIds.contains(String.valueOf(generalResponseData.get(getAdapterPosition()).getId()))
            if (!itemCustomCheckBoxItem.isChecked()) {
                for (int i = 0; i < newIds.size(); i++) {
                    if (newIds.get(i).equals(generalResponseData.get(getAdapterPosition()).getId())) {
                        newIds.remove(i);
                        break;
                    }
                }
            }else {
                newIds.add(generalResponseData.get(getAdapterPosition()).getId());
            }
        }
    }
}

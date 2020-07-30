package com.elatienda.kaytamarka.bloodbank.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.elatienda.kaytamarka.bloodbank.R;
import com.elatienda.kaytamarka.bloodbank.data.model.general_response.GeneralResponseData;
import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<GeneralResponseData> generalResponseDataList = new ArrayList<>();
    private LayoutInflater inflater;
    public int selectedId = 0;

    public SpinnerAdapter(Context applicationContext){
        this.context = applicationContext;
        inflater = (LayoutInflater.from(applicationContext));
    }

    public void setData(List<GeneralResponseData> generalResponseDataList, String hint) {
        generalResponseDataList.add(0, new GeneralResponseData(0, hint));
        this.generalResponseDataList = new ArrayList<>();
        this.generalResponseDataList = generalResponseDataList;
    }

    @Override
    public int getCount() {
        return generalResponseDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return generalResponseDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.item_custom_spinner,null, false);

        TextView names = convertView.findViewById(R.id.item_custom_spinner_tv_text);

        names.setText(generalResponseDataList.get(position).getName());
        selectedId = generalResponseDataList.get(position).getId();

        return convertView;
    }
}

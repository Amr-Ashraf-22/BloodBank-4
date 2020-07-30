package com.elatienda.kaytamarka.bloodbank.util;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.elatienda.kaytamarka.bloodbank.adapter.SpinnerAdapter;
import com.elatienda.kaytamarka.bloodbank.data.model.general_response.GeneralResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralRequest {

    public static void getSpinnerData(Activity activity, final Spinner spinner, final SpinnerAdapter adapter,
                                      final String hint, Call<GeneralResponse> method){

        method.clone().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {
                try{
                    assert response.body() != null;
                    if(response.body().getStatus() == 1){
                        //spinner.setAdapter(null);
                        spinner.setVisibility(View.VISIBLE);
                        adapter.setData(response.body().getData(), hint);
                        spinner.setAdapter(adapter);
                        //Toast.makeText(activity, "Responed single method", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(activity, "onResponse status-0: \n" + response.body().getMsg() , Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(activity, "Exeption: \n" + e.getMessage() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {
                Toast.makeText(activity,"onFailure: \n" + t.getMessage() , Toast.LENGTH_LONG).show();
            }
        });

    }

    public static void getSpinnerData(final Activity activity, final Spinner spinner1, final SpinnerAdapter adapter1, final String hint1, final Call<GeneralResponse> method1,
                                      final Spinner spinner2, final SpinnerAdapter adapter2, final String hint2, final Call<GeneralResponse> method2){

        method1.enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(@NonNull Call<GeneralResponse> call, @NonNull Response<GeneralResponse> response) {
                try{
                    assert response.body() != null;
                    if(response.body().getStatus() == 1){
                        //spinner1.setAdapter(null);
                        spinner1.setVisibility(View.VISIBLE);
                        adapter1.setData(response.body().getData(),hint1);
                        spinner1.setAdapter(adapter1);
                        //Toast.makeText(activity, "Responed double method", Toast.LENGTH_SHORT).show();
                        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                if(position != 0){
                                    //spinner2.setAdapter(null);
                                    spinner2.setEnabled(true);
                                    getSpinnerData(activity,spinner2,adapter2,hint2,method2);
                                }else{
                                    spinner2.setEnabled(false);
                                    spinner2.setAdapter(null);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }else{
                        Toast.makeText(activity, "onResponse status-0: \n" + response.body().getMsg() , Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(activity, "Exeption: \n" + e.getMessage() , Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<GeneralResponse> call, @NonNull Throwable t) {
                Toast.makeText(activity, "onFailure: \n" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

package com.example.root.sens.DAO;

import android.util.Log;

import com.example.root.sens.DTO.Response;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SensDAO implements Callback<Response> {
    private static final String TAG = SensDAO.class.getSimpleName();
    private Retrofit retrofitInstance;
    private ISensAPI service;
    private static SensDAO sensDAOInstance;

    public static SensDAO getInstance(){
        if(sensDAOInstance == null){
            sensDAOInstance = new SensDAO();
            sensDAOInstance.retrofitInstance  = new Retrofit.Builder().baseUrl("https://beta.sens.dk/exapi/1.0/patients/data/external/").addConverterFactory(GsonConverterFactory.create()).build();
            sensDAOInstance.service = sensDAOInstance.retrofitInstance.create(ISensAPI.class);
        }
        return sensDAOInstance;
    }

    private SensDAO() {
    }
    public void getData(String patientKey){
        Call<Response> temp = service.getData(patientKey);
        temp.enqueue(this);
    }
    public void getData(String patientKey, int dayCount){
        validateDayCount(dayCount);
        Call<Response> temp = service.getData(patientKey, dayCount);
        temp.enqueue(this);
    }
    public void getDataSpecificDate(String patientKey, int dayCount, String date){
        validateDayCount(dayCount);
        validateDate(date);
        Call<Response> temp = service.getData(patientKey, dayCount);
        temp.enqueue(this);
    }

    public void getDataSpecificDate(String patientKey, String date){
        validateDate(date);
        Call<Response> temp = service.getData(patientKey);
        temp.enqueue(this);
    }

    private void validateDayCount(int dayCount){
        if(dayCount < 0 || dayCount > 14){
            //Throw some kind of exception here
        }
    }

    private void validateDate(String date){
        String[] temp = date.split("-");
        if(temp.length != 3){
            //Throw error
        }
        //Check following format size ####-##-##, year-month-day
        if(temp[1].length() != 4 || temp[2].length() != 2 || temp[3].length() != 2){
            //Throw error
        }
    }

    @Override
    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
        if(response.isSuccessful()){
            Response t = response.body();
            Log.d(TAG,"Received response from sens");
        }else{
            Log.d(TAG,"Error receiving response from sens");
        }
    }

    @Override
    public void onFailure(Call<Response> call, Throwable t) {
        t.printStackTrace();
    }
}

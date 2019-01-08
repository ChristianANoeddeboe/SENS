package com.example.root.sens.dao;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.example.root.sens.dao.interfaces.ISensAPI;
import com.example.root.sens.dao.interfaces.SensObserver;
import com.example.root.sens.dao.interfaces.Subject;
import com.example.root.sens.dto.ActivityCategories;
import com.example.root.sens.dto.Record;
import com.example.root.sens.dto.sensresponse.Datum;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.sensresponse.Response;
import com.example.root.sens.dto.User;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Used to download data from SENS
 */
public class SensDAO implements Callback<Response>, Subject {
    private static final String TAG = "test1234";
    private Retrofit retrofitInstance;
    private ISensAPI service;
    private static SensDAO sensDAOInstance;
    private ArrayList<SensObserver> mObservers;
    public static SensDAO getInstance(){
        if(sensDAOInstance == null){
            sensDAOInstance = new SensDAO();
            sensDAOInstance.mObservers = new ArrayList<>();
            sensDAOInstance.retrofitInstance  = new Retrofit.Builder().baseUrl("http://beta.sens.dk/exapi/1.0/patients/data/external/").addConverterFactory(GsonConverterFactory.create()).build();
            sensDAOInstance.service = sensDAOInstance.retrofitInstance.create(ISensAPI.class);
        }
        return sensDAOInstance;
    }

    private SensDAO() {
    }

    public void getData(String patientKey){
        Call<Response> temp = service.getData(patientKey,14);
        temp.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()){
                    Response t = response.body();
                    if(t != null && t.statusMsg.equals("OK") && t.statusCode == 0){
                        Log.d(TAG,"SUCESSFULL");
                        notifyObservers(t);
                    }else{
                        Log.d(TAG, "Response was supposedly sucessfull but was not as expected" + t.toString());
                    }


                }else{
                    if(response.errorBody().contentLength() == 67){
                    Log.d(TAG,"Retrying....");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            new AsyncTask() {
                                @Override
                                protected Object doInBackground(Object[] objects) {
                                    Log.d(TAG,"Retrying the call");
                                    getData(patientKey);
                                    return null;
                                }
                            }.execute();
                        }
                    }, 30000);
                    }else{
                        Log.d(TAG, "Some other error: " + response.errorBody());
                    }

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Log.d(TAG, "ERROR");
                t.printStackTrace();
            }
        });
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

    }

    @Override
    public void onFailure(Call<Response> call, Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void registerObserver(SensObserver sensObserver) {
        if(!mObservers.contains(sensObserver)) {
            mObservers.add(sensObserver);
        }
    }

    @Override
    public void removeObserver(SensObserver sensObserver) {
        if(mObservers.contains(sensObserver)) {
            mObservers.remove(sensObserver);
        }
    }

    @Override
    public void notifyObservers(Response r) {
        User tempUser = UserDAO.getInstance().getUserLoggedIn();
        RealmList<DayData>  tempUserDayData = tempUser.getDayData();
        List<Datum> responseData = r.getData();
        DateFormat sensDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        //First generate the daydata from the data we have received and then merge with the users
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for(Datum d : responseData){
            boolean found = false;
            RealmList<Record> tempRecords = new RealmList<>();
            tempRecords.add(new Record(d.values.activityRestingTime,ActivityCategories.Resting.toString()));
            tempRecords.add(new Record(d.values.activityStandingTime,ActivityCategories.Standing.toString()));
            tempRecords.add(new Record(d.values.activityWalkingTime,ActivityCategories.Walking.toString()));
            tempRecords.add(new Record(d.values.activityExerciseTime,ActivityCategories.Exercise.toString()));
            tempRecords.add(new Record(d.values.activityCyclingTime,ActivityCategories.Cycling.toString()));
            DayData temp = null;
            try {
                temp = new DayData(sensDf.parse(d.startTime), sensDf.parse(d.endTime),tempRecords);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for(DayData userData : tempUserDayData){
                if(userData.getStart_time().equals(temp.getStart_time()) && userData.getEnd_time().equals(temp.getEnd_time())){
                    for(int i = 0; i < userData.getRecords().size(); i++){
                        if(userData.getRecords().get(i).getType() == tempRecords.get(i).getType()){
                            userData.getRecords().get(i).setValue(tempRecords.get(i).getValue());
                        }else{
                            Log.d(TAG, "FATAL ERROR WHILE TRYING TO NOTIFY OBSERVERS IN SENS DAO");
                            System.exit(0);
                        }

                    }
                    found = true;
                    break;
                }
            }
            if(!found){
                tempUserDayData.add(temp);
            }
        }

        // At this point the tempUserDayDAta realmlist has the up to date version of the day data.
        tempUser.setDayData(tempUserDayData);
        realm.commitTransaction();
        UserDAO.getInstance().saveUser(tempUser);

        for (SensObserver observer: mObservers) {
            observer.onDataReceived();
        }
    }
}

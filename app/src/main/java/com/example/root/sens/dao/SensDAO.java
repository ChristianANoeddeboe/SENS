package com.example.root.sens.dao;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.root.sens.dao.interfaces.ISensAPI;
import com.example.root.sens.dao.interfaces.SensObserver;
import com.example.root.sens.dao.interfaces.SensSubject;
import com.example.root.sens.ActivityCategories;
import com.example.root.sens.dto.Record;
import com.example.root.sens.dto.sensresponse.Datum;
import com.example.root.sens.dto.DayData;
import com.example.root.sens.dto.sensresponse.Response;
import com.example.root.sens.dto.User;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
 * Used to download DemoData from SENS
 */
public class SensDAO implements SensSubject {
    private static final String TAG = SensDAO.class.getSimpleName();
    private Retrofit retrofitInstance;
    private ISensAPI service;
    private static SensDAO sensDAOInstance;
    private List<SensObserver> mObservers;
    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    public static SensDAO getInstance(){
        if(sensDAOInstance == null){
            sensDAOInstance = new SensDAO();
            sensDAOInstance.mObservers = new ArrayList<>();
            sensDAOInstance.retrofitInstance  = new Retrofit.Builder().baseUrl("http://beta.sens.dk/exapi/1.0/patients/DemoData/external/").addConverterFactory(GsonConverterFactory.create()).build();
            sensDAOInstance.service = sensDAOInstance.retrofitInstance.create(ISensAPI.class);
        }
        return sensDAOInstance;
    }

    /*
     * Empty constructor needed for retrofit
     */
    private SensDAO() {
    }

    public void getData(String patientKey, int dayCount){
        ArrayList<Call<Response>> callArrayList = new ArrayList<>();
        callArrayList.add(service.getData(patientKey, dayCount));
        getDataFromSens(callArrayList,0, false);
    }

    public void getDataMonth(String patientKey, Date dateOfMonth, boolean pageswipe){
        ArrayList<Call<Response>> callArrayList = new ArrayList<>();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(dateOfMonth);
        c1.add(Calendar.DATE, 13);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(dateOfMonth);
        c2.add(Calendar.DATE,27);

        callArrayList.add(service.getDataSpecificDate(patientKey, 14,df.format(c1.getTime())));
        callArrayList.add(service.getDataSpecificDate(patientKey, 14,df.format(c2.getTime())));
        Calendar c3 = Calendar.getInstance();
        if(c1.getMaximum(Calendar.DAY_OF_MONTH) > 30){
            c3.setTime(dateOfMonth);
            c3.add(Calendar.DATE,31);
            callArrayList.add(service.getDataSpecificDate(patientKey, 14,df.format(c3.getTime())));
        }


        getDataFromSens(callArrayList,0, pageswipe);
    }

    public void getDataSpecificDate(String patientKey, Date date){
        ArrayList<Call<Response>> callArrayList = new ArrayList<>();
        callArrayList.add(service.getDataSpecificDate(patientKey,df.format(date)));
        getDataFromSens(callArrayList,0, false);
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
    public void notifyObservers(boolean b) {
        //Called on failure, to make the snackbar dismiss, an automatic retry is setup elsewhere.
        for (SensObserver observer: mObservers) {
            observer.onDataReceived(b);
        }
    }

    /*
     * We merge and save the DemoData
     * @param r
     */
    private void mergeAndSaveData(Response r) {
        User tempUser = UserDAO.getInstance().getUserLoggedIn();
        RealmList<DayData> tempUserDayData = tempUser.getDayData();
        List<Datum> responseData = r.getData();
        DateFormat sensDf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        //First generate the daydata from the DemoData we have received and then merge with the users
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for(Datum d : responseData){
            boolean found = false;
            RealmList<Record> tempRecords = new RealmList<>();
            tempRecords.add(new Record(d.values.activityRestingTime,ActivityCategories.Søvn.toString()));
            tempRecords.add(new Record(d.values.activityStandingTime,ActivityCategories.Stå.toString()));
            tempRecords.add(new Record(d.values.activityWalkingTime,ActivityCategories.Gang.toString()));
            tempRecords.add(new Record(d.values.activityExerciseTime,ActivityCategories.Træning.toString()));
            tempRecords.add(new Record(d.values.activityCyclingTime,ActivityCategories.Cykling.toString()));
            tempRecords.add(new Record(d.values.activityStepsCount,ActivityCategories.Skridt.toString()));
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

        // At this point the tempUserDayData realmlist has the up to date version of the day DemoData.
        tempUser.setDayData(tempUserDayData);
        realm.commitTransaction();
        UserDAO.getInstance().saveUser(tempUser);
    }

    /*
     * Fetch DemoData from SENS
     * @param patientKey The patient key
     */
    private void getDataFromSens(List<Call<Response>> callList, int i, boolean pageSwipe){
        if(i < callList.size()) {
            callList.get(i).enqueue(new Callback<Response>() {
                @Override
                public void onResponse(@NonNull Call<Response> call, @NonNull retrofit2.Response<Response> response) { // When response is received
                    if (response.isSuccessful()) { // If response was sucessfull
                        Response t = response.body();
                        if (t != null && t.statusMsg.equals("OK") && t.statusCode == 0) { // A few checks making sure everything is Ok
                            Log.d(TAG, "SUCCESSFUL");
                            mergeAndSaveData(t);
                        } else {
                            Log.d(TAG, "Response was supposedly sucessfull but was not as expected" + t.toString());
                        }
                        int counter = i;
                        counter = counter + 1;
                        getDataFromSens(callList,counter, pageSwipe);
                    } else if (response.code() == 406) {
                        try {
                            String s = response.errorBody().string().toLowerCase();
                            if (s.contains("analysis")) {
                                Log.d(TAG, "Retrying....");
                                new Handler().postDelayed(() -> new AsyncTask() {
                                    @Override
                                    protected Object doInBackground(Object[] objects) {
                                        Log.d(TAG, "Retrying the call");
                                        Call<Response> temp = call.clone();
                                        ArrayList<Call<Response>> tempList = new ArrayList<>();
                                        tempList.add(temp);
                                        getDataFromSens(tempList,i, pageSwipe);
                                        return null;
                                    }
                                }.execute(), 10000); // Delay the retry, note this function is recursive and calls itself until DemoData is fetched sucessfully.
                                // We try every 10 seconds
                            } else if (s.contains("measurement")) {
                                int counter = i;
                                counter = counter +1;
                                getDataFromSens(callList,counter, pageSwipe);
                                if(callList.size() == 1 && !pageSwipe){ // We have clicked on a date on the calendar
                                    notifyObservers(false);
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            Log.d(TAG, "Some other error: " + response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                }

                @Override
                public void onFailure(@NonNull Call<Response> call, @NonNull Throwable t) {
                    Log.d(TAG, "ERROR");
                    notifyObservers(true);
                    t.printStackTrace();
                }
            });
        }else{ // There are no calls pending
            notifyObservers(true);
        }
    }
}

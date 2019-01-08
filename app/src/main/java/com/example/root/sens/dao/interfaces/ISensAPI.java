package com.example.root.sens.dao.interfaces;

import com.example.root.sens.dto.sensresponse.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ISensAPI {
    @GET("overview?project_key=k5W2uX")
    Call<Response> getData(@Query("patient_key") String patient_key);

    @GET("overview?project_key=k5W2uX")
    Call<Response> getData(@Query("patient_key") String patient_key, @Query("day_count") int dayCount);

    @GET("overview?project_key=k5W2uX")
    Call<Response> getDataSpecificDate(@Query("patient_key") String patient_key, @Query("day_count") int dayCount, @Query("date") String date);

    @GET("overview?project_key=k5W2uX")
    Call<Response> getDataSpecificDate(@Query("patient_key") String patient_key, @Query("date") String date);




}

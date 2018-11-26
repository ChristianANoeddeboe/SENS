package com.example.root.sens.dao;

import com.example.root.sens.dto.Response;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ISensAPI {
    @GET("overview?project_key=k5W2uX&patient_key={patientkey}")
    Call<Response> getData(@Path("patientkey") String patientKey);


    @GET("overview?project_key=k5W2uX&patient_key={patientkey}&day_count={daycount}")
    Call<Response> getData(@Path("patientkey") String patientKey, @Path("daycount") int dayCount);

    @GET("overview?project_key=k5W2uX&patient_key={patientkey}&day_count={daycount}&date={date}")
    Call<Response> getDataSpecificDate(@Path("patientkey") String patientKey, @Path("daycount") int dayCount, @Path("date") String date);

    @GET("overview?project_key=k5W2uX&patient_key={patientkey}&date={date}")
    Call<Response> getDataSpecificDate(@Path("patientkey") String patientKey, @Path("date") String date);




}

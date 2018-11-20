package com.example.root.sens;

import com.example.root.sens.DTO.Response;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SENSAPI {
    @GET("overview?project_key=k5W2uX&patient_key=xt9w2r&day_count=7")
    Call<Response> getData();
}

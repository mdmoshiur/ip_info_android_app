package com.moshiur.ipinfo.Interface;

import com.moshiur.ipinfo.Model.ServerResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/json")
    Call<ServerResponse> getMyIp();
}

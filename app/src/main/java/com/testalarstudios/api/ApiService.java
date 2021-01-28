package com.testalarstudios.api;

import com.testalarstudios.model.ResponseModel;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {
    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("auth.cgi")
    Single<ResponseModel> auth(@Query("username") String username, @Query("password") String password);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("data.cgi")
    Single<ResponseModel> fetchData(@Query("p") int page, @Query("code") String code);
}

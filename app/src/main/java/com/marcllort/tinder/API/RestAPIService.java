package com.marcllort.tinder.API;


import com.marcllort.tinder.Model.MyProfile;
import com.marcllort.tinder.Model.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RestAPIService {

    @POST("/api/authenticate")
    Call<UserToken> requestToken(@Body UserData userData);

    @POST("/api/register")
    Call<Void> register(@Body UserData userData);

    @GET("/api/profiles/{id}")
    Call<MyProfile> getProfileById(@Path("id") Integer id, @Header("Autorization") String token);

    @POST("/api/profiles")
    Call<MyProfile> createProfile(@Body MyProfile myProfile, @Header("Autorization") String token);

    @PUT("/api/my-profile")
    Call<MyProfile> updateCurrentProfile(@Body MyProfile myProfile, @Header("Autorization") String token);

    @PUT("/api/profiles")
    Call<MyProfile> updateProfile(@Body MyProfile myProfile, @Header("Autorization") String token);

    @GET("/api/my-profile")
    Call<MyProfile> getProfile(@Header("Authorization") String token);

}

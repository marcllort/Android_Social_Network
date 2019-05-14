package com.marcllort.tinder.API;


import com.marcllort.tinder.Model.MyProfile;
import com.marcllort.tinder.Model.User;
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

    @POST("/api/profiles")
    Call<MyProfile> createProfile(@Body MyProfile myProfile, @Header("Authorization") String token);

    @GET("/api/my-profile")
    Call<MyProfile> getMyProfile(@Header("Authorization") String token);

    @PUT("api/my-profile")
    Call<MyProfile> updateMyProfile(@Body MyProfile myProfile, @Header("Authorization") String token);

    @PUT("api/users")
    Call<User> getUser(@Header("Authorization") String token);
}

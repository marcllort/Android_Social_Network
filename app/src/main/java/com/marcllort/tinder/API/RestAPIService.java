package com.marcllort.tinder.API;


import com.marcllort.tinder.Model.Invitation;
import com.marcllort.tinder.Model.MyProfile;
import com.marcllort.tinder.Model.User;
import com.marcllort.tinder.Model.UserData;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface RestAPIService {

    @POST("/api/authenticate")
    Call<UserToken> requestToken(@Body UserData userData);

    @POST("/api/register")
    Call<Void> register(@Body UserData userData);

    @POST("/api/profiles")
    Call<MyProfile> createProfile(@Body MyProfile myProfile, @Header("Authorization") String token);

    @GET("/api/my-profile")
    Call<MyProfile> getMyProfile(@Header("Authorization") String token);

    @PUT("/api/my-profile")
    Call<MyProfile> updateMyProfile(@Body MyProfile myProfile, @Header("Authorization") String token);

    @GET("/api/profiles/{login}")
    Call<MyProfile> getUser(@Path("login")int login, @Header("Authorization") String token);

    @GET("/api/profiles")
    Call<ArrayList<MyProfile>> getUsers(@Header("Authorization") String token);

    @GET("/api/pending-invites")
    Call<Invitation[]> getPendingInvites(@Header("Authorization") String token, @QueryMap Map<String, String> options);

    @GET("/api/invitations")
    Call<Invitation[]> getAllInvitations (@Header("Authorization") String token, @QueryMap Map<String, String> options);

    @POST("/api/invite/{id}")
    Call<Invitation> inviteUser(@Path("id")int id, @Header("Authorization") String token);

    @GET("/api/my-friends")
    Call<ArrayList<MyProfile>> getConnections(@Header("Authorization") String token);

}

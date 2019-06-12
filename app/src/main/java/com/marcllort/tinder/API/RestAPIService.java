package com.marcllort.tinder.API;


import com.marcllort.tinder.Model.Invitation;
import com.marcllort.tinder.Model.Message;
import com.marcllort.tinder.Model.MyProfile;
import com.marcllort.tinder.Model.User;
import com.marcllort.tinder.Model.UserData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
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

    @GET("/api/account")
    Call<User> getAccount(@Header("Authorization") String token);

    @PUT("/api/my-profile")
    Call<MyProfile> updateMyProfile(@Body MyProfile myProfile, @Header("Authorization") String token);

    @GET("/api/profiles/{login}")
    Call<MyProfile> getUser(@Path("login")int login, @Header("Authorization") String token);

    @GET("/api/profiles")
    Call<ArrayList<MyProfile>> getUsers(@Header("Authorization") String token);

    @GET("/api/pending-invites")
    Call<ArrayList<Invitation>> getPendingInvites(@Header("Authorization") String token);

    @GET("/api/invitations")
    Call<List<Invitation>> getAllInvitations (@Header("Authorization") String token);


    @POST("/api/invite/{id}")
    Call<Invitation> inviteUser(@Path("id")int id, @Header("Authorization") String token);

    @GET("/api/my-friends")
    Call<ArrayList<MyProfile>> getConnections(@Header("Authorization") String token);

    @GET("/api/direct-messages")
    Call<ArrayList<Message>> getMessages(@Header("Authorization") String token);

    @PUT("/api/direct-messages")
    Call<Message> sendMessage(@Body Message message, @Header("Authorization") String token);

    @PUT("/api/invite/{id}/state/{state}")
    Call<Invitation> changeInviteState(@Path("id")int id, @Path("state")boolean state, @Header("Authorization") String token);

    @PUT("api/invitations")
    Call<Invitation> updateInvitation(@Body Invitation invitation, @Header("Authorization") String token);

}

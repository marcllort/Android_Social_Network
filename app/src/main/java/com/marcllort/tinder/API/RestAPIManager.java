package com.marcllort.tinder.API;


import com.marcllort.tinder.Model.MyProfile;
import com.marcllort.tinder.Model.UserData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPIManager {

    private static final String BASE_URL = "http://android.byted.xyz/";
    private static RestAPIManager ourInstance;
    private Retrofit retrofit;
    private RestAPIService restApiService;
    private UserToken userToken;


    public static RestAPIManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new RestAPIManager();
        }
        return ourInstance;
    }

    private RestAPIManager() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        restApiService = retrofit.create(RestAPIService.class);

    }

    public synchronized void getUserToken(String username, String password, final LoginCallBack loginCallBack) {
        UserData userData = new UserData(username, password);
        Call<UserToken> call = restApiService.requestToken(userData);

        call.enqueue(new Callback<UserToken>() {
            @Override
            public void onResponse(Call<UserToken> call, Response<UserToken> response) {

                if (response.isSuccessful()) {
                    userToken = response.body();
                    loginCallBack.onLoginSuccess(userToken);
                } else {
                    loginCallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<UserToken> call, Throwable t) {
                loginCallBack.onFailure(t);
            }
        });
    }

    public synchronized void register(String username, String email, String password, final RegisterCallBack registerCallback) {
        UserData userData = new UserData(username, email, password);
        Call<Void> call = restApiService.register(userData);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if (response.isSuccessful()) {
                    registerCallback.onRegisterSuccess();
                } else {
                    registerCallback.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                registerCallback.onFailure(t);
            }
        });
    }

    public synchronized void createProfile(MyProfile myProfile, final CreateProfileCallBack createProfileCallBack) {
        MyProfile perfil = myProfile;
        Call<MyProfile> call = restApiService.createProfile(perfil, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<MyProfile>() {
            @Override
            public void onResponse(Call<MyProfile> call, Response<MyProfile> response) {

                if(response.isSuccessful()) {
                    createProfileCallBack.onCreateMyProfile(response.body());
                    createProfileCallBack.onSuccess();
                }
                else {
                    createProfileCallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<MyProfile> call, Throwable t) {
                createProfileCallBack.onFailure(t);
            }
        });
    }

    public synchronized void updateMyProfile(MyProfile myProfile, final ProfileCallBack profileCallBack) {
        MyProfile perfil = myProfile;
        Call<MyProfile> call = restApiService.updateCurrentProfile(perfil,"Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<MyProfile>() {
            @Override
            public void onResponse(Call<MyProfile> call, Response<MyProfile> response) {

                if(response.isSuccessful()) {
                    profileCallBack.onUpdateMyProfile(response.body());
                }
                else {
                    profileCallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<MyProfile> call, Throwable t) {
                profileCallBack.onFailure(t);
            }
        });
    }

}

package com.marcllort.tinder.API;


import com.marcllort.tinder.Model.*;


import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPIManager {

    private static final String BASE_URL = "http://android3.byted.xyz/";
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

                if (response.isSuccessful()) {
                    createProfileCallBack.onCreateMyProfile(response.body());
                    createProfileCallBack.onSuccess();
                } else {
                    createProfileCallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<MyProfile> call, Throwable t) {
                createProfileCallBack.onFailure(t);
            }
        });
    }




    public synchronized void getMyProfile(final ProfileCallBack profileCallBack) {

        Call<MyProfile> call = restApiService.getMyProfile("Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<MyProfile>() {
            @Override
            public void onResponse(Call<MyProfile> call, Response<MyProfile> response) {
                if (response.isSuccessful()) {
                    profileCallBack.onGetProfile(response.body());
                } else {
                    profileCallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<MyProfile> call, Throwable t) {
                profileCallBack.onFailure(t);
            }
        });
    }

    public synchronized void getAccount(final AccountCallback accountCallback) {

        Call<User> call = restApiService.getAccount("Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    accountCallback.onGetAccount(response.body());
                } else {
                    accountCallback.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                accountCallback.onFailure(t);
            }
        });
    }


    public synchronized void updateProfile(final MyProfile profile, final ProfileCallBack profileCallBack) {
        final MyProfile newUserProfile = profile;
        Call<MyProfile> call = restApiService.updateMyProfile(profile, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<MyProfile>() {
            @Override
            public void onResponse(Call<MyProfile> call, Response<MyProfile> response) {
                if (response.isSuccessful()) {
                    profileCallBack.onUpdateProfile(newUserProfile);
                } else {
                    profileCallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<MyProfile> call, Throwable t) {
                profileCallBack.onFailure(t);
            }
        });
    }

    public synchronized void getUser(final UserProfileCallBack userprofileCallBack, int login) {

        Call<MyProfile> call = restApiService.getUser(login, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<MyProfile>() {
            @Override
            public void onResponse(Call<MyProfile> call, Response<MyProfile> response) {
                if (response.isSuccessful()) {
                    userprofileCallBack.onGetUser(response.body());
                } else {
                    userprofileCallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<MyProfile> call, Throwable t) {
                userprofileCallBack.onFailure(t);
            }
        });
    }

    public synchronized void getUsers(final UserCallBack userCallBack) {

        Call<ArrayList<MyProfile>> call = restApiService.getUsers("Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<ArrayList<MyProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<MyProfile>> call, Response<ArrayList<MyProfile>> response) {
                if (response.isSuccessful()) {
                    userCallBack.onGetUsers(response.body());
                } else {
                    userCallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyProfile>> call, Throwable t) {
                userCallBack.onFailure(t);
            }
        });
    }

    public synchronized void getAllInvitations(final InvitationCallBack invitationCallBack) {
        Call<Invitation[]> call = restApiService.getPendingInvites("Bearer " + userToken.getIdToken(), new HashMap<String, String>());

        call.enqueue(new Callback<Invitation[]>() {
            @Override
            public void onResponse(Call<Invitation[]> call, Response<Invitation[]> response) {
                if (response.isSuccessful()) {
                    invitationCallBack.onGetAllInvitations(response.body());
                } else {
                    invitationCallBack.onFailure((new Throwable("ERROR " + response.code() + ", " + response.raw().message())));
                }
            }

            @Override
            public void onFailure(Call<Invitation[]> call, Throwable t) {
                invitationCallBack.onFailure(t);
            }
        });
    }

    public synchronized void inviteUser(final InviteCallBack inviteCallBack, int id) {
        Call<Invitation> call = restApiService.inviteUser(id, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<Invitation>() {
            @Override
            public void onResponse(Call<Invitation> call, Response<Invitation> response) {
                if (response.isSuccessful()) {
                    inviteCallBack.onGetInvitation(response.body());
                } else {
                    inviteCallBack.onFailure((new Throwable("ERROR " + response.code() + ", " + response.raw().message())));
                }
            }

            @Override
            public void onFailure(Call<Invitation> call, Throwable t) {
                inviteCallBack.onFailure(t);
            }
        });
    }

    public synchronized void getConnections(final ConnectionsCallBack connectionsCallBack) {

        Call<ArrayList<MyProfile>> call = restApiService.getConnections("Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<ArrayList<MyProfile>>() {
            @Override
            public void onResponse(Call<ArrayList<MyProfile>> call, Response<ArrayList<MyProfile>> response) {
                if (response.isSuccessful()) {
                    connectionsCallBack.onGetConnections(response.body());
                } else {
                    connectionsCallBack.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyProfile>> call, Throwable t) {
                connectionsCallBack.onFailure(t);
            }
        });
    }

    public synchronized void getMessages(final MessageCallback messageCallback) {

        Call<ArrayList<Message>> call = restApiService.getMessages("Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<ArrayList<Message>>() {
            @Override
            public void onResponse(Call<ArrayList<Message>> call, Response<ArrayList<Message>> response) {
                if (response.isSuccessful()) {
                    messageCallback.onGetMessages(response.body());
                } else {
                    messageCallback.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Message>> call, Throwable t) {
                messageCallback.onFailure(t);
            }
        });
    }

    public synchronized void sendMessage(Message message, final MessageCallback messageCallback) {

        Call<Message> call = restApiService.sendMessage(message, "Bearer " + userToken.getIdToken());

        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {

                if (response.isSuccessful()) {
                    messageCallback.onSendMessage();

                } else {
                    messageCallback.onFailure(new Throwable("ERROR " + response.code() + ", " + response.raw().message()));

                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                messageCallback.onFailure(t);
            }
        });
    }

}

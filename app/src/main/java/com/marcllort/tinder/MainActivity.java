package com.marcllort.tinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;


import com.marcllort.tinder.API.InvitationCallBack;
import com.marcllort.tinder.API.ProfileCallBack;
import com.marcllort.tinder.API.RestAPIManager;
import com.marcllort.tinder.Model.CustomArrayAdapter;
import com.marcllort.tinder.Model.Invitation;
import com.marcllort.tinder.Model.MyProfile;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements InvitationCallBack, ProfileCallBack {

    private ImageButton profileBtn;
    private ImageButton matchesBtn;
    private ImageButton btn_main;
    private CustomArrayAdapter adapter;
    private  ListView invitationsList;
    public  ArrayList<Invitation> pendingInvitations;
    public   Invitation invitationAux;
    private MyProfile myProfile;
    private int position;


    public List<Invitation> getPending() { return pendingInvitations;}
    public void deleteInvitation(int position) { pendingInvitations.remove(position);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        configButtons();

        pendingInvitations = new ArrayList<>();
        invitationsList = findViewById(R.id.invitations_list);
        RestAPIManager.getInstance().getMyProfile(this);
        invitationAux = null;
    }



    private void configButtons() {

        profileBtn = findViewById(R.id.btn_profile);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(profileIntent);
            }
        });

        matchesBtn = findViewById(R.id.btn_matches);
        matchesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent matchesIntent = new Intent(getApplicationContext(), MatchesActivity.class);
                startActivity(matchesIntent);
            }
        });
        btn_main = findViewById(R.id.btn_main);
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent matchesIntent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(matchesIntent);
            }
        });

    }

    //API
    @Override
    public void onGetPendingInvites(ArrayList<Invitation> invitationsList) {


    }

    @Override
    public void onGetAllInvitations(List<Invitation> invitations) {


        System.out.println(invitations.size());
        pendingInvitations = new ArrayList<>();
        for(Invitation inv: invitations) {
            if(inv.getReceived().getDisplayName().equals(myProfile.getDisplayName()) && inv.getAccepted() == false) {
                System.out.println(inv.getSent().getDisplayName() + " " + inv.getId());
                pendingInvitations.add(inv);
            }
        }
        invitationsList = (ListView) findViewById(R.id.invitations_list);
        adapter = new CustomArrayAdapter(this);
        invitationsList.setAdapter(adapter);

    }

    public void handleAccepted(int position) {
        this.position = position;
        Invitation invitation = pendingInvitations.get(position);
        invitation.setAccepted(true);
        RestAPIManager.getInstance().updateInvitation(invitation,  this);

    }

    @Override
    public void onChangeState() {


    }

    @Override
    public void onUpdateInvitation(Invitation invitation) {

        RestAPIManager.getInstance().getAllInvitations(this);
    }

    @Override
    public void onFailure(Throwable t) {
        System.out.println("fallo");
    }


    @Override
    public void onGetProfile(MyProfile profile) {
        myProfile = profile;

       RestAPIManager.getInstance().getAllInvitations(this);
    }

    @Override
    public void onUpdateProfile(MyProfile myProfile) {}

    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


  /*  private ArrayList<String> data;                                                                         // Informació a mostrar a les targetes
    private SwipeFlingAdapterView flingContainer;
    private ImageButton leftBtn;
    private ImageButton rightBtn;
    private ImageButton profileBtn;
    private ImageButton matchesBtn;
    private ImageButton btn_main;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setData();                                                                                          // Posem informació dins el ArrayList data

        RestAPIManager.getInstance().getAllInvitations(this);

    }


    private void setData() {

        data = new ArrayList<>();

        data.add("php");
        data.add("c");
        data.add("python");
        data.add("java");
        data.add("html");
        data.add("c++");
        data.add("css");
        data.add("javascript");

    }

    private void createCards() {
        flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);            // Busquem el frame (on apareixen les diferents targetes)

        flingContainer.setAdapter(arrayAdapter);                                                            // Posem al frame les targetes que haura de mostrar

        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {                       // Listeners segons el que li fem a la targeta
            @Override
            public void removeFirstObjectInAdapter() {                                                      // S'executa al fer el swipe a qualsevol lloc, demoment borra el objecte del array de info
                Log.d("LIST", "removed object!");
                invitationsArr.remove(0);
                //data.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {                                                 // Que fer al haber un left-swipe
                makeToast(MainActivity.this, "Left!");
            }

            @Override
            public void onRightCardExit(Object dataObject) {                                                // Que fer al haber un right-swipe
                makeToast(MainActivity.this, "Right!");
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {                                         // Que fer quan està apunt d'acabar-se el array de informació
                // Demanarem per més informació aqui
//                data.add("XML ".concat(String.valueOf(i)));
                arrayAdapter.notifyDataSetChanged();
                Log.d("LIST", "notified");
                i++;
            }

            @Override
            public void onScroll(float scrollProgressPercent) {

            }

        });                 // Listeners per quan fem algun tipus de swipe


        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {          // Listener per quan clickem la targeta
                makeToast(MainActivity.this, "Clicked!");
            }
        });
    }

    private void buttonsSetup() {
        rightBtn = findViewById(R.id.right);
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectRight();
            }
        });


        leftBtn = findViewById(R.id.left);
        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flingContainer.getTopCardListener().selectLeft();
            }
        });

        profileBtn = findViewById(R.id.btn_profile);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(profileIntent);
            }
        });

        matchesBtn = findViewById(R.id.btn_matches);
        matchesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent matchesIntent = new Intent(getApplicationContext(), MatchesActivity.class);
                startActivity(matchesIntent);
            }
        });
        btn_main = findViewById(R.id.btn_main);
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent matchesIntent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(matchesIntent);
            }
        });

    }

    static void makeToast(Context ctx, String s) {
        Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onGetPendingInvites(ArrayList<Invitation> invitations) {

    }

    @Override
    public void onGetAllInvitations(ArrayList<Invitation> invitations) {


        createCards();                                                                                      // Creem les targetes, posem els seus listeners de fer swipe, click...

        buttonsSetup();
    }

    @Override
    public void onFailure(Throwable t) {

    }*/
}
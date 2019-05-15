package com.marcllort.tinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lorentzos.flingswipe.SwipeFlingAdapterView;
import com.marcllort.tinder.API.InvitationCallBack;
import com.marcllort.tinder.API.RestAPIManager;
import com.marcllort.tinder.Model.Invitation;

import java.util.ArrayList;


public class MainActivity extends Activity implements InvitationCallBack {

    private ArrayList<String> data;                                                                         // Informació a mostrar a les targetes
    private Invitation[] invitations;
    private ArrayAdapter<Invitation> arrayAdapter;                                                              // Adaptador de infromació a targetes
    private SwipeFlingAdapterView flingContainer;
    private ImageButton leftBtn;
    private ImageButton rightBtn;
    private ImageButton profileBtn;
    private ImageButton matchesBtn;
    private ImageButton btn_main;
    private int i;


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
                data.remove(0);
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
                data.add("XML ".concat(String.valueOf(i)));
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
    public void onGetPendingInvites(Invitation[] invitations) {

    }

    @Override
    public void onGetAllInvitations(Invitation[] invitations) {
        this.invitations = invitations;

        arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.dataText, invitations);                // Adaptem el arraylist a el format necessari per les targetes

        createCards();                                                                                      // Creem les targetes, posem els seus listeners de fer swipe, click...

        buttonsSetup();
    }

    @Override
    public void onFailure(Throwable t) {

    }
}
package com.marcllort.tinder;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.marcllort.tinder.API.RestAPIManager;
import com.marcllort.tinder.API.UserCallBack;
import com.marcllort.tinder.Model.MyProfile;
import com.marcllort.tinder.Model.User;

import java.util.ArrayList;

public class SearchActivity extends Activity implements UserCallBack {

    private ImageButton profileBtn;
    private ImageButton chatButton;
    private ImageButton mainBtn;
    private ListView matchList;
    private EditText filter;
    ArrayList<MyProfile> users;
    ArrayList<String> names;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        names = new ArrayList<>();
        RestAPIManager.getInstance().getUsers(this);
        users = new ArrayList<MyProfile>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        matchList = findViewById(R.id.matchList);
        filter = findViewById(R.id.searchBar);
        topBarSetup();

        matchList = findViewById(R.id.matchList);


    }


    @Override
    public void onGetUsers(final ArrayList<MyProfile> users) {
        //Copiar el arraylist de users a names
        //names = users;
        for (MyProfile i : users) {
            //System.out.println(i.getDisplayName());
            names.add(i.getDisplayName());
        }

        adapter = new ArrayAdapter(this, R.layout.activity_searchlayout, names);
        matchList.setAdapter(adapter);


        matchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                Toast.makeText(getBaseContext(), names.get(position), Toast.LENGTH_LONG).show();
                Intent profileIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                //System.out.println("-------- "+ id + "---------" + names.get(position).getDisplayName());
                String  user = (String) adapter.getItem(position);
                for (int i = 0; i<users.size(); i++){
                    System.out.println("AAA");
                    if(user.equals(users.get(i).getDisplayName())){
                        System.out.println(users.get(i).getDisplayName() + "NOM " +user);
                        profileIntent.putExtra("login", users.get(i).getId());
                        break;
                    }
                }


                startActivity(profileIntent);
            }
        });


        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (SearchActivity.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onFailure(Throwable t) {
        new AlertDialog.Builder(this)
                .setTitle("Users not found")
                .setMessage(t.getMessage())


                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    }
                })

                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    private void topBarSetup() {

        profileBtn = findViewById(R.id.btn_profile);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent profileIntent = new Intent(getApplicationContext(), MyProfileActivity.class);
                startActivity(profileIntent);
            }
        });

        mainBtn = findViewById(R.id.btn_main);
        mainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        chatButton = findViewById(R.id.btn_matches);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent matchesIntent = new Intent(getApplicationContext(), MatchesActivity.class);
                startActivity(matchesIntent);
            }
        });

    }

}

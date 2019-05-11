package com.marcllort.tinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

public class MatchesActivity extends Activity {


    private ImageButton profileBtn;
    private ImageButton mainBtn;
    private ListView matchList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        topBarSetup();

        matchList = findViewById(R.id.matchList);
        //matchList.add


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

    }

}

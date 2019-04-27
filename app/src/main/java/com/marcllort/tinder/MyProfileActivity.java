package com.marcllort.tinder;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;


public class MyProfileActivity extends Activity {

    private FloatingActionButton saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        saveButton();

    }

    private void saveButton() {

        saveButton = findViewById(R.id.btn_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

}

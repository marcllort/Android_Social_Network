package com.marcllort.tinder;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcllort.tinder.API.ProfileCallBack;
import com.marcllort.tinder.API.RestAPIManager;
import com.marcllort.tinder.Model.MyProfile;
import com.marcllort.tinder.Model.User;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Locale;


public class MyProfileActivity extends AppCompatActivity implements ProfileCallBack {

    private FloatingActionButton saveButton;
    private ImageView profileImage;
    private EditText et_location;
    private EditText bio;
    private EditText interests;
    private TextView name;
    private Uri resultUri;
    private LocationManager locationManager;
    private LocationListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        saveButton();

        name = (TextView) findViewById(R.id.txt_name);
        bio = (EditText) findViewById(R.id.et_aboutme);
        interests = (EditText) findViewById(R.id.et_interests);

        profileImage = findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                System.out.println("Location: " + location.getLongitude() + " " + location.getLatitude());

                String cityName = null;
                Geocoder gcd = new Geocoder(getBaseContext(),
                        Locale.getDefault());
                List<Address> addresses;
                try {
                    addresses = gcd.getFromLocation(location.getLatitude(), location
                            .getLongitude(), 1);
                    if (addresses.size() > 0)
                        System.out.println(addresses.get(0).getLocality());
                    cityName = addresses.get(0).getLocality();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                et_location.setText(cityName);
                System.out.println(cityName);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        configure_button();
        refreshProfile();

    }

    private void saveButton() {

        saveButton = findViewById(R.id.btn_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {           //enviar los nuevos datos al backend

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {                         // Posem imatge a la imageView
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            profileImage.setImageURI(resultUri);
        }
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button() {
        // Mirem permisos
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }

        et_location = findViewById(R.id.et_location);
        et_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates("gps", 2000, 0, listener);       // Podem canviar el temps de update


            }
        });
    }

    private void refreshProfile() {
        RestAPIManager.getInstance().getMyProfile(this);
    }

    @Override
    public void onGetMyProfile(MyProfile myProfile) {
        MyProfile perfil = myProfile;
        name.setText(perfil.getDisplayName());
        bio.setText(perfil.getAboutMe());
        interests.setText(perfil.getFilterPreferences());

    }

    @Override
    public void onUpdateProfile(MyProfile myProfile) {
        new AlertDialog.Builder(this)
                .setTitle("Changes added")
                .setMessage("Changes to your profile were added successfully.")
                .show();

    }

    @Override
    public void onFailure(Throwable t) {
        System.out.println(t.getMessage());
    }

}

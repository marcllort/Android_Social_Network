
package com.marcllort.tinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.marcllort.tinder.API.CreateProfileCallBack;
import com.marcllort.tinder.API.ProfileCallBack;
import com.marcllort.tinder.API.RestAPIManager;
import com.marcllort.tinder.Model.MyProfile;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;


public class MyProfileActivity extends AppCompatActivity implements ProfileCallBack {

    private FloatingActionButton saveButton;
    private MyProfile myProfile;
    private ImageView profileImage;
    private EditText et_location;
    private EditText bio;
    private EditText interests;
    private TextView name;
    private Uri resultUri;
    private LocationManager locationManager;
    private String image;
    private LocationListener listener;
    private boolean bioChanged = false, interestsChanged = false, nameChanged = false, imageChanged = false;
    private boolean actualized = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        saveButton();


        bio = (EditText) findViewById(R.id.et_aboutme);
        interests = (EditText) findViewById(R.id.et_interests);
        name = (TextView) findViewById(R.id.txt_name);


        profileImage = findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {            //Al subir una imagen de la galeria
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
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
        setData();



        bio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bioChanged = true;
            }
        });

        interests.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                interestsChanged = true;
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nameChanged = true;
            }
        });

        System.out.println(bioChanged + " " + nameChanged + " " + interestsChanged);

    }


    public static String convertBitmapToString(Bitmap bitmap) {                                     //Convertir la imagen a base64 encoded string
        String encodedImage = "";


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        try {
            encodedImage = URLEncoder.encode(Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return encodedImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == 0) {
            if (resultCode == RESULT_OK) {
                resultUri = data.getData();
                Bitmap bitmap;

                try {
                    bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(resultUri));
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                    profileImage.setImageBitmap(resizedBitmap);
                    imageChanged = true;
                    //myProfile.setPicture("changed");
                    image = convertBitmapToString(resizedBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    void setData() {
        if (!actualized) {
            RestAPIManager.getInstance().getMyProfile(this);
        }
    }

    @Override
    public synchronized void onGetProfile(MyProfile myProfile) {

        if(myProfile.getBirthDate() == null) {
            Intent profileIntent = new Intent(getApplicationContext(), ProfileCreateActivity.class);
            startActivity(profileIntent);
        }
        else {

            this.myProfile = myProfile;

            bio.setText(this.myProfile.getAboutMe());
            interests.setText(this.myProfile.getFilterPreferences());
            name.setText(this.myProfile.getDisplayName());

            if (this.myProfile.getPicture() != null) {
                fromStringToImage(this.myProfile.getPicture());
            }

            nameChanged = false;
            bioChanged = false;
            interestsChanged = false;
        }

    }

    @Override
    public void onFailure(Throwable t) {
    }

    private void saveButton() {

        saveButton = findViewById(R.id.btn_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {           //enviar los nuevos datos al backend

                attemptSaveProfile();
            }
        });

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

    public void attemptSaveProfile() {
        if (!bioChanged && !nameChanged && !interestsChanged && !imageChanged) {
            Toast.makeText(getBaseContext(), "No changes found", Toast.LENGTH_LONG).show();
            Intent profileIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(profileIntent);
        } else {
            if(myProfile.getPicture() != null || imageChanged) {
                image = fromImageToBase64();
                myProfile.setPicture(image);
            } else {
               myProfile.setPicture(null);
            }
            myProfile.setAboutMe(bio.getText().toString());
            myProfile.setDisplayName(name.getText().toString());
            myProfile.setFilterPreferences(interests.getText().toString());
            RestAPIManager.getInstance().updateProfile(myProfile, this);
        }

    }

    @Override
    public synchronized void onUpdateProfile(MyProfile newProfile) {

        Toast.makeText(getBaseContext(), "Profile Updated!", Toast.LENGTH_LONG).show();
        Intent profileIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(profileIntent);

    }


    private String fromImageToBase64() {
        /*
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.id.profileImage);
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        String image = convertBitmapToString(bitmap);
        return image;*/
        BitmapDrawable drawable = (BitmapDrawable) profileImage.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        String encodedImage = encodeToBase64(bitmap, Bitmap.CompressFormat.JPEG, 100);

       /* Bitmap bitmap = ((BitmapDrawable)profileImage.getDrawable()).getBitmap();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
        image = convertBitmapToString(resizedBitmap);*/

        return encodedImage;
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private void fromStringToImage(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        profileImage.setImageBitmap(decodedImage);
    }

}
package com.marcllort.tinder;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marcllort.tinder.API.CreateProfileCallBack;
import com.marcllort.tinder.API.RestAPIManager;
import com.marcllort.tinder.Model.Gender;
import com.marcllort.tinder.Model.MyProfile;

import org.honorato.multistatetogglebutton.MultiStateToggleButton;
import org.honorato.multistatetogglebutton.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static androidx.constraintlayout.motion.widget.MotionScene.TAG;


public class ProfileCreateActivity extends Activity implements CreateProfileCallBack {

    private EditText dateText;
    private EditText mapText;
    private Uri resultUri;
    private FloatingActionButton saveButton;
    private MultiStateToggleButton gender;
    private EditText displayName;
    private EditText interests;
    private EditText bio;
    private LocationManager locationManager;
    private ImageView profileImage;
    private LocationListener listener;
    private boolean bioChanged = false, interestsChanged = false, pictureChanged = false, displayNameChanged = false, genderChanged = false, dateChanged = false;
    private MyProfile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);

        profile = new MyProfile();


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        gender = (MultiStateToggleButton) findViewById(R.id.gender_selector);

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

                mapText.setText(cityName);
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

        bio = (EditText) findViewById(R.id.et_aboutme);
        interests = (EditText) findViewById(R.id.et_interests);
        displayName = (EditText) findViewById(R.id.et_name);


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

        displayName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                displayNameChanged = true;
            }
        });



        dateSelector();
        saveButton();
        MultiStateToggle();
        mapSelector();

    }


    private void saveButton() {

        saveButton = findViewById(R.id.btn_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCreate();
            }
        });

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

                    //myProfile.setPicture("changed");
                    pictureChanged = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    private void handleCreate() {

        if(bioChanged && pictureChanged && interestsChanged && displayNameChanged && dateChanged && genderChanged) {
            profile.setPicture(fromImageToBase64());
            profile.setDisplayName(displayName.getText().toString());
            profile.setAboutMe(bio.getText().toString());
            profile.setFilterPreferences(interests.getText().toString());
            profile.setBirthDate(dateText.toString());

            RestAPIManager.getInstance().createProfile( profile, this);

        } else {
            Toast.makeText(getBaseContext(), "Fill all fields!", Toast.LENGTH_LONG).show();

        }

    }

    private void mapSelector() {
        mapText = findViewById(R.id.et_location);
        mapText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager.requestLocationUpdates("gps", 2000, 0, listener);       // Podem canviar el temps de update

            }
        });
    }

    private void dateSelector() {                                                                           // Selector de data amb datepicker, i posteriorment la adapta a un edittext
        final Calendar myCalendar = Calendar.getInstance(Locale.US);
        dateText = (EditText) findViewById(R.id.birth);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

                dateText.setText(sdf.format(myCalendar.getTime()));
                dateChanged = true;
            }

        };

        dateText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("nadfklsaffsafas");
                new DatePickerDialog(ProfileCreateActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }


    @Override
    public void onCreateMyProfile(MyProfile myProfile) {

    }

    @Override
    public void onSuccess() {
        Intent profileIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(profileIntent);
    }

    @Override
    public void onFailure(Throwable t) {

    }


    private void MultiStateToggle() {

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        MultiStateToggleButton msb_button = (MultiStateToggleButton) this.findViewById(R.id.gender_selector);
        CharSequence[] texts = new CharSequence[]{"Male", "Female", "Other"};
        msb_button.setElements(texts);


        msb_button.setOnValueChangedListener(new ToggleButton.OnValueChangedListener() {
            @Override
            public void onValueChanged(int position) {
                Gender gender;
                Log.d(TAG, "Position: " + position);
                switch (position) {
                    case 0:
                        gender = new Gender(1, "Male");
                        genderChanged = true;
                        profile.setGender(gender);
                        break;
                    case 1:
                        gender = new Gender(2, "Female");
                        genderChanged = true;
                        profile.setGender(gender);
                        break;

                    case 2:
                        gender = new Gender(3, "Other");
                        genderChanged = true;
                        profile.setGender(gender);
                        break;

                }
            }
        });
    }
}

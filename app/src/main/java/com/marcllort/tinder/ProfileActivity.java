
package com.marcllort.tinder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.marcllort.tinder.API.ProfileCallBack;
import com.marcllort.tinder.API.RestAPIManager;
import com.marcllort.tinder.API.UserProfileCallBack;
import com.marcllort.tinder.Model.MyProfile;
import com.marcllort.tinder.Model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class ProfileActivity extends  AppCompatActivity implements UserProfileCallBack {

    private FloatingActionButton saveButton;
    private ImageView profileImage;
    private EditText et_location;
    private TextView bio;
    private TextView interests;
    private TextView name;
    private FloatingActionButton btn_invite;
    private Uri resultUri;
    private LocationManager locationManager;
    private LocationListener listener;
    private boolean bioChanged = false, interestsChanged = false, nameChanged = false;
    private boolean actualized = false;
    String login;
    User user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            login = extras.getString("login");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btn_invite = findViewById(R.id.btn_invite);
        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //saveButton();


        bio =  findViewById(R.id.et_aboutme);
        interests =  findViewById(R.id.et_interests);
        name =  findViewById(R.id.txt_name);


        profileImage = findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Bitmap bmp = profileImage.getDrawingCache();
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
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


        setData();

        System.out.println(bioChanged + " " + nameChanged + " " + interestsChanged);

/*        bio.addTextChangedListener(new TextWatcher() {
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
        });*/

/*        interests.addTextChangedListener(new TextWatcher() {
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
        });*/

        /*name.addTextChangedListener(new TextWatcher() {
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
        });*/

        System.out.println(bioChanged + " " + nameChanged + " " + interestsChanged);

    }

    void setData() {
        if (!actualized) {
            RestAPIManager.getInstance().getUser(this,login);


        }
    }


    public synchronized void onGetUser(MyProfile myProfile) {



    }

    @Override
    public void onGetUser(User userin) {
         user = userin;

       // bio.setText(user.getAboutMe());
        //interests.setText(profile.getFilterPreferences());
        name.setText(user.getLogin());
        if(user.getEmail() != null){
            bio.setText(user.getEmail());
        }

        nameChanged = false;

        bioChanged = false;
        interestsChanged = false;
    }

    @Override
    public void onFailure(Throwable t) {
    }



  /*  @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {                         // Posem imatge a la imageView
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            final Uri imageUri = data.getData();
            resultUri = imageUri;
            profileImage.setImageURI(resultUri);
            profileImage.buildDrawingCache();
            Bitmap bmap = profileImage.getDrawingCache();
            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            bmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
            byte[] image=stream.toByteArray();
            System.out.println("byte array:"+image);
            String img_str = Base64.encodeToString(image, 0);
            System.out.println("string:"+img_str);
            bio.setText(img_str);
        }
    }*/


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                //configure_button();
                break;
            default:
                break;
        }
    }






    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String imgDecodableString = Base64.encodeToString(b, Base64.DEFAULT);
        System.out.println(imgDecodableString);
        return imgDecodableString;

    }

/*public static Bitmap decodeBase64(String input) {
        byte[] decodedBytes = Base64.decode(input.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        }
    private String encodeImages(String path) {
        File imagefile = new File(path);
        FileInputStream fis = null;
        try{
        fis = new FileInputStream(imagefile);
        }catch(FileNotFoundException e){
        e.printStackTrace();
        }
        Bitmap bm = BitmapFactory.decodeStream(fis);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String imgDecodableString = Base64.encodeToString(b, Base64.DEFAULT);
        //Base64.de
        return imgDecodableString;
    }*/


}
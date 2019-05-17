
package com.marcllort.tinder;

import android.Manifest;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.marcllort.tinder.API.InviteCallBack;
import com.marcllort.tinder.API.ProfileCallBack;
import com.marcllort.tinder.API.RestAPIManager;
import com.marcllort.tinder.API.UserProfileCallBack;
import com.marcllort.tinder.Model.Invitation;
import com.marcllort.tinder.Model.MyProfile;
import com.marcllort.tinder.Model.User;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Locale;


public class ProfileActivity extends  AppCompatActivity implements UserProfileCallBack , InviteCallBack {

    private FloatingActionButton saveButton;
    private ImageView profileImage;
    private TextView txt_distance;
    private TextView bio;
    private TextView interests;
    private TextView name;
    private TextView gender;
    private FloatingActionButton btn_invite;
    private Uri resultUri;
    private LocationManager locationManager;
    private LocationListener listener;
    private boolean bioChanged = false, interestsChanged = false, nameChanged = false;
    private boolean actualized = false;
    int id;
    com.marcllort.tinder.Model.Location location;

    MyProfile user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        location= new com.marcllort.tinder.Model.Location();
        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            id = extras.getInt("login");
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        btn_invite = findViewById(R.id.btn_invite);
        btn_invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestAPIManager.getInstance().inviteUser(ProfileActivity.this, user.getId());
                finish();
            }
        });


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        //saveButton();

        txt_distance = findViewById(R.id.txt_distance);
        bio =  findViewById(R.id.et_aboutme);
        interests =  findViewById(R.id.et_interests);
        name =  findViewById(R.id.txt_name);
        gender = findViewById(R.id.et_gender);
        profileImage = findViewById(R.id.profileImage);





        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location1) {
                System.out.println("Location: " + location1.getLongitude() + " " + location1.getLatitude());
                location = new com.marcllort.tinder.Model.Location();
                location.setLongitude(location1.getLongitude());
                location.setLatitude(location1.getLatitude());

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



        System.out.println(bioChanged + " " + nameChanged + " " + interestsChanged);

    }

    void setData() {
        if (!actualized) {
            RestAPIManager.getInstance().getUser(this,id);


        }
    }




    @Override
    public void onGetUser(MyProfile myProfile) {
        user = myProfile;
        bio.setText(user.getAboutMe());
        name.setText(user.getDisplayName());
        interests.setText(user.getBirthDate());
        if (user.getPicture() != null) {
            fromStringToImage(user.getPicture());
        }

        if (user.getGender() != null){
            gender.setText(user.getGender().getType());
        }


        //Calcula la distancia
        if (user.getLocation() != null) {
            double latitude = user.getLocation().getLongitude();
            double longitude = user.getLocation().getLatitude();
            double distance = location.distance(longitude, latitude);
            txt_distance.setText(distance + " km away from me");
        }else {
            txt_distance.setText("No distance information avaliable");
        }
    }

    @Override
    public void onGetInvitation(Invitation invitation) {
        Toast.makeText(ProfileActivity.this, "Invitation sent", Toast.LENGTH_SHORT).show();
        System.out.println("Invitacio enviada");
    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(ProfileActivity.this, "Error sending invitation", Toast.LENGTH_SHORT).show();
        System.out.println("Invitacio erronia");
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






    private void fromStringToImage(String encodedImage) {
        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
        Bitmap decodedImage = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        profileImage.setImageBitmap(decodedImage);
    }


}
package com.marcllort.tinder.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.marcllort.tinder.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ArrayAdapter<MyProfile> {
    public UserAdapter(Context context, int resource, ArrayList<MyProfile> objects) {
        super(context, resource, objects);

    }
  /*  private static final String TAG = "ArrayAdapter";
    private Context mContext;
    int mResource;



    public UserAdapter(Context context, int resource, ArrayList<MyProfile> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        //Get User Info
        String name = getItem(position).getDisplayName();
        Gender gender ;

        gender = getItem(position).getGender();


        String birthday = getItem(position).getBirthDate();



        MyProfile user = new MyProfile(name,gender,birthday);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);


        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvGender = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvBirthday = (TextView) convertView.findViewById(R.id.textView3);

        tvName.setText(name);
        if (gender != null){
            tvGender.setText(gender.getType());
        }

        if (birthday != null){
            tvBirthday.setText(birthday);
        }


        return convertView;
    }

    @Override
    public Filter getFilter() {
        return super.getFilter();
    }*/
}

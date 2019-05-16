package com.marcllort.tinder.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.marcllort.tinder.R;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends ArrayAdapter<MyProfile> {
    private static final String TAG = "ArrayAdapter";
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
        String email = " ";
        if (getItem(position).getGender() != null){
             email = getItem(position).getGender().getType();
        }

        String language = getItem(position).getBirthDate();


        if (language == null){
            language = " ";
        }
        User user = new User(name,email,language);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);


        TextView tvName = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvEmail = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvLang = (TextView) convertView.findViewById(R.id.textView3);

        tvName.setText(name);
        tvEmail.setText(email);
        tvLang.setText(language);

        return convertView;
    }
}

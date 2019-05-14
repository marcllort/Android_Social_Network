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

public class UserAdapter extends ArrayAdapter<User> {
    private static final String TAG = "ArrayAdapter";
    private Context mContext;
    int mResource;



    public UserAdapter(Context context, int resource, ArrayList<User> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {
        //Get User Info
        String name = getItem(position).getLogin();
        String email = getItem(position).getEmail();
        String language = getItem(position).getLangKey();

        if (email == null){
            email = " ";
        }
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

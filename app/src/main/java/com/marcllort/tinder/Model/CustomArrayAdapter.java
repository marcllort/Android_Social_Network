package com.marcllort.tinder.Model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcllort.tinder.R;


public class CustomArrayAdapter extends ArrayAdapter<Invitation> {

    Context context;

    public CustomArrayAdapter(Context context, int resourceId, Invitation[] invitations) {
        super(context, resourceId, invitations);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Invitation invitation = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView name = convertView.findViewById(R.id.dataText);
        ImageView imageView = convertView.findViewById(R.id.mainImage);

        name.setText(invitation.getSent().getDisplayName());
        imageView.setImageResource(Integer.parseInt(invitation.getSent().getPicture()));

        return convertView;
    }
}

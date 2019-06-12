package com.marcllort.tinder.Model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.marcllort.tinder.API.InvitationCallBack;
import com.marcllort.tinder.API.RestAPIManager;
import com.marcllort.tinder.MainActivity;
import com.marcllort.tinder.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class CustomArrayAdapter extends BaseAdapter {

    private Context context;
    private Invitation invitation;


    public CustomArrayAdapter(Context context) {
        this.context = context;
    }


    @Override
    public int getCount() {
        return ((MainActivity) context).getPending().size();
    }

    @Override
    public Object getItem(int position) {
        return ((MainActivity) context).getPending().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item, null, true);

            holder.btn_accept = (Button) convertView.findViewById(R.id.btn_accept);
            holder.usernameButton = (Button) convertView.findViewById(R.id.usernameButton);

            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }

        holder.usernameButton.setText(((MainActivity) context).pendingInvitations.get(position).getSent().getDisplayName());
        holder.usernameButton.setTag(R.integer.btn_username_view, convertView);
        holder.usernameButton.setTag(R.integer.btn_username_pos, position);
        holder.usernameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mostrar su perfil

            }
        });

        holder.btn_accept.setTag(R.integer.btn_accept_view, convertView);
        holder.btn_accept.setTag(R.integer.btn_accept_pos, position);
        holder.btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context = (MainActivity)context;


                ((MainActivity) context).handleAccepted(position);
                notifyDataSetChanged();
                ((MainActivity) context).deleteInvitation(position);
            }
        });

        return convertView;
    }


    private class ViewHolder {
        protected Button btn_accept, usernameButton;

    }
}

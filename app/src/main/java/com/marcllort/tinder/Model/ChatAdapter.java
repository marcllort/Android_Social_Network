package com.marcllort.tinder.Model;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.marcllort.tinder.R;

import java.util.List;



public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    private List<Message> mChatList;
    private Context mContext;

    public ChatAdapter(List<Message> chatList, Context context){
        this.mChatList = chatList;
        this.mContext = context;
    };

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_received, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        itemView.setLayoutParams(lp);

        return new ChatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {

        Message chatMessage = mChatList.get(position);

        // Handling image view, if there's no image, hide the image view

            holder.mChatMessage.setVisibility(View.VISIBLE);
            holder.mChatImage.setVisibility(View.GONE);

            holder.mChatMessage.setText(chatMessage.getMessage());

            // Direction of the text
            if (chatMessage.getSender()!= null) {
                holder.mChatMessage.setGravity(Gravity.END);
                holder.mChatMessage.setTextColor(Color.parseColor("#404040"));
                holder.mChatMessageContainer.setBackgroundColor(Color.parseColor("#F4F4F4"));
            }
            else {
                holder.mChatMessage.setGravity(Gravity.START);
                holder.mChatMessage.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mChatMessageContainer.setBackgroundColor(Color.parseColor("#2DB4C8"));
            }


    }

    @Override
    public int getItemCount() {
        return mChatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mChatMessage;
        public ImageView mChatImage;
        public LinearLayout mChatMessageContainer;

        public ChatViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mChatMessage = itemView.findViewById(R.id.text_message_body);
            mChatImage = itemView.findViewById(R.id.image_message_profile);
            //mChatMessageContainer = itemView.findViewById(R.id.ll_text_container);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
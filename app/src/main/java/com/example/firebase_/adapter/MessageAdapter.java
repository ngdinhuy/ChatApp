package com.example.firebase_.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebase_.R;
import com.example.firebase_.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    private ArrayList<Message> messages;
    private  String senderImg, recieveImg;
    private Context context;

    public MessageAdapter(ArrayList<Message> messages, String senderImg, String recieveImg, Context context) {
        this.messages = messages;
        this.senderImg = senderImg;
        this.recieveImg = recieveImg;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_container_message,parent,false);
        return new MessageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int position) {
        holder.txtContent.setText(messages.get(position).getContent());
        Glide.with(context).load(Uri.parse(senderImg)).into(holder.imgAvatar);
        Log.e("err",senderImg);
        ConstraintLayout constraintLayout = holder.csl;
        if(messages.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.csl);
            constraintSet.clear(R.id.cardView1,ConstraintSet.LEFT);
            constraintSet.clear(R.id.txtContent1,ConstraintSet.LEFT);
            constraintSet.connect(R.id.cardView1,ConstraintSet.RIGHT,R.id.constraint1,ConstraintSet.RIGHT,0);
            constraintSet.connect(R.id.txtContent1,ConstraintSet.RIGHT,R.id.cardView1,ConstraintSet.LEFT,0);
            constraintSet.applyTo(holder.csl);
         }else{
            Glide.with(context).load(recieveImg).error(R.drawable.ic_avatar_24).placeholder(R.drawable.ic_avatar_24).into(holder.imgAvatar);
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(holder.csl);
            constraintSet.clear(R.id.cardView1,ConstraintSet.RIGHT);
            constraintSet.clear(R.id.txtContent1,ConstraintSet.RIGHT);
            constraintSet.connect(R.id.cardView1,ConstraintSet.LEFT,R.id.constraint1,ConstraintSet.LEFT,0);
            constraintSet.connect(R.id.txtContent1,ConstraintSet.LEFT,R.id.cardView1,ConstraintSet.RIGHT,0);
            constraintSet.applyTo(holder.csl);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MessageHolder extends RecyclerView.ViewHolder{
        ConstraintLayout csl;
        TextView txtContent;
        ImageView imgAvatar;
        public MessageHolder(@NonNull View itemView) {
            super(itemView);
            csl = itemView.findViewById(R.id.constraint1);
            txtContent = itemView.findViewById(R.id.txtContent1);
            imgAvatar = itemView.findViewById(R.id.imgAvatar1);
        }
    }
}

package com.example.firebase_.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavArgs;

import com.example.firebase_.adapter.MessageAdapter;
import com.example.firebase_.databinding.FragmentUsersBinding;
import com.example.firebase_.model.Message;
import com.example.firebase_.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class UserFragment extends Fragment {
    FragmentUsersBinding databinding;
    User roomate;
    String chatRoomID;
    ArrayList<Message> messages = new ArrayList<>();
    MessageAdapter messageAdapter;
    String mAvatar="", mEmail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        databinding = FragmentUsersBinding.inflate(inflater,container,false);
        roomate = UserFragmentArgs.fromBundle(getArguments()).getUser();
        databinding.setUser(UserFragmentArgs.fromBundle(getArguments()).getUser());
        return databinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setUpEvent();
        setUpChatRoom();

    }


    private void setUpEvent() {
        databinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = databinding.edtContent.getText().toString();
                if(!content.equals("")){
                    FirebaseDatabase.getInstance().getReference("messages/"+chatRoomID).push().setValue(new Message(mEmail,roomate.getEmail(),content));
                    databinding.edtContent.setText("");
                }
            }
        });
    }
    private void setUpChatRoom() {
        FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mAvatar = snapshot.getValue(User.class).getImage();
                Log.e("err1",mAvatar);
                mEmail = snapshot.getValue(User.class).getEmail();
                String tmp1 = mEmail.substring(0,mEmail.length()-4);
                String tmp2 = roomate.getEmail().substring(0,roomate.getEmail().length()-4);

                if(roomate.getEmail().compareTo(mEmail)>=0){
                    chatRoomID = tmp1 + tmp2;
                }else{
                    chatRoomID = tmp2 + tmp1;
                }
                attachMessageListener(chatRoomID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void attachMessageListener(String chatRoomID){
        FirebaseDatabase.getInstance().getReference("messages/"+chatRoomID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messages.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    messages.add(dataSnapshot.getValue(Message.class));
                }
                messageAdapter = new MessageAdapter(messages,mAvatar,roomate.getImage(),requireContext());
                databinding.messageRecycleView.setAdapter(messageAdapter);
                messageAdapter.notifyDataSetChanged();
                databinding.messageRecycleView.scrollToPosition(messages.size()-1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

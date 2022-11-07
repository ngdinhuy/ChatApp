package com.example.firebase_.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firebase_.R;
import com.example.firebase_.adapter.UsersAdapter;
import com.example.firebase_.databinding.FragmentChatBinding;
import com.example.firebase_.model.User;
import com.example.firebase_.ui.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    FragmentChatBinding fragmentChatBinding;
    private ArrayList<User> users;
    UsersAdapter usersAdapter;
    UsersAdapter.OnUserClickListener onUserClickListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentChatBinding = FragmentChatBinding.inflate(inflater,container,false);
        return fragmentChatBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        users = new ArrayList<>();
        setUpEvent();
        getUsers();
    }


    private void setUpEvent() {
        onUserClickListener = new UsersAdapter.OnUserClickListener() {
            @Override
            public void onUserClicked(int position) {
                NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
                HomeFragment parent =(HomeFragment) navHostFragment.getParentFragment();
                parent.goToUserFragment(users.get(position));
            }
        };
    }
    private void getUsers(){
        users.clear();
        FirebaseDatabase.getInstance().getReference("user").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    users.add(dataSnapshot.getValue(User.class));
                }
                usersAdapter = new UsersAdapter(users,requireContext(),onUserClickListener);
                fragmentChatBinding.usersRecycleView.setLayoutManager(new LinearLayoutManager(requireContext()));
                fragmentChatBinding.usersRecycleView.setAdapter(usersAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

package com.example.firebase_.controller;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.firebase_.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserController {
    public static User getUser(){
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String url = "";
        if(firebaseUser.getPhotoUrl()!=null){
            url = firebaseUser.getPhotoUrl().toString();
        }
        User user = new User(firebaseUser.getDisplayName(),url,firebaseUser.getEmail());
        return user;
    }
}

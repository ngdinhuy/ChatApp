package com.example.firebase_.controller;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.firebase_.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.FirebaseDatabase;

public class AuthController {
    public static boolean checkLogin = false;
    public static String errorLogin = "";
    public static boolean checkSignUp = false;
    public static String errorSignUp = "";
    public static Boolean login(String email, String password, Context context){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            checkLogin = true;
                        } else {
                            checkLogin = false;
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if(e instanceof FirebaseAuthInvalidUserException){
                    Toast.makeText(context, "Tài khoản không tồn tại!", Toast.LENGTH_SHORT).show();
                }else if(e instanceof FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(context, "Mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }else if(e instanceof FirebaseNetworkException){
                    Toast.makeText(context, "Vui lòng kiểm tra kết nối internet!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return checkLogin;
    }
    public static Boolean signUp(String email, String password, Context context){
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new User("","",email,password));
                            checkSignUp = true;
                        } else {
                            checkSignUp = false;
                        }
                    }});
        return checkSignUp;
    }
}

package com.example.firebase_.ui.auth;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebase_.R;
import com.example.firebase_.controller.AuthController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {
    Button btnLogin;
    TextView btnSignUp;
    EditText edtEmail,edtPassword;

    public LoginFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpUI(view);
        setUpEvent();

    }

    private void setUpEvent() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment();
                NavHostFragment.findNavController(LoginFragment.this).navigate(action);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checkLogin = AuthController.login(edtEmail.getText().toString(),edtPassword.getText().toString(),requireContext());
                if(checkLogin){
                    NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
                    NavHostFragment.findNavController(LoginFragment.this).navigate(action);
                }
            }
        });
    }

    private void setUpUI(View view) {
        btnLogin = view.findViewById(R.id.btnLogin);
        btnSignUp = view.findViewById(R.id.btnRegister);
        edtEmail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
    }
//    private void login(){
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        String email = edtEmail.getText().toString();
//        String password = edtPassword.getText().toString();
//        auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                             NavDirections action = LoginFragmentDirections.actionLoginFragmentToHomeFragment();
//                             NavHostFragment.findNavController(LoginFragment.this).navigate(action);
//                        } else {
//                            Toast.makeText(requireContext(), "Login Fail", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }
}
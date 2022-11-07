package com.example.firebase_.ui.auth;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.example.firebase_.R;
import com.example.firebase_.controller.AuthController;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public class SignUpFragment extends Fragment {
    Button btnSignUp;
    EditText edtPassword, edtMail;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
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
                if (AuthController.signUp(edtMail.getText().toString(),edtPassword.getText().toString(),requireContext())){
                    NavDirections action = LoginFragmentDirections.actionGlobalLoginFragment();
                    NavHostFragment.findNavController(SignUpFragment.this).navigate(action);
                }else{
                    Toast.makeText(requireContext(), "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setUpUI(View view) {
        btnSignUp = view.findViewById(R.id.btnSignUp);
        edtMail = view.findViewById(R.id.edtEmail);
        edtPassword = view.findViewById(R.id.edtPassword);
    }
//    private void signUpUser() {
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        String email = edtMail.getText().toString();
//        String password = edtPassword.getText().toString();
//        auth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            NavDirections action = LoginFragmentDirections.actionGlobalLoginFragment();
//                            NavHostFragment.findNavController(SignUpFragment.this).navigate(action);
//                        } else {
//                            Toast.makeText(requireContext(), "Failed Create", Toast.LENGTH_SHORT).show();
//                        }
//                    }});
//    }
}

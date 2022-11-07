package com.example.firebase_.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.example.firebase_.R;
import com.example.firebase_.controller.AuthController;
import com.example.firebase_.controller.UserController;
import com.example.firebase_.databinding.FragmentProfileBinding;
import com.example.firebase_.databinding.FragmentProfileBindingImpl;
import com.example.firebase_.model.User;
import com.example.firebase_.ui.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding fragmentProfileBinding;
    StorageReference storageReference;
    User user;
    String mPassword, mName, senderImg;
    @Nullable
     @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentProfileBinding = FragmentProfileBinding.inflate(inflater,container,false);
        user = UserController.getUser();
        fragmentProfileBinding.setUser(user);
        return fragmentProfileBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        storageReference  = FirebaseStorage.getInstance().getReference();
        setUpEvent();
        getInfoUser();
    }

    private void setUpEvent() {
        fragmentProfileBinding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(requireContext(), "Logout", Toast.LENGTH_SHORT).show();
                NavHostFragment navHostFragment = (NavHostFragment) getParentFragment();
                HomeFragment parent =(HomeFragment) navHostFragment.getParentFragment();
                parent.logout();
            }
        });
        fragmentProfileBinding.changeAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,1000);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            Uri uri = data.getData();
            Glide.with(requireContext()).load(uri).into(fragmentProfileBinding.imgAvatar);
            Log.e("err",uri.toString());
            upLoadImageToFirebase(uri);
        }
    }

    private void upLoadImageToFirebase(Uri uri) {
        StorageReference fileRef = storageReference.child("profile.jpg");
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(requireContext(), "Thay đổi thành công", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setPhotoUri(uri)
                                .build();
                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseDatabase.getInstance().getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(new User(mName,uri.toString(), user.getEmail(),mPassword));
                                        }
                                    }
                                });
                    }
                });
            }
        });
    }
    private void getInfoUser(){
        FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mPassword = snapshot.getValue(User.class).password;
                mName = snapshot.getValue(User.class).name;
                senderImg = snapshot.getValue(User.class).image;
                Glide.with(requireContext()).load(Uri.parse(senderImg)).into(fragmentProfileBinding.imgAvatar);
                fragmentProfileBinding.txtName.setText(mName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

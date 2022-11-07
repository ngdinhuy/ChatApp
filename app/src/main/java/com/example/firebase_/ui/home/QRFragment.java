package com.example.firebase_.ui.home;

import static android.app.Activity.RESULT_OK;

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

import com.bumptech.glide.Glide;
import com.example.firebase_.R;
import com.example.firebase_.adapter.CaptureAct;
import com.example.firebase_.databinding.FragmentQrBinding;
import com.example.firebase_.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureActivity;

public class QRFragment extends Fragment {
    FragmentQrBinding databinding;
    String mName,mEmail,mSDT="";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        databinding = FragmentQrBinding.inflate(inflater,container,false);
        return databinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setUpEvent();
        getInfoUser();
    }

    private void setUpEvent() {
        databinding.btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Khởi tạo Object IntentIntegrator
//                IntentIntegrator intentIntegrator=new IntentIntegrator(requireActivity());
//                intentIntegrator.setCaptureActivity(CaptureAct.class);
//                intentIntegrator.setOrientationLocked(true);
//                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);//Định dạng mã vạch mong muốn
//                intentIntegrator.setPrompt("Scanning...");//Set thuộc tính lời nhắc khi bật camera
//                //Bắt đầu scan
//                intentIntegrator.initiateScan();
                IntentIntegrator intentIntegrator = new IntentIntegrator(requireActivity());
                intentIntegrator.setPrompt("Scan a barcode or QR Code");
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        // if the intentResult is null then
        // toast a message as "cancelled"
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void getInfoUser(){
        FirebaseDatabase.getInstance().getReference("user/"+ FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mName = snapshot.getValue(User.class).name;
                mEmail = snapshot.getValue(User.class).email;
                databinding.txtName.setText(mName);
                databinding.txtGmail.setText(mEmail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

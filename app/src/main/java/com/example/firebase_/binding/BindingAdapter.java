package com.example.firebase_.binding;


import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class BindingAdapter {
    @androidx.databinding.BindingAdapter("imageUrl")
    public static void setImage(ImageView imgView, String url){
        Glide.with(imgView).load(url);
    }
}

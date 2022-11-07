package com.example.firebase_.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.firebase_.R;
import com.example.firebase_.model.User;
import com.example.firebase_.ui.Splash.SplashFragmentDirections;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeFragment extends Fragment {

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        BottomNavigationView bottomNavigationView = view.findViewById(R.id.bottomNav);
        NavController navController = Navigation.findNavController(requireActivity(),R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNavigationView,navController);
        setUpUI();
    }

    private void setUpUI() {

    }
    public void logout(){
        NavDirections action = SplashFragmentDirections.actionGlobalSplashFragment2();
        NavHostFragment.findNavController(HomeFragment.this).navigate(action);
    }
    public void goToUserFragment(User user){
        NavDirections action = HomeFragmentDirections.actionHomeFragmentToUserFragment(user);
        NavHostFragment.findNavController(HomeFragment.this).navigate(action);
    }
}
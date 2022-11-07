package com.example.firebase_.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.firebase_.databinding.ItemContainerUserBinding;
import com.example.firebase_.model.User;
import com.example.firebase_.ui.HomeFragment;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder>{
    private ArrayList<User> users = new ArrayList<>();
    private Context context;
    private OnUserClickListener onUserClickListener;
    public UsersAdapter(ArrayList<User> users, Context context, OnUserClickListener onUserClickListener ) {
        this.users = users;
        this.context = context;
        this.onUserClickListener = onUserClickListener;
    }

    public UsersAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemContainerUserBinding itemContainerUserBinding = ItemContainerUserBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new UserViewHolder(itemContainerUserBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setData(users.get(position));
        holder.binding.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserClickListener.onUserClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public interface OnUserClickListener{
        void onUserClicked(int position);
    }
    class UserViewHolder extends RecyclerView.ViewHolder{
        ItemContainerUserBinding binding;
        public UserViewHolder(ItemContainerUserBinding itemContainerUserBinding) {
            super(itemContainerUserBinding.getRoot());
            binding = itemContainerUserBinding;
        }

        public void setData(User user){
            binding.setUser(user);
        }
    }

}

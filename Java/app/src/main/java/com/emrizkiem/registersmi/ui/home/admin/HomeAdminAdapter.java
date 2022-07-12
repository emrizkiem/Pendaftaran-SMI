package com.emrizkiem.registersmi.ui.home.admin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.emrizkiem.registersmi.data.model.Users;
import com.emrizkiem.registersmi.databinding.ItemDataMemberBinding;
import com.emrizkiem.registersmi.utils.UsersDiffCallback;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class HomeAdminAdapter extends RecyclerView.Adapter<HomeAdminAdapter.ViewHolder> {

    private final ArrayList<Users> listUsers = new ArrayList<>();

    public void setListUsers(List<Users> listUsers) {
        final UsersDiffCallback callback = new UsersDiffCallback(this.listUsers, listUsers);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(callback);

        this.listUsers.clear();
        this.listUsers.addAll(listUsers);
        diffResult.dispatchUpdatesTo(this);
    }


    @NonNull
    @Override
    public HomeAdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDataMemberBinding binding = ItemDataMemberBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdminAdapter.ViewHolder holder, int position) {
        holder.bindView(listUsers.get(position));
    }

    @Override
    public int getItemCount() {
        return listUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final ItemDataMemberBinding binding;

        public ViewHolder(ItemDataMemberBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(Users users) {
            // Get data image from database
            ByteArrayInputStream imageStream = new ByteArrayInputStream(users.getImageProfile());
            Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
            binding.imgProfile.setImageBitmap(bitmap);
            // Get data string from database
            binding.tvName.setText(users.getUsername());
            binding.tvMotivationLetter.setText(users.getMotivationLetter());
            binding.tvGpa.setText(users.getGpa().toString());
        }
    }
}

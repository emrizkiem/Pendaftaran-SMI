package com.emrizkiem.registersmi.ui.home.admin;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.emrizkiem.registersmi.data.model.Users;
import com.emrizkiem.registersmi.databinding.ActivityHomeAdminBinding;
import com.emrizkiem.registersmi.utils.ViewModelFactory;

import java.io.ByteArrayInputStream;

public class HomeAdminActivity extends AppCompatActivity {

    private ActivityHomeAdminBinding binding;
    private HomeAdminAdapter adapter;
    private Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new HomeAdminAdapter();

        HomeAdminViewModel viewModel = obtainViewModel(HomeAdminActivity.this);
        viewModel.getAllMember().observe(this, users -> {
            if (users != null) {
                adapter.setListUsers(users);
            }
        });

        binding.rvMember.setHasFixedSize(true);
        binding.rvMember.setLayoutManager(new LinearLayoutManager(this));
        binding.rvMember.setAdapter(adapter);

//        users = new Users();
//
//        setupData();
        makeStatusBarTransparent();
    }

    private HomeAdminViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(HomeAdminViewModel.class);
    }

    private void setupData() {
        // Get data image from database
//        ByteArrayInputStream imageStream = new ByteArrayInputStream(users.getImageProfile());
//        Bitmap bitmap = BitmapFactory.decodeStream(imageStream);
//        binding.imgProfile.setImageBitmap(bitmap);
        binding.tvName.setText(users.getUsername());
    }

    private void setTopBar() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            binding.scrollable.setOnScrollChangeListener(new View.OnScrollChangeListener() {
//                @Override
//                public void onScrollChange(View view, int scrollX, int scrollY, int i2, int i3) {
//                    float elevation = 15f;
//
//                }
//            });
//        }
    }

    private void makeStatusBarTransparent() {
        // This function to make StatusBar Transparent
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
            );
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            );
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
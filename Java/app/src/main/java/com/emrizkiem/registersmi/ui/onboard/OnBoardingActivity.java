package com.emrizkiem.registersmi.ui.onboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.emrizkiem.registersmi.R;
import com.emrizkiem.registersmi.data.model.OnBoarding;
import com.emrizkiem.registersmi.ui.login.LoginActivity;
import com.emrizkiem.registersmi.ui.register.RegisterActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.TimerTask;

public class OnBoardingActivity extends AppCompatActivity {

    private ArrayList<OnBoarding> list = new ArrayList<>();
    private ViewPager pager;
    private TabLayout tabLayout;
    private Button btnLogin, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        pager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        list.addAll(getListOnBoard());

        OnBoardingAdapter adapter = new OnBoardingAdapter(list);
        pager.setAdapter(adapter);

        java.util.Timer timer = new java.util.Timer();
        timer.scheduleAtFixedRate(new Timer(), 2000, 3000);
        tabLayout.setupWithViewPager(pager, true);

        goToLoginPage();
        goToRegisterPage();
        makeStatusBarTransparent();
    }

    private void goToLoginPage() {
        btnLogin.setOnClickListener((View v) -> {
            Intent goToLogin = new Intent(OnBoardingActivity.this, LoginActivity.class);
            startActivity(goToLogin);
        });
    }

    private void goToRegisterPage() {
        btnRegister.setOnClickListener((View v) -> {
            Intent goToRegister = new Intent(OnBoardingActivity.this, RegisterActivity.class);
            startActivity(goToRegister);
        });
    }

    private ArrayList<OnBoarding> getListOnBoard() {
        String[] dataTitle = getResources().getStringArray(R.array.data_title_onboard);
        String[] dataDesc = getResources().getStringArray(R.array.data_desc_onboard);
        TypedArray dataImg = getResources().obtainTypedArray(R.array.data_img_onboard);

        ArrayList<OnBoarding> listData = new ArrayList<>();
        for (int i = 0; i < dataTitle.length; i++) {
            OnBoarding onBoard = new OnBoarding();
            onBoard.setTitle(dataTitle[i]);
            onBoard.setDescription(dataDesc[i]);
            onBoard.setImg(dataImg.getResourceId(i, -1));

            listData.add(onBoard);
        }

        return listData;
    }

    private void makeStatusBarTransparent() {
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

    public class Timer extends TimerTask {
        @Override
        public void run() {
            OnBoardingActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (pager.getCurrentItem() < list.size() - 1) {
                        pager.setCurrentItem(pager.getCurrentItem() + 1);
                    } else {
                        pager.setCurrentItem(0);
                    }
                }
            });
        }
    }
}
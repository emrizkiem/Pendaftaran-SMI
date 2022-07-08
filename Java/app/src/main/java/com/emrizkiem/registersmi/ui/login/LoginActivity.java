package com.emrizkiem.registersmi.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.emrizkiem.registersmi.R;
import com.emrizkiem.registersmi.databinding.ActivityLoginBinding;
import com.emrizkiem.registersmi.ui.home.admin.HomeAdminActivity;
import com.emrizkiem.registersmi.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener((View v) -> {
            // Goto Admin/Member page;
            Intent goToHome = new Intent(LoginActivity.this, HomeAdminActivity.class);
            startActivity(goToHome);
        });

        customizeText();
        makeStatusBarTransparent();
    }

    private void customizeText() {
        /* This function for customize text "Daftar",
        add underline text forgot password and
        clickable to direct to page register if don't have account
        */
        Spannable spannable = new SpannableString(getString(R.string.dont_have_account));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent goToRegister = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(goToRegister);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getColor(R.color.red));
                ds.setUnderlineText(false);
            }
        };
        spannable.setSpan(new ForegroundColorSpan(getColor(R.color.red)), 18, 24, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannable.setSpan(clickableSpan, 18, 24, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        binding.tvDontHaveAccount.setText(spannable);
        binding.tvDontHaveAccount.setMovementMethod(LinkMovementMethod.getInstance());
        binding.tvForgotPassword.getPaint().setUnderlineText(true);
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
}
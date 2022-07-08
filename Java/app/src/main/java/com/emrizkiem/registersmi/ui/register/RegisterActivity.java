package com.emrizkiem.registersmi.ui.register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.emrizkiem.registersmi.R;
import com.emrizkiem.registersmi.databinding.ActivityRegisterBinding;
import com.emrizkiem.registersmi.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        customizeText();
        makeStatusBarTransparent();
    }

    private void customizeText() {
        /* This function for customize text "Masuk", and
        clickable to direct to page login if already account
        */
        Spannable spannable = new SpannableString(getString(R.string.already_account));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent goToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goToLogin);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(getColor(R.color.red));
                ds.setUnderlineText(false);
            }
        };
        spannable.setSpan(new ForegroundColorSpan(getColor(R.color.red)), 18, 23, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannable.setSpan(clickableSpan, 18, 23, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        binding.tvAlreadyAccount.setText(spannable);
        binding.tvAlreadyAccount.setMovementMethod(LinkMovementMethod.getInstance());
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
package com.emrizkiem.registersmi.ui.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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
import com.emrizkiem.registersmi.data.model.Users;
import com.emrizkiem.registersmi.databinding.ActivityLoginBinding;
import com.emrizkiem.registersmi.ui.home.admin.HomeAdminActivity;
import com.emrizkiem.registersmi.ui.register.RegisterActivity;
import com.emrizkiem.registersmi.utils.InputValidation;
import com.emrizkiem.registersmi.utils.ToastHelper;
import com.emrizkiem.registersmi.utils.ViewModelFactory;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private LoginViewModel viewModel;
    private ActivityLoginBinding binding;
    private InputValidation validation;
    private ToastHelper toastHelper;
    private Users users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = obtainViewModel(LoginActivity.this);

        // Initialize listener onClick
        binding.btnLogin.setOnClickListener(this);
        // Initialize object validation
        validation = new InputValidation(this);
        // Initialize object toast
        toastHelper = new ToastHelper(this);
        // Initialize model
        users = new Users();

        customizeText();
        makeStatusBarTransparent();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_login) {
            onLogin();
        }
    }

    private static LoginViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(LoginViewModel.class);
    }

    /**
     * This method is to validate the input text fields and verify login credentials from Room Database
     */
    private void onLogin() {
        boolean email = !validation.isInputEditTextEmail(binding.edtEmail, binding.tilEmail, getString(R.string.error_message_email));
        boolean password = !validation.isInputEditTextPassword(binding.edtPassword, binding.tilPassword, getString(R.string.error_message_password));

        if (email && password) {
            return;
        }

        users = viewModel.onLogin(binding.edtEmail.getText().toString().trim(), binding.edtPassword.getText().toString().trim());
        if (users != null) {
            if (users.getEmail().equalsIgnoreCase(binding.edtEmail.getText().toString().trim()) &&
                    users.getPassword().equalsIgnoreCase(binding.edtPassword.getText().toString().trim())) {
                Intent goToHome = new Intent(LoginActivity.this, HomeAdminActivity.class);
                emptyInputEditText();
                startActivity(goToHome);
                finish();
            } else {
                toastHelper.showToastError(getString(R.string.error_valid_email_password));
            }
        } else {
            toastHelper.showToastError(getString(R.string.error_data_not_available));
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        binding.edtEmail.setText(null);
        binding.edtPassword.setText(null);
    }

    /**
     * This function for customize text "Daftar", and
     * clickable to direct to page login if already account
     */
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
                finish();
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

    /**
     * This function to make StatusBar Transparent
     */
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
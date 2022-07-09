package com.emrizkiem.registersmi.ui.register;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.emrizkiem.registersmi.databinding.ActivityRegisterBinding;
import com.emrizkiem.registersmi.ui.login.LoginActivity;
import com.emrizkiem.registersmi.utils.InputValidation;
import com.emrizkiem.registersmi.utils.ToastHelper;
import com.emrizkiem.registersmi.utils.ViewModelFactory;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private RegisterViewModel viewModel;
    private ActivityRegisterBinding binding;
    private InputValidation validation;
    private ToastHelper toastHelper;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
    private Bitmap bitmap;
    private Users users;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = obtainViewModel(RegisterActivity.this);

        // Initialize listener onClick
        binding.btnRegister.setOnClickListener(this);
        // Initialize object Validation
        validation = new InputValidation(this);
        // Initialize object Toast
        toastHelper = new ToastHelper(this);
        // Initialize model
        users = new Users();
        // Select image from gallery
        binding.imgProfile.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select"), 99);
        });

        datePicker();
        customizeText();
        makeStatusBarTransparent();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_register) {
            onRegistered();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && data != null && data.getData() != null){
            Uri uri = data.getData();
            try{
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                binding.imgProfile.setImageBitmap(bitmap);
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static RegisterViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(RegisterViewModel.class);
    }

    /**
    * This function is to picker date of birthday
    */
    private void datePicker() {
        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("Select date of birth");

        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        binding.edtDate.setOnClickListener((View v) -> {
            materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER");
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                String date = sdf.format(selection);
                binding.edtDate.setText(date);
            }
        });
    }

    /**
    * This function is to validate the input edit text and post data to Room Database
    */
    private void onRegistered() {
        boolean email = !validation.isInputEditTextEmail(binding.edtEmail, binding.tilEmail, getString(R.string.error_message_email));
        boolean username = !validation.isInputEditTextFilled(binding.edtUsername, binding.tilUsername, getString(R.string.error_message_username));
        boolean password = !validation.isInputEditTextPassword(binding.edtPassword, binding.tilPassword, getString(R.string.error_message_password));
        boolean date = !validation.isInputEditTextFilled(binding.edtDate, binding.tilDate, getString(R.string.error_message_date));
        boolean gpa = !validation.isInputEditTextFilled(binding.edtGpa, binding.tilGpa, getString(R.string.error_message_gpa));
        boolean address = !validation.isInputEditTextFilled(binding.edtAddress, binding.tilAddress, getString(R.string.error_message_address));
        boolean motivationLetter = !validation.isInputEditTextFilled(binding.edtMotivationLetter, binding.tilMotivationLetter, getString(R.string.error_message_motivation));

        if (email && username && password && date && gpa && address && motivationLetter) {
            return;
        }

        users = viewModel.findByEmail(binding.edtEmail.getText().toString().trim());
        if (users != null && !users.getEmail().equalsIgnoreCase(binding.edtEmail.getText().toString().trim())) {
            // Convert image to bitmap
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            bitmap.recycle();

            users.setImageProfile(byteArray);
            users.setEmail(binding.edtEmail.getText().toString().trim());
            users.setUsername(binding.edtUsername.getText().toString().trim());
            users.setPassword(binding.edtPassword.getText().toString().trim());
            users.setDateBirthday(binding.edtDate.getText().toString().trim());
            users.setGpa(Double.valueOf(binding.edtGpa.getText().toString().trim()));
            users.setAddress(binding.edtAddress.getText().toString().trim());
            users.setMotivationLetter(binding.edtMotivationLetter.getText().toString().trim());
            // Save to database
            viewModel.saveToDatabase(users);
            toastHelper.showToastSuccess(getString(R.string.success_registered));
            emptyInputEditText();
        } else {
            toastHelper.showToastError(getString(R.string.error_email_exists));
        }

    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        binding.edtEmail.setText(null);
        binding.edtUsername.setText(null);
        binding.edtPassword.setText(null);
        binding.edtDate.setText(null);
        binding.edtGpa.setText(null);
        binding.edtAddress.setText(null);
        binding.edtMotivationLetter.setText(null);
    }

    /**
    * This function for customize text "Masuk", and
    * clickable to direct to page login if already account
    */
    private void customizeText() {
        Spannable spannable = new SpannableString(getString(R.string.already_account));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent goToLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(goToLogin);
                finish();
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

    /**
    * This function to make StatusBar Transparent
    */
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
}
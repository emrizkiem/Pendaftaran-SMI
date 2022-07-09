package com.emrizkiem.registersmi.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.emrizkiem.registersmi.R;

public class ToastHelper {
    private Context context;

    /**
     *
     * @param context
     */
    public ToastHelper(Context context) { this.context = context; }

    /**
     *
     * @param msg
     * This function for show toast success
     */
    public void showToastSuccess(String msg) {
        Toast toast = new Toast(context);

        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast_success, null);

        TextView textView = layout.findViewById(R.id.tvMessage);
        textView.setText(msg);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setView(layout);
        toast.show();
    }

    /**
     *
     * @param msg
     * This function for show toast success
     */
    public void showToastError(String msg) {
        Toast toast = new Toast(context);

        View layout = LayoutInflater.from(context).inflate(R.layout.custom_toast_error, null);

        TextView textView = layout.findViewById(R.id.tvMessage);
        textView.setText(msg);

        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.setView(layout);
        toast.show();
    }
}

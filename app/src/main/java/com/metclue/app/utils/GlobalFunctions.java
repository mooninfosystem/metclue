package com.metclue.app.utils;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.provider.Settings;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.kaopiz.kprogresshud.KProgressHUD;
import com.metclue.app.R;

public class GlobalFunctions {

    public static void showProgressDialog(KProgressHUD kProgressHUD) {
        kProgressHUD
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }


    public static void hideProgressDialog(KProgressHUD kProgressHUD) {
        kProgressHUD.dismiss();
    }

    public static String getDeviceID(Context context) {
        @SuppressLint("HardwareIds") String device_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_id;
    }

    public static void shakeanimation(Context context, View view) {
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake);
        view.startAnimation(shake);
    }

    public static void showAlert(Context context, String title, String desc) {

        final Dialog myDialog = new Dialog(context);
        myDialog.setCancelable(true);
        myDialog.setContentView(R.layout.dialog_layout);

        TextView tvDescription,tvTitle,dialogButton;

        tvDescription = myDialog.findViewById(R.id.dialogDescription);
        tvTitle = myDialog.findViewById(R.id.dialogTitle);
        dialogButton = myDialog.findViewById(R.id.dialogButton);

        tvTitle.setText(title);
        tvDescription.setText(desc);
        dialogButton.setOnClickListener(v -> myDialog.dismiss());

        myDialog.show();

    }















}

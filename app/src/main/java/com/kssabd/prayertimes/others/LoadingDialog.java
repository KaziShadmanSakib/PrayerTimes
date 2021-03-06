package com.kssabd.prayertimes.others;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.kssabd.prayertimes.R;

public class LoadingDialog {

    private final Activity activity;
    private AlertDialog dialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoadingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_screen, null));
        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }
    public boolean isShowing(){


        return dialog.isShowing();
    }

    public void dismissDialog() {
        dialog.dismiss();
    }
}
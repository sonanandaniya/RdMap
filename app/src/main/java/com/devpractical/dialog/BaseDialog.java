package com.devpractical.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import com.devpractical.R;
import com.devpractical.utils.Utils;


public class BaseDialog extends Dialog {

    public Context context;

    public BaseDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public BaseDialog(@NonNull Context context, @StyleRes int style) {
        super(context, style);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
    }

    public boolean isNetworkAvail(boolean showMsg) {
        if (com.devpractical.utils.Utils.isNetworkAvailable(context)) {
            return true;
        } else {
            if (showMsg)
                showErrorMessage(context.getString(R.string.nonetwork));
            return false;
        }
    }

    void showSuccessMessage(String msg) {
        com.devpractical.utils.Utils.showSuccessMessage(context, msg);
    }

    void showErrorMessage(String msg) {
        Utils.showErrorMessage(context, msg);
    }

}

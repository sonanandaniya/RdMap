package com.devpractical.dialog;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.devpractical.R;
import com.devpractical.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetBaseDialog extends BottomSheetDialogFragment {

    public Context context;
     public static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    public BottomSheetBaseDialog(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

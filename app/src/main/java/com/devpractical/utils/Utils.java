package com.devpractical.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.devpractical.R;
import com.google.android.material.snackbar.Snackbar;

public class Utils {

    public static boolean isNetworkAvailable(Context context) {
        boolean isConnected = false;
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();

        if (activeInfo != null
                && (activeInfo.isConnected() || activeInfo.isConnectedOrConnecting())) {

            switch (activeInfo.getType()) {
                case ConnectivityManager.TYPE_MOBILE:
                    //SLog.i("TYPE_MOBILE : "+ ConnectivityManager.TYPE_MOBILE);
                    isConnected = true;
                    break;
                case ConnectivityManager.TYPE_WIFI:
                    //SLog.i("TYPE_WIFI : " + ConnectivityManager.TYPE_WIFI);
                    isConnected = true;
                    break;
                case ConnectivityManager.TYPE_WIMAX:
                    //SLog.i("TYPE_WIMAX : " + ConnectivityManager.TYPE_WIMAX);
                    isConnected = true;
                    break;
                case ConnectivityManager.TYPE_ETHERNET:
                    //SLog.i("TYPE_ETHERNET : "+ ConnectivityManager.TYPE_ETHERNET);
                    isConnected = true;
                    break;

                default:
                    isConnected = false;
                    break;
            }
        }
        return isConnected;
    }


    public static void showErrorMessage(Context context, String message) {
        Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_error));
        TextView textView = snackBarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }


    public static void showSuccessMessage(Context context, String message) {
        Snackbar snackbar = Snackbar.make(((Activity) context).findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(context, R.color.color_success));
        TextView textView = snackBarView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    public static void addFragment(Fragment fragment, FragmentManager fragmentManager, int containerId) {

         fragmentManager.beginTransaction().replace(containerId, fragment).commit();
    }


    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
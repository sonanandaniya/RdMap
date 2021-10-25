package com.devpractical;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.devpractical.utils.NetworkConnectivity;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AppClass extends Application {

    public NetworkConnectivity networkConnectivity;
    private String chatUserId;
    public static int RING_MAX_TIME = 45;
    public static int width, height;


    public void onCreate() {
        super.onCreate();
        networkConnectivity = new NetworkConnectivity(this);
        height = Resources.getSystem().getDisplayMetrics().heightPixels;
        width = Resources.getSystem().getDisplayMetrics().widthPixels;

    }


    public NetworkConnectivity getNetworkConnectivity() {
        if (networkConnectivity == null) {
            networkConnectivity = new NetworkConnectivity(this);
        }
        return networkConnectivity;
    }


}

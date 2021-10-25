package com.devpractical.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.devpractical.AppClass;
import com.devpractical.R;
import com.devpractical.databinding.DialogNoInternetBinding;


public class NoInternetDialog extends BaseDialog {

    private View.OnClickListener listener;
    private DialogNoInternetBinding binding;
    private RotateAnimation aniRotate;


    public NoInternetDialog(@NonNull Context context, View.OnClickListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_no_internet, null, false);
        setContentView(binding.getRoot());
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_background);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        // ButterKnife.bind(this, binding.getRoot());
        aniRotate = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );

        aniRotate.setDuration(900);
        aniRotate.setRepeatCount(5);
        aniRotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (!((AppClass) context.getApplicationContext()).networkConnectivity.isNetworkAvailable())
                    showErrorMessage(context.getString(R.string.no_internet_connection));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        setCancelable(false);

        binding.tvRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.ivRetry.startAnimation(aniRotate);
                if (((AppClass) context.getApplicationContext()).networkConnectivity.isNetworkAvailable())
                    listener.onClick(v);
            }
        });
    }

    @NonNull
    public Context getDialogContext() {
        return context;
    }


}
